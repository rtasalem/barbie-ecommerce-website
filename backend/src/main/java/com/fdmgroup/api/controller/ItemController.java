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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fdmgroup.api.model.Item;
import com.fdmgroup.api.service.ItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/items")
@CrossOrigin(origins = "http://localhost:3000")
public class ItemController {

	private final static Logger log = LoggerFactory.getLogger(ItemController.class);

	private final ItemService itemService;

	public ItemController(ItemService itemService) {
		super();
		this.itemService = itemService;
	}

	@GetMapping
	public ResponseEntity<List<Item>> getAllItems() {
		log.info("Entering getAllItems");
		log.info("Exiting getAllItems");
		return ResponseEntity.status(HttpStatus.OK).body(itemService.findAllItems());
	}

	@GetMapping("/{itemId}")
	public ResponseEntity<Item> getItem(@PathVariable int itemId) {
		log.info("Entering getItem");
		log.info("Exiting getItem");
		return ResponseEntity.status(HttpStatus.OK).body(itemService.findItemById(itemId));
	}

	@GetMapping("premiere-item-page/{itemId}")
	public ResponseEntity<Item> getPremiereItem(@PathVariable int itemId) {
		log.info("Entering getPremiereItem");
		log.info("Exiting getPremiereItem");
		return ResponseEntity.status(HttpStatus.OK).body(itemService.findItemById(itemId));
	}

	@GetMapping("swimwear-item-page/{itemId}")
	public ResponseEntity<Item> getSwimwearItem(@PathVariable int itemId) {
		log.info("Entering getSwimwearItem");
		log.info("Exiting getSwimwearItem");
		return ResponseEntity.status(HttpStatus.OK).body(itemService.findItemById(itemId));
	}

	@GetMapping("ken-item-page/{itemId}")
	public ResponseEntity<Item> getKenItem(@PathVariable int itemId) {
		log.info("Entering getKenItem");
		log.info("Exiting getKenItem");
		return ResponseEntity.status(HttpStatus.OK).body(itemService.findItemById(itemId));
	}

	@GetMapping("/search")
	public ResponseEntity<List<Item>> searchItemsByNameOrDescription(@RequestParam("searchTerm") String searchTerm) {
		log.info("Entering searchItemsByNameOrDescription");
		List<Item> items = itemService.findItemsByNameOrDescription(searchTerm);
		if (!items.isEmpty()) {
			log.info("Exiting searchItemsByNameOrDescription");
			return ResponseEntity.status(HttpStatus.OK).body(items);
		}
		log.info("Exiting searchItemsByNameOrDescription");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<Item> searchItemByName(@PathVariable("name") String name) {
		log.info("Entering searchItemByName");
		Item item = itemService.findItemByName(name);
		if (item != null) {
			log.info("Exiting searchItemByName");
			return ResponseEntity.status(HttpStatus.OK).body(item);
		}
		log.info("Exiting searchItemByName");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@GetMapping("/description/{description}")
	public ResponseEntity<Item> searchItemByDescription(@PathVariable("description") String description) {
		log.info("Entering searchItemByDescription");
		Item item = itemService.findItemByDescription(description);
		if (item != null) {
			log.info("Exiting searchItemByDescription");
			return ResponseEntity.status(HttpStatus.OK).body(item);
		}
		log.info("Exiting searchItemByDescription");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PostMapping
	public ResponseEntity<Item> addItem(@Valid @RequestBody Item item) {
		log.info("Entering addItem");
		itemService.addItem(item);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{itemId}")
				.buildAndExpand(item.getItemId()).toUri();
		log.info("Exiting addItem");
		return ResponseEntity.created(location).body(item);
	}

	@PutMapping("/{itemId}")
	public ResponseEntity<Item> editItem(@PathVariable int itemId, @Valid @RequestBody Item item) {
		log.info("Entering editItem");
		log.info("Exiting editItem");
		return ResponseEntity.ok(itemService.editItem(item, itemId));
	}

	@DeleteMapping("/{itemId}")
	public ResponseEntity<Void> deleteItem(@PathVariable int itemId) {
		log.info("Entering deleteItem");
		itemService.deleteItemById(itemId);
		log.info("Exiting deleteItem");
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
