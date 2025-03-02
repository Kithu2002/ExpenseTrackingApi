package com.expenseTracker.application.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Changed int to Long

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;



    @Column(nullable = false)
    private String email;


    @Setter
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;




    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Expense> expenses;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }


}
