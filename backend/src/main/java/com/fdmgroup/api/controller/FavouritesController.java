package com.fdmgroup.api.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fdmgroup.api.model.Favourites;
import com.fdmgroup.api.service.FavouritesService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/favourites")
@CrossOrigin(origins = "http://localhost:3000")
public class FavouritesController {

	private final FavouritesService favouritesService;

	private final static Logger log = LoggerFactory.getLogger(FavouritesController.class);

	public FavouritesController(FavouritesService favouritesService) {
		super();
		this.favouritesService = favouritesService;
	}

	@GetMapping
	public ResponseEntity<List<Favourites>> getAllFavourites() {
		log.info("Getting all favourite items");
		return ResponseEntity.status(HttpStatus.OK).body(favouritesService.getAllFavouriteItems());
	}

	@GetMapping("/{favourites_id}")
	public ResponseEntity<Favourites> getFavourite(@PathVariable int favourites_id) {
		log.info("Getting favourite list with id: {}", favourites_id);
		return ResponseEntity.status(HttpStatus.OK).body(favouritesService.getFavourite(favourites_id));
	}

	@PostMapping
	public ResponseEntity<Favourites> addFavourites(@Valid @RequestBody Favourites favourites) {
		log.info("Adding a new favourite item: {}", favourites);
		favouritesService.addFavourite(favourites);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{favourites_id}")
				.buildAndExpand(favourites.getFavourites_id()).toUri();
		return ResponseEntity.created(location).body(favourites);
	}

	@DeleteMapping("/{favourites_id}")
	public ResponseEntity<Void> deleteFavourites(@PathVariable int favourites_id) {
		log.info("Deleting favourite item with id: {}", favourites_id);
		favouritesService.deleteFavouriteById(favourites_id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
