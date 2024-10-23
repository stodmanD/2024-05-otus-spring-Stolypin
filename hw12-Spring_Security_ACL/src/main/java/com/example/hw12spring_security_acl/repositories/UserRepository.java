package com.example.hw12spring_security_acl.repositories;

import com.example.hw12spring_security_acl.models.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String userName);
}
