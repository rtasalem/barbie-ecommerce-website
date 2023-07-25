package com.fdmgroup.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.api.model.Favourites;

public interface FavouritesRepository extends JpaRepository<Favourites, Integer> {

}
