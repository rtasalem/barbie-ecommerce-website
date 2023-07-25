package com.fdmgroup.api.controller.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.api.controller.FavouritesController;
import com.fdmgroup.api.exception.ResourceNotFoundException;
import com.fdmgroup.api.model.Favourites;
import com.fdmgroup.api.model.Item;
import com.fdmgroup.api.service.FavouritesService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class FavouritesControllerTest {

	@Mock
	private FavouritesService mockFavouritesService;

	private MockMvc mockMvc;

	private List<Favourites> favouritesList;

	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new FavouritesController(mockFavouritesService)).build();

		// Prepare mock data
		Item item1 = new Item("Item 1", "Description 1", "Type 1", "Size 1", 5.99);
		Item item2 = new Item("Item 2", "Description 2", "Type 2", "Size 2", 8.99);
		Item item3 = new Item("Item 3", "Description 3", "Type 3", "Size 3", 12.49);

		Favourites favourites1 = new Favourites();
		favourites1.setFavourites_id(1);
		favourites1.setFavourites_list(List.of(item1, item2));

		Favourites favourites2 = new Favourites();
		favourites2.setFavourites_id(2);
		favourites2.setFavourites_list(List.of(item1));

		favouritesList = List.of(favourites1, favourites2);

	}

	// ... Other test methods ...

	@Test
	void testAddFavourites() throws Exception {
		Favourites newFavourites = new Favourites();
		newFavourites.setFavourites_id(3);
		newFavourites.setFavourites_list(List.of(new Item("New Item", "Description", "Type", "Size", 9.99)));

//		when(mockFavouritesService.addFavourite(newFavourites)).thenReturn(newFavourites);
		when(mockFavouritesService.addFavourite(any(Favourites.class))).thenReturn(newFavourites);

		mockMvc.perform(
				post("/api/v1/favourites").contentType(MediaType.APPLICATION_JSON).content(asJsonString(newFavourites)))
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.favourites_id").value(newFavourites.getFavourites_id()))
				.andExpect(jsonPath("$.favourites_list[0].name").value("New Item"))
				.andExpect(jsonPath("$.favourites_list[0].description").value("Description"))
				.andExpect(jsonPath("$.favourites_list[0].itemType").value("Type"))
				.andExpect(jsonPath("$.favourites_list[0].size").value("Size"))
				.andExpect(jsonPath("$.favourites_list[0].price").value(9.99));
	}

	// ... Other test methods ...

	// Helper method to convert Java object to JSON string
	private String asJsonString(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Test
	void testGetFavourite() throws Exception {
		int favouritesId = 1;
		Favourites favourites = favouritesList.get(0);
		when(mockFavouritesService.getFavourite(favouritesId)).thenReturn(favourites);

		mockMvc.perform(get("/api/v1/favourites/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.favourites_id").value(favourites.getFavourites_id()))
				.andExpect(jsonPath("$.favourites_list.length()").value(favourites.getFavourites_list().size()))
				.andExpect(jsonPath("$.favourites_list[0].itemId")
						.value(favourites.getFavourites_list().get(0).getItemId()))
				.andExpect(
						jsonPath("$.favourites_list[0].name").value(favourites.getFavourites_list().get(0).getName()))
				.andExpect(jsonPath("$.favourites_list[1].itemId")
						.value(favourites.getFavourites_list().get(1).getItemId()))
				.andExpect(
						jsonPath("$.favourites_list[1].name").value(favourites.getFavourites_list().get(1).getName()));
	}

	@Test
	void testGetAllFavourites() throws Exception {
		// Prepare mock data
		Item item1 = new Item("Item 1", "Description 1", "Type 1", "Size 1", 5.99);
		Item item2 = new Item("Item 2", "Description 2", "Type 2", "Size 2", 8.99);
		Item item3 = new Item("Item 3", "Description 3", "Type 3", "Size 3", 12.49);

		Favourites favourites1 = new Favourites();
		favourites1.setFavourites_id(1);
		favourites1.setFavourites_list(List.of(item1, item2));

		Favourites favourites2 = new Favourites();
		favourites2.setFavourites_id(2);
		favourites2.setFavourites_list(List.of(item1));

		List<Favourites> favouritesList = List.of(favourites1, favourites2);

		// Mock the service call to return the mock data
		when(mockFavouritesService.getAllFavouriteItems()).thenReturn(favouritesList);

		mockMvc.perform(get("/api/v1/favourites")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(favouritesList.size()))
				.andExpect(jsonPath("$[0].favourites_id").value(favouritesList.get(0).getFavourites_id()))
				.andExpect(jsonPath("$[0].favourites_list.length()")
						.value(favouritesList.get(0).getFavourites_list().size()))
				.andExpect(jsonPath("$[0].favourites_list[0].itemId")
						.value(favouritesList.get(0).getFavourites_list().get(0).getItemId()))
				.andExpect(jsonPath("$[0].favourites_list[0].name")
						.value(favouritesList.get(0).getFavourites_list().get(0).getName()))
				.andExpect(jsonPath("$[1].favourites_id").value(favouritesList.get(1).getFavourites_id()))
				.andExpect(jsonPath("$[1].favourites_list.length()")
						.value(favouritesList.get(1).getFavourites_list().size()))
				.andExpect(jsonPath("$[1].favourites_list[0].itemId")
						.value(favouritesList.get(1).getFavourites_list().get(0).getItemId()))
				.andExpect(jsonPath("$[1].favourites_list[0].name")
						.value(favouritesList.get(1).getFavourites_list().get(0).getName()));

		// Verify that the service method was called once
		verify(mockFavouritesService, times(1)).getAllFavouriteItems();
	}

	@Test
	void testDeleteFavourites_Success() throws Exception {
		int favouritesIdToDelete = 1;

		// Mock the service call and ensure it doesn't throw an exception
		doNothing().when(mockFavouritesService).deleteFavouriteById(favouritesIdToDelete);

		mockMvc.perform(delete("/api/v1/favourites/1")).andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$").doesNotExist());

		// Verify that the service method was called once with the correct id
		verify(mockFavouritesService, times(1)).deleteFavouriteById(favouritesIdToDelete);
	}

	@Test
	void testDeleteFavourites_Failure_ResourceNotFound() throws Exception {
		int nonExistentFavouritesId = 100;

		// Mock the service call and throw a ResourceNotFoundException
		doThrow(new ResourceNotFoundException(
				"Favourite items with the id of " + nonExistentFavouritesId + " not found."))
				.when(mockFavouritesService).deleteFavouriteById(nonExistentFavouritesId);

		mockMvc.perform(delete("/api/v1/favourites/{favourites_id}", nonExistentFavouritesId))
				.andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message")
						.value("Favourite items with the id of " + nonExistentFavouritesId + " not found."));

		// Verify that the service method was called once with the correct id
		verify(mockFavouritesService, times(1)).deleteFavouriteById(nonExistentFavouritesId);
	}

}
