package com.fdmgroup.api.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.api.exception.FavouriteAlreadyExistsException;
import com.fdmgroup.api.exception.ResourceNotFoundException;
import com.fdmgroup.api.model.Favourites;
import com.fdmgroup.api.repository.FavouritesRepository;
import com.fdmgroup.api.service.FavouritesService;

@ExtendWith(MockitoExtension.class)
public class FavouritesServiceTest {

	@Mock
	private FavouritesRepository favRepo;

	@InjectMocks
	private FavouritesService favouritesService;

	@Test
	public void testGetAllFavouriteItems() {
		List<Favourites> favouritesList = new ArrayList<>();
		Favourites favourites1 = new Favourites();
		Favourites favourites2 = new Favourites();
		favouritesList.add(favourites1);
		favouritesList.add(favourites2);

		when(favRepo.findAll()).thenReturn(favouritesList);

		List<Favourites> result = favouritesService.getAllFavouriteItems();
		assertEquals(favouritesList, result);
	}

	@Test
	public void testGetFavourite_ItemExists() {
		Favourites favourites = new Favourites();
		int favouritesId = 1;

		when(favRepo.findById(favouritesId)).thenReturn(Optional.of(favourites));

		Favourites result = favouritesService.getFavourite(favouritesId);
		assertEquals(favourites, result);
	}

	@Test
	public void testGetFavourite_ItemNotExists() {
		int favouritesId = 1;

		when(favRepo.findById(favouritesId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> favouritesService.getFavourite(favouritesId));
	}

	@Test
	public void testAddFavourite_ItemNotExists() {
		Favourites favourites = new Favourites();

		when(favRepo.existsById(anyInt())).thenReturn(false);
		when(favRepo.save(any(Favourites.class))).thenReturn(favourites);

		Favourites result = favouritesService.addFavourite(favourites);
		assertEquals(favourites, result);
	}

	@Test
	public void testAddFavourite_ItemExists() {
		Favourites favourites = new Favourites();
		int favouritesId = 1;

		when(favRepo.existsById(anyInt())).thenReturn(true);

		assertThrows(FavouriteAlreadyExistsException.class, () -> favouritesService.addFavourite(favourites));
	}

	@Test
	public void testDeleteFavouriteById_ItemExists() {
		int favouritesId = 1;

		when(favRepo.existsById(favouritesId)).thenReturn(true);

		assertThrows(ResourceNotFoundException.class, () -> favouritesService.deleteFavouriteById(favouritesId));
	}

	@Test
	public void testDeleteFavouriteById_ItemNotExists() {
		int favouritesId = 1;

		when(favRepo.existsById(favouritesId)).thenReturn(false);
		doNothing().when(favRepo).deleteById(favouritesId);

		favouritesService.deleteFavouriteById(favouritesId);
		verify(favRepo).deleteById(favouritesId);
	}
}