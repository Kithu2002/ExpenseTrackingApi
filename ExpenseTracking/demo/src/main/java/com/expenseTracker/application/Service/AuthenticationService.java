package com.expenseTracker.application.Service;

import com.expenseTracker.application.DTO.LoginRequestDTO;
import com.expenseTracker.application.DTO.LoginResponseDTO;
import com.expenseTracker.application.DTO.RegisterDTO;
import com.expenseTracker.application.Entity.User;
import com.expenseTracker.application.JWT.JWTService;
import com.expenseTracker.application.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNormalUser(RegisterDTO registerUser) {
        if (userRepository.findByUsername(registerUser.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        Set<String> roles = new HashSet<>();
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

        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user);
        return LoginResponseDTO.builder().token(token).username(user.getUsername()).roles(user.getRoles()).build();
    }
}