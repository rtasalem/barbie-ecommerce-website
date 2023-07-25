package com.fdmgroup.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.api.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {

	boolean existsByName(String name);

	List<Item> findByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(String name, String description);

	Item findByNameContainsIgnoreCase(String name);

	Item findByDescriptionContainsIgnoreCase(String description);

}
