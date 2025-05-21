package com.example.LibraryManagement.Service;

import com.example.LibraryManagement.Entity.User;  // Make sure this is your User entity
import com.example.LibraryManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve the user from the database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Return a Spring Security UserDetails implementation (e.g., org.springframework.security.core.userdetails.User)
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),  // username
                user.getPassword(),  // password
                user.getRoles().stream()  // roles
                        .map(role -> new SimpleGrantedAuthority(role))  // convert roles to SimpleGrantedAuthority
                        .collect(Collectors.toList())  // collect as a list of granted authorities
        );
    }
}
