package com.example.LibraryManagement.Service;

import com.example.LibraryManagement.DTO.LoginRequestDTO;
import com.example.LibraryManagement.DTO.LoginResponseDTO;
import com.example.LibraryManagement.DTO.RegisterDTO;
import com.example.LibraryManagement.Entity.User;
import com.example.LibraryManagement.JWT.JWTService;
import com.example.LibraryManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

   private  final PasswordEncoder passwordEncoder;

   @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNormalUser(RegisterDTO registerUser) {
        if(userRepository.findByUsername(registerUser.getUsername()).isPresent()){
            throw  new RuntimeException("User already exists");
        }

        Set<String>roles = new HashSet<>();
        roles.add("ROLE_USER");
        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        user.setEmail(registerUser.getEmail());
        user.setRoles(roles);
        return userRepository.save(user);

    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(()-> new RuntimeException("User not found"));

        String token =jwtService.generateToken(user);
        return LoginResponseDTO.builder().token(token).username(user.getUsername()).roles(user.getRoles()).build();
    }

    public User registerAdminUser(RegisterDTO registerAdmin) {
        if(userRepository.findByUsername(registerAdmin.getUsername()).isPresent()){
            throw  new RuntimeException("User already exists");
        }

        Set<String>roles = new HashSet<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");
        User user = new User();
        user.setUsername(registerAdmin.getUsername());
        user.setPassword(passwordEncoder.encode(registerAdmin.getPassword()));
        user.setEmail(registerAdmin.getEmail());
        user.setRoles(roles);
        return userRepository.save(user);


    }
}
