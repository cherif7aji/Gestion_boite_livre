package com.capgemini.polytech.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTGenerator tokenGenerator;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtAuthEntryPoint authEntryPoint ;


    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws IOException, ServletException  {
        try {
            String token = getJWTFromRequest(request);
            System.out.println("the token after extraction :"+token);
            if (StringUtils.hasText(token) && tokenGenerator.validateToken(token)) {
                System.out.println("start extracting the user information");
                String username = tokenGenerator.getUsernameFromJWT(token);
                System.out.println(username);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                System.out.println(userDetails);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null,
                        userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                System.out.println(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                System.out.println("fin");
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
            throw ex ;
            //SecurityContextHolder.clearContext();
            //authEntryPoint.commence(request, response, new AuthenticationException("Authentication failed: " + ex.getMessage()) {});

            //return;
        }
        filterChain.doFilter(request, response);
    }

    public String getJWTFromRequest(HttpServletRequest request) {
        try {
            System.out.println("getting the token");
            String bearerToken = request.getHeader("Authorization");
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(bearerToken.substring(7));
                System.out.println("le token est: " + jsonNode.get("accessToken").asText());
                return jsonNode.get("accessToken").asText();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}
