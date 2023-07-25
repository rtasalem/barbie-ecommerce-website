package com.fdmgroup.api.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.api.exception.FavouriteAlreadyExistsException;
import com.fdmgroup.api.exception.ResourceNotFoundException;
import com.fdmgroup.api.model.Favourites;
import com.fdmgroup.api.repository.FavouritesRepository;

@Service
public class FavouritesService {

	@Autowired
	private FavouritesRepository favRepo;

	private final static Logger log = LoggerFactory.getLogger(FavouritesService.class);

	public List<Favourites> getAllFavouriteItems() {
		log.info("Getting all favourites from the list from the database.");
		return favRepo.findAll();
	}

	public Favourites getFavourite(int favourites_id) {
		log.info("Getting a specific favourite list with id: {}", favourites_id);
		Optional<Favourites> favouritesOpt = favRepo.findById(favourites_id);
		if (favouritesOpt.isEmpty()) {
			log.warn("Favourite item with id {} not found.", favourites_id);
			throw new ResourceNotFoundException("Favourite item with id of " + favourites_id + " not found.");
		}
		return favouritesOpt.get();
	}

	// adding items to the Favourites page

	public Favourites addFavourite(Favourites favourites) {
		if (favRepo.existsById(favourites.getFavourites_id())) {
			throw new FavouriteAlreadyExistsException(
					"Item with this id" + favourites.getFavourites_id() + "already exists.");
		}
		log.info("Adding new favourite with id: {}", favourites.getFavourites_id());

		return favRepo.save(favourites);
	}

	// delete items from the Favourites page

	public void deleteFavouriteById(int id) {
		if (favRepo.existsById(id)) {
			throw new ResourceNotFoundException("Favourite items with the id of" + id + "not found.");
		}
		log.info("Deleting favourite with id: {}", id);
		favRepo.deleteById(id);
	}

}
