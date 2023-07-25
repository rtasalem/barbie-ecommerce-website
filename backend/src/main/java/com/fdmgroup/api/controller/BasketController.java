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

import com.fdmgroup.api.model.Basket;
import com.fdmgroup.api.model.BasketItem;
import com.fdmgroup.api.model.User;
import com.fdmgroup.api.service.BasketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/basket")
@CrossOrigin(origins = "http://localhost:3000")
public class BasketController {
	
	private final BasketService basketService;

	private final static Logger log = LoggerFactory.getLogger(BasketController.class);

	public BasketController(BasketService basketService) {
		super();
		this.basketService = basketService;
	}

	@GetMapping
	public ResponseEntity<List<Basket>> getAllItemsInBasket() {
		log.info("Entering getAllItemsInBasket");
		log.info("Exiting getAllItemsInBasket");
		return ResponseEntity.status(HttpStatus.OK).body(basketService.getAllItemsInBasket());
	}

	@GetMapping("/{basketId}")
	public ResponseEntity<Basket> getBasket(@PathVariable int basketId) {
		log.info("Entering getBasket");
		log.info("Exiting getBasket");
		return ResponseEntity.status(HttpStatus.OK).body(basketService.getBasket(basketId));
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<Basket> getBasketByUser(@PathVariable Long userId) {
		log.info("Entering getBasketByUser");
		User user = new User();
		user.setUserId(userId);
		log.info("Exiting getBasketByUser");
		return ResponseEntity.status(HttpStatus.OK).body(basketService.getBasketByUser(user));
	}

	@PostMapping("/addToBasket/{basketId}")
	public ResponseEntity<Basket> addItemToBasket(@PathVariable int basketId, @Valid @RequestBody BasketItem basketItem) {
		log.info("Entering addItemToBasket");
		Basket updatedBasket = basketService.addItemToBasket(basketId, basketItem);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{basketId}")
				.buildAndExpand(basketItem.getBasketItemId()).toUri();
		log.info("Exiting addItemToBasket");
		return ResponseEntity.created(location).body(updatedBasket);
	}

	@PutMapping("/editQuantity/{basketItemId}")
	public ResponseEntity<Basket> editItemQuantityInBasket(@PathVariable int basketItemId,
			@RequestParam int newQuantity) {
		log.info("Entering editItemQuantityInBasket");
		basketService.editItemQuantityInBasket(basketItemId, newQuantity);
		log.info("Exiting editItemQuantityInBasket");
		return ResponseEntity.ok().build();
	}

	@PutMapping("/editSize/{basketItemId}")
	public ResponseEntity<Basket> editItemSizeInBasket(@PathVariable int basketItemId, @RequestParam String newSize) {
		log.info("Entering editItemSizeInBasket");
		// You may need to handle the logic for editing the item size based on your requirements
		basketService.editItemSizeInBasket(basketItemId, newSize);
		log.info("Exiting editItemSizeInBasket");
		return ResponseEntity.ok().build();
	}

	@GetMapping("/totalPrice/{basketId}")
	public ResponseEntity<Double> calculateTotalPrice(@PathVariable int basketId) {
		log.info("Entering calculateTotalPrice");
		double totalPrice = basketService.calculateTotalPrice(basketId);
		log.info("Exiting calculateTotalPrice");
		return ResponseEntity.status(HttpStatus.OK).body(totalPrice);
	}

	@DeleteMapping("/{basketItemId}")
	public ResponseEntity<Void> deleteItemFromBasketById(@PathVariable int basketItemId) {
		log.info("Entering deleteItemFromBasketById");
		basketService.deleteItemFromBasketById(basketItemId);
		log.info("Exiting deleteItemFromBasketById");
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@DeleteMapping("/clear/{basketId}")
	public ResponseEntity<Void> clearBasket(@PathVariable int basketId) {
		log.info("Entering clearBasket");
		basketService.clearBasket(basketId);
		log.info("Exiting clearBasket");
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
