package com.fdmgroup.api.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fdmgroup.api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	boolean existsByEmail(String email);
	//
	Optional<User> findByEmail(@Param("email") String email);
	//
	Optional<User> findById(Long userId);

}
	