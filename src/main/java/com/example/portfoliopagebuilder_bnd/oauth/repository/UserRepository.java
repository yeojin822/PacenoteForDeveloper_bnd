package com.example.portfoliopagebuilder_bnd.oauth.repository;

import java.util.Optional;

import com.example.portfoliopagebuilder_bnd.oauth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Optional<User> findByProviderId(String providerId);
}
