package com.fdmgroup.api.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fdmgroup.api.exception.ItemNameExistsException;
import com.fdmgroup.api.exception.ResourceNotFoundException;
import com.fdmgroup.api.model.Item;
import com.fdmgroup.api.repository.ItemRepository;

@Service
public class ItemService {

	private final static Logger log = LoggerFactory.getLogger(ItemService.class);

	private final ItemRepository itemRepo;

	public ItemService(ItemRepository itemRepo) {
		super();
		this.itemRepo = itemRepo;
	}

	public List<Item> findAllItems() {
		log.info("Entering findAllItems");
		log.info("Exiting findAllItems");
		return itemRepo.findAll();
	}

	public Item findItemById(int itemId) {
		log.info("Entering findItemById");
		Optional<Item> itemOpt = itemRepo.findById(itemId);
		if (itemOpt.isEmpty()) {
			throw new ResourceNotFoundException("Item with id of " + itemId + " does not exist.");
		}
		log.info("Exiting findItemById");
		return itemOpt.get();
	}

	public List<Item> findItemsByNameOrDescription(String searchTerm) {
		log.info("Entering findItemsByNameOrDescription");
		log.info("Exiting findItemsByNameOrDescription");
		return itemRepo.findByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(searchTerm, searchTerm);
	}

	public Item findItemByName(String name) {
		log.info("Entering findItemByName");
		log.info("Exiting findItemByName");
		return itemRepo.findByNameContainsIgnoreCase(name);
	}

	public Item findItemByDescription(String description) {
		log.info("Entering findItemByDescription");
		log.info("Exiting findItemByDescription");
		return itemRepo.findByDescriptionContainsIgnoreCase(description);
	}

	public Item addItem(Item item) {
		log.info("Entering addItem");
		if (itemRepo.existsByName(item.getName())) {
			throw new ItemNameExistsException("Item with the name of " + item.getName() + " already exists.");
		}
		log.info("Exiting addItem");
		return itemRepo.save(item);
	}

	public Item editItem(Item item, int itemId) {
		log.info("Entering editItem");
		if (!itemRepo.existsById(itemId)) {
			throw new ResourceNotFoundException("Item with id of " + itemId + " does not exist.");
		}
		item.setItemId(itemId);
		log.info("Exiting editItem");
		return itemRepo.save(item);
	}

	public void deleteItemById(int itemId) {
		log.info("Entering deleteItem");
		if (!itemRepo.existsById(itemId)) {
			throw new ResourceNotFoundException("Item with id of " + itemId + " does not exist.");
		}
		log.info("Exiting deleteItem");
		itemRepo.deleteById(itemId);
	}

}
