package com.fdmgroup.api.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Basket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASKET_ID_GEN")
	@SequenceGenerator(name = "BASKET_ID_GEN", sequenceName = "BASKET_ID_SEQ", allocationSize = 1)
	private int basketId;
	
	@OneToMany(mappedBy = "basket")
	private List<BasketItem> basketItems = new ArrayList<>();
	
	private double basketTotal;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "userId")
	private User user;
	
	
	public Basket() {
		super();
	}

	
	public Basket(List<BasketItem> basketItems, double basketTotal, User user) {
		super();
		this.basketItems = basketItems;
		this.basketTotal = basketTotal;
		this.user = user;
	}


	public int getBasketId() {
		return basketId;
	}


	public void setBasketId(int basketId) {
		this.basketId = basketId;
	}


	public List<BasketItem> getBasketItems() {
		return basketItems;
	}


	public void setBasketItems(List<BasketItem> basketItems) {
		this.basketItems = basketItems;
	}


	public double getBasketTotal() {
		return basketTotal;
	}


	public void setBasketTotal(double basketTotal) {
		this.basketTotal = basketTotal;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


}
