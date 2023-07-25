package com.fdmgroup.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.api.model.Basket;
import com.fdmgroup.api.model.User;

public interface BasketRepository extends JpaRepository<Basket, Integer> {

	Optional<Basket> findByUser(User user);

}
