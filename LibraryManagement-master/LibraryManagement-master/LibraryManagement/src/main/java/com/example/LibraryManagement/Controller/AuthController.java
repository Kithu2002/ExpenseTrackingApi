package com.example.LibraryManagement.Controller;

import com.example.LibraryManagement.DTO.LoginRequestDTO;
import com.example.LibraryManagement.DTO.LoginResponseDTO;
import com.example.LibraryManagement.DTO.RegisterDTO;
import com.example.LibraryManagement.Entity.User;
import com.example.LibraryManagement.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")

public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/registernormaluser")
    public ResponseEntity<User> registerNormalUser(@RequestBody RegisterDTO registerUser) {
        return ResponseEntity.ok(authenticationService.registerNormalUser(registerUser));


    }

    @PostMapping("/loginuser")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequest) {
        return  ResponseEntity.ok(authenticationService.login(loginRequest));
    }
}
