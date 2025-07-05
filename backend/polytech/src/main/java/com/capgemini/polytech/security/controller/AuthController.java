package com.capgemini.polytech.security.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.polytech.repositories.RoleRepository;
import com.capgemini.polytech.repositories.UtilisateurRepository;
import com.capgemini.polytech.security.JWTAuthenticationFilter;
import com.capgemini.polytech.security.JWTGenerator;
import com.capgemini.polytech.security.dto.AuthResponse;
import com.capgemini.polytech.security.dto.LoginDto;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    private AuthenticationManager authenticationManager;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UtilisateurRepository userRepository,
            RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator,
            JWTAuthenticationFilter filter) {
        this.authenticationManager = authenticationManager;

        this.jwtGenerator = jwtGenerator;

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto) {
        System.out.println(loginDto.getUsername() + " bvcf " + loginDto.getPassword());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Map<String, String> token = jwtGenerator.generateToken(authentication);
        String access = token.get("access-token");
        String userId = token.get("user-id");
        return new ResponseEntity<>(new AuthResponse(access,userId), HttpStatus.OK);
    }

    @PostMapping("/access-denied")
    public ResponseEntity<String> handleAccessDenied() {
        System.out.println("access denied");
        return new ResponseEntity<>("You do not have permission to access this resource.", HttpStatus.FORBIDDEN);
    }
}