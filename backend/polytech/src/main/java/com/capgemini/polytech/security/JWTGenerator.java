 package com.capgemini.polytech.security;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.capgemini.polytech.models.Utilisateur;
import com.capgemini.polytech.repositories.UtilisateurRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Component
public class JWTGenerator {

    @Autowired
    private UtilisateurRepository us;


    public Map<String, String> generateToken(Authentication authentication) {
        String username = authentication.getName();

        Optional<Utilisateur> u = us.findByUsername(username);
       
        Utilisateur utilisateur = u.get();  // Obtenir l'objet Utilisateur
        Integer userId = utilisateur.getId();  // Récupérer l'ID de l'utilisateur
        
        // Convertir userId en String
        String userIdString = String.valueOf(userId);
        
        System.out.println("L'ID de l'utilisateur en tant que String est : " + userIdString);
        

        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);
        Date expireRefreshDate = new Date(currentDate.getTime() + SecurityConstants.JWT_REFRESH_EXPIRATION);
        
        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .claim("roles", roles)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireRefreshDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET)
                .compact();

                Map<String, String> idToken = new HashMap<>();
                idToken.put("access-token", token);
                idToken.put("refresh-token", refreshToken);
                idToken.put("user-id",userIdString);
                String usId = idToken.get("user-id");
                System.out.println("usId");
                System.out.println(usId);
                return idToken;
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            System.out.println(token);
            Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET).parseClaimsJws(token);
            System.out.println("hey");

            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }
}

