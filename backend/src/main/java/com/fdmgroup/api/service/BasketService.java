package com.fdmgroup.api.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.api.exception.ResourceNotFoundException;
import com.fdmgroup.api.model.Basket;
import com.fdmgroup.api.model.BasketItem;
import com.fdmgroup.api.model.User;
import com.fdmgroup.api.repository.BasketItemRepository;
import com.fdmgroup.api.repository.BasketRepository;

@Service
public class BasketService {
	@Autowired
	private BasketRepository basketRepo;
	
	@Autowired
	private BasketItemRepository basketItemRepo;
	
	private final static Logger log = LoggerFactory.getLogger(BasketService.class);
	
	public List<Basket> getAllItemsInBasket() {
		log.info("Entering getAllItemsInBasket");
		log.trace("Returning a list of all items in the basket");
		log.info("Exiting getAllItemsInBasket");
		return basketRepo.findAll();
	}
	
	public Basket getBasket(int basketId) {
		log.info("Entering getBasket");
		Optional<Basket> basketOpt = basketRepo.findById(basketId);
		if(basketOpt.isEmpty()) {
			throw new ResourceNotFoundException("Basket with id of " + basketId + " not found.");
		}
		log.info("Exiting getBasket");
		return basketOpt.get();
	}
	
	public Basket getBasketByUser(User user) {
		log.info("Entering getBasketByUser");
		if (user == null) {
	        throw new ResourceNotFoundException("User does not exist.");
	    }
		Optional<Basket> basketUser = basketRepo.findByUser(user);
	    if (basketUser.isEmpty()) {
	        throw new ResourceNotFoundException("Basket not found for user with id " + user.getUserId());
	    }
	    log.info("Exiting getBasketByUser");
	    return basketUser.get();
	}
	
	public Basket addItemToBasket(int basketId, BasketItem basketItem) {
		log.info("Entering addItemToBasket");
		Basket basket = getBasket(basketId);
		BasketItem existingItem = basketItemRepo.findByBasketAndItem(basket, basketItem.getItem());

		if (existingItem == null) {
			basketItem.setBasket(basket);
			basketItem.setQuantity(1);
			basket.getBasketItems().add(basketItem);
		} else {
			existingItem.setQuantity(existingItem.getQuantity() + 1);
		}
		
		basketItemRepo.save(basketItem);
		basketRepo.save(basket);
		log.info("Exiting addItemToBasket");
		return basket;
	}

	public void editItemQuantityInBasket(int basketItemId, int newQuantity) {
		log.info("Entering editItemQuantityInBasket");
	    BasketItem basketItem = basketItemRepo.findById(basketItemId)
	            .orElseThrow(() -> new ResourceNotFoundException("Basket item with id " + basketItemId + " not found."));

	    if (newQuantity < 1) {
	    	Basket basket = basketItem.getBasket();
	    	basket.getBasketItems().remove(basketItem);
	    	basketItemRepo.delete(basketItem);
	    	basketRepo.save(basket);
	    } else {
	        basketItem.setQuantity(newQuantity);
		    log.info("Exiting editItemQuantityInBasket");
	        basketItemRepo.save(basketItem);
	    }
	}
	
	public void editItemSizeInBasket(int basketItemId, String newSize) {
	    log.info("Entering editItemSizeInBasket");
	    BasketItem basketItem = basketItemRepo.findById(basketItemId)
	            .orElseThrow(() -> new ResourceNotFoundException("Basket item with id " + basketItemId + " not found."));

	    basketItem.setSize(newSize);
	    log.info("Exiting editItemSizeInBasket");
	    basketItemRepo.save(basketItem);
	}
	
	public double calculateTotalPrice(int basketId) {
		log.info("Entering calculateTotalPrice");
	    Basket basket = getBasket(basketId);
	    List<BasketItem> basketItems = basket.getBasketItems();

	    double totalPrice = 0.0;

	    for (BasketItem basketItem : basketItems) {
	        double itemPrice = basketItem.getItem().getPrice();
	        int quantity = basketItem.getQuantity();
	        
	        totalPrice += (itemPrice * quantity);
	    }
	    log.info("Exiting calculateTotalPrice");
	    return totalPrice;
	}
	
	public void deleteItemFromBasketById(int basketItemId) {
		log.info("Entering deleteItemFromBasketById");
		BasketItem basketItem = basketItemRepo.findById(basketItemId)
				.orElseThrow(() -> new ResourceNotFoundException("Item with id of " + basketItemId + " not found"));
		
		Basket basket = basketItem.getBasket();
		basket.getBasketItems().remove(basketItem);
		basketItemRepo.delete(basketItem);
		log.info("Exiting deleteItemFromBasketById");
		basketRepo.save(basket);
	}
	
	public void clearBasket(int basketId) {
		log.info("Entering clearBasket");
	    Basket basket = getBasket(basketId);
	    basket.getBasketItems().clear();
	    log.info("Exiting clearBasket");
	    basketRepo.save(basket);
	}
	
}



