package com.expenseTracker.application.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.expenseTracker.application.Entity.User;


public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}