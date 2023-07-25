package com.fdmgroup.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class BasketItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASKET_ITEM_ID_GEN")
	@SequenceGenerator(name = "BASKET_ITEM_ID_GEN", sequenceName = "BASKET_ITEM_ID_SEQ", allocationSize = 1)
	private int basketItemId;
	
	@ManyToOne
	@JoinColumn(name = "basketId")
	@JsonIgnore
	private Basket basket;
	
	@ManyToOne
	@JoinColumn(name = "itemId")
	private Item item;
	
	@Column(name = "quantity")
	private int quantity;

	@Column(name = "size")
    private String size;
	
	public BasketItem() {
		super();
	}

	public BasketItem(Basket basket, Item item, int quantity, String size) {
		super();
		this.basket = basket;
		this.item = item;
		this.quantity = quantity;
		this.size = size;
	}

	
	public int getBasketItemId() {
		return basketItemId;
	}

	public void setBasketItemId(int basketItemId) {
		this.basketItemId = basketItemId;
	}

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
	
}
