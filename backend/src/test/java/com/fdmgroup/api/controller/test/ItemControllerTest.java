package com.fdmgroup.api.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fdmgroup.api.controller.ItemController;
import com.fdmgroup.api.exception.ItemNameExistsException;
import com.fdmgroup.api.model.Item;
import com.fdmgroup.api.service.ItemService;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

	@InjectMocks
	private ItemController itemController;

	@Mock
	private ItemService itemService;

	@Test
	void testGetAllItems() {
		List<Item> itemList = Arrays.asList(new Item("Item 1", "Description 1", "Type 1", "Size 1", 10.99),
				new Item("Item 2", "Description 2", "Type 2", "Size 2", 15.99));
		when(itemService.findAllItems()).thenReturn(itemList);

		ResponseEntity<List<Item>> responseEntity = itemController.getAllItems();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(itemList, responseEntity.getBody());
		verify(itemService, times(1)).findAllItems();
	}

	@Test
	void testGetItem() {
		int itemId = 1;
		Item item = new Item("Item 1", "Description 1", "Type 1", "Size 1", 10.99);

		when(itemService.findItemById(itemId)).thenReturn(item);

		ResponseEntity<Item> responseEntity = itemController.getItem(itemId);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(item, responseEntity.getBody());
		verify(itemService, times(1)).findItemById(itemId);
	}

	@Test
	public void testGetPremiereItem() {
		int itemId = 123;
		Item expectedItem = new Item();
		expectedItem.setItemId(itemId);

		Mockito.when(itemService.findItemById(itemId)).thenReturn(expectedItem);

		ResponseEntity<Item> responseEntity = itemController.getPremiereItem(itemId);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedItem, responseEntity.getBody());

		Mockito.verify(itemService, Mockito.times(1)).findItemById(itemId);
	}

	@Test
	public void testGetSwimwearItem() {
		int itemId = 456;
		Item expectedItem = new Item();
		expectedItem.setItemId(itemId);

		Mockito.when(itemService.findItemById(itemId)).thenReturn(expectedItem);

		ResponseEntity<Item> responseEntity = itemController.getSwimwearItem(itemId);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedItem, responseEntity.getBody());

		Mockito.verify(itemService, Mockito.times(1)).findItemById(itemId);
	}

	@Test
	public void testGetKenItem() {
		int itemId = 789;
		Item expectedItem = new Item();
		expectedItem.setItemId(itemId);

		Mockito.when(itemService.findItemById(itemId)).thenReturn(expectedItem);

		ResponseEntity<Item> responseEntity = itemController.getKenItem(itemId);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedItem, responseEntity.getBody());

		Mockito.verify(itemService, Mockito.times(1)).findItemById(itemId);
	}

	@Test
	public void testSearchItemsByNameOrDescription_ItemFound() {
		List<Item> mockItems = new ArrayList<>();
		mockItems.add(new Item());
		mockItems.add(new Item());
		when(itemService.findItemsByNameOrDescription("searchTerm")).thenReturn(mockItems);

		ResponseEntity<List<Item>> response = itemController.searchItemsByNameOrDescription("searchTerm");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockItems, response.getBody());
	}

	@Test
    public void testSearchItemsByNameOrDescription_ItemNotFound() {
        when(itemService.findItemsByNameOrDescription("searchTerm")).thenReturn(new ArrayList<>());

        ResponseEntity<List<Item>> response = itemController.searchItemsByNameOrDescription("searchTerm");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

	@Test
	public void testSearchItemByName_ItemFound() {
		String name = "TestItem";
		Item mockItem = new Item();

		when(itemService.findItemByName(name)).thenReturn(mockItem);

		ResponseEntity<Item> responseEntity = itemController.searchItemByName(name);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(mockItem, responseEntity.getBody());
	}

	@Test
	public void testSearchItemByName_ItemNotFound() {
		String name = "NonExistingItem";

		when(itemService.findItemByName(name)).thenReturn(null);

		ResponseEntity<Item> responseEntity = itemController.searchItemByName(name);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());
	}

	@Test
	public void testSearchItemByDescription_ItemFound() {
		String description = "test item";

		Item mockItem = new Item();

		when(itemService.findItemByDescription(description)).thenReturn(mockItem);

		ResponseEntity<Item> responseEntity = itemController.searchItemByDescription(description);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(mockItem, responseEntity.getBody());
	}

	@Test
	public void testSearchItemByDescription_ItemNotFound() {
		String description = "NonExistingDescription";

		when(itemService.findItemByDescription(description)).thenReturn(null);

		ResponseEntity<Item> responseEntity = itemController.searchItemByDescription(description);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());
	}

	@Test
	public void testAddItem() {
		Item itemToAdd = new Item("Test Item", "Test Description", "Type", "Size", 10.0);
		when(itemService.addItem(any(Item.class))).thenReturn(itemToAdd);

		ResponseEntity<Item> response = itemController.addItem(itemToAdd);

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(itemToAdd, response.getBody());
	}

	@Test
	public void testAddItemWithExistingName() {
		Item existingItem = new Item("Existing Item", "Description", "Type", "Size", 15.0);
		when(itemService.addItem(any(Item.class)))
				.thenThrow(new ItemNameExistsException("Item with the name of Existing Item already exists."));

		assertThrows(ItemNameExistsException.class, () -> itemController.addItem(existingItem));
	}

	@Test
	void testEditItem() {
		int itemId = 1;
		Item item = new Item("Item 1", "Description 1", "Type 1", "Size 1", 10.99);

		when(itemService.editItem(eq(item), eq(itemId))).thenReturn(item);

		ResponseEntity<Item> responseEntity = itemController.editItem(itemId, item);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(item, responseEntity.getBody());
		verify(itemService, times(1)).editItem(eq(item), eq(itemId));
	}

	@Test
	void testDeleteItem() {
		int itemId = 1;

		ResponseEntity<Void> responseEntity = itemController.deleteItem(itemId);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		verify(itemService, times(1)).deleteItemById(eq(itemId));
	}

}
