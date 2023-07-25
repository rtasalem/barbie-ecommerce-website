package com.fdmgroup.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.api.model.Basket;
import com.fdmgroup.api.model.BasketItem;
import com.fdmgroup.api.model.Item;

public interface BasketItemRepository extends JpaRepository<BasketItem, Integer> {
	
	BasketItem findByBasketAndItem(Basket basket, Item item);

	void deleteByBasket(Basket basket);

	List<BasketItem> findByBasket(Basket basket);
}
