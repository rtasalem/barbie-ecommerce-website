package com.fdmgroup.api.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
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

import com.fdmgroup.api.exception.ItemNameExistsException;
import com.fdmgroup.api.exception.ResourceNotFoundException;
import com.fdmgroup.api.model.Item;
import com.fdmgroup.api.repository.ItemRepository;
import com.fdmgroup.api.service.ItemService;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

	@InjectMocks
	private ItemService itemService;

	@Mock
	private ItemRepository itemRepo;

	@Test
	public void testFindAllItems() {
		List<Item> itemList = new ArrayList<>();
		when(itemRepo.findAll()).thenReturn(itemList);

		List<Item> result = itemService.findAllItems();
		assertEquals(itemList, result);
	}

	@Test
	public void testFindItemById_ItemExists() {
		int itemId = 1;
		Item item = new Item("ItemName", "ItemDesc", "ItemType", "ItemSize", 10.0);
		when(itemRepo.findById(itemId)).thenReturn(Optional.of(item));

		Item result = itemService.findItemById(itemId);
		assertEquals(item, result);
	}

	@Test
	public void testFindItemById_ItemNotExists() {
		int itemId = 100;
		when(itemRepo.findById(itemId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> itemService.findItemById(itemId));
	}

	@Test
	public void testFindItemsByNameOrDescription() {
		List<Item> mockItems = new ArrayList<>();
		mockItems.add(new Item());
		mockItems.add(new Item());
		when(itemRepo.findByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase("searchTerm", "searchTerm"))
				.thenReturn(mockItems);

		List<Item> result = itemService.findItemsByNameOrDescription("searchTerm");

		assertEquals(mockItems, result);
	}

	@Test
	public void testFindItemByName_ItemFound() {
		String name = "TestItem";
		Item mockItem = new Item();

		when(itemRepo.findByNameContainsIgnoreCase(name)).thenReturn(mockItem);

		Item resultItem = itemService.findItemByName(name);

		assertEquals(mockItem, resultItem);
	}

	@Test
	public void testFindItemByName_ItemNotFound() {
		String name = "NonExistingItem";

		when(itemRepo.findByNameContainsIgnoreCase(name)).thenReturn(null);

		Item resultItem = itemService.findItemByName(name);

		assertNull(resultItem);
	}

	@Test
	public void testFindItemByDescription_ItemFound() {
		String description = "test item";
		Item mockItem = new Item();

		when(itemRepo.findByDescriptionContainsIgnoreCase(description)).thenReturn(mockItem);

		Item resultItem = itemService.findItemByDescription(description);

		assertEquals(mockItem, resultItem);
	}

	@Test
	public void testFindItemByDescription_ItemNotFound() {
		String description = "NonExistingDescription";

		when(itemRepo.findByDescriptionContainsIgnoreCase(description)).thenReturn(null);

		Item resultItem = itemService.findItemByDescription(description);

		assertNull(resultItem);
	}

	@Test
	public void testAddItem_ItemNameDoesNotExist() {
		Item item = new Item("ItemName", "ItemDesc", "ItemType", "ItemSize", 10.0);
		when(itemRepo.existsByName(item.getName())).thenReturn(false);
		when(itemRepo.save(item)).thenReturn(item);

		Item result = itemService.addItem(item);
		assertEquals(item, result);
	}

	@Test
	public void testAddItem_ItemNameExists() {
		Item item = new Item("ItemName", "ItemDesc", "ItemType", "ItemSize", 10.0);
		when(itemRepo.existsByName(item.getName())).thenReturn(true);

		assertThrows(ItemNameExistsException.class, () -> itemService.addItem(item));
	}

	@Test
	public void testEditItem_ItemExists() {
		int itemId = 1;
		Item item = new Item("ItemName", "ItemDesc", "ItemType", "ItemSize", 10.0);
		when(itemRepo.existsById(itemId)).thenReturn(true);
		when(itemRepo.save(item)).thenReturn(item);

		Item result = itemService.editItem(item, itemId);
		assertEquals(item, result);
	}

	@Test
	public void testEditItem_ItemNotExists() {
		int itemId = 100;
		Item item = new Item("ItemName", "ItemDesc", "ItemType", "ItemSize", 10.0);
		when(itemRepo.existsById(itemId)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> itemService.editItem(item, itemId));
	}

	@Test
	public void testDeleteItemById_ItemExists() {
		int itemId = 1;
		when(itemRepo.existsById(itemId)).thenReturn(true);

		itemService.deleteItemById(itemId);
		verify(itemRepo, times(1)).deleteById(itemId);
	}

	@Test
	public void testDeleteItemById_ItemNotExists() {
		int itemId = 100;
		when(itemRepo.existsById(itemId)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> itemService.deleteItemById(itemId));
	}

}
