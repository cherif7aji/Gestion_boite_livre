package com.capgemini.polytech.security;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.capgemini.polytech.models.Utilisateur;
import com.capgemini.polytech.models.Role;
import com.capgemini.polytech.repositories.UtilisateurRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UtilisateurRepository userRepository;
    @Autowired
    public CustomUserDetailsService(UtilisateurRepository userRepository) {
        this.userRepository = userRepository;
    }

     @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> optionalUserEntity = userRepository.findByUsername(username);
        Utilisateur userEntity = optionalUserEntity
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        System.out.println("the user "+userEntity.getNom()+" has the role"+userEntity.getRoles());
        return new User(userEntity.getUsername(), userEntity.getPassword(), mapRolesToAuthorities(userEntity.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
