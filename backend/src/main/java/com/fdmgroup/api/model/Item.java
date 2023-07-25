package com.fdmgroup.api.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_ID_GEN")
	@SequenceGenerator(name = "ITEM_ID_GEN", sequenceName = "ITEM_ID_SEQ", allocationSize = 1)
	private int itemId;

	@NotBlank(message = "Item name must not be left blank.")
	@Size(min = 2, max = 100, message = "Item name must be longer than 2 characters and less than 100 characters.")
	private String name;

	@NotBlank(message = "Item description must not be left blank.")
	@Size(min = 2, max = 100, message = "Item description must be longer than 2 characters and less then 100 characters.")
	private String description;

	@NotBlank(message = "Item type must not be left blank.")
	@Size(min = 2, max = 100, message = "Item type must be longer than 2 characters and less than 100 characters.")
	private String itemType;

	@NotBlank(message = "Item size must not be left blank.")
	@Size(min = 1, max = 50, message = "Item size must be longer than 1 character and less than 50 characters.")
	private String size;

	@DecimalMax(value = "100000", message = "Item price must be less than 100,000.")
	@DecimalMin(value = "1", message = "Item price must be more than 1.")
	private double price;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private Favourites favourite;

	public Item() {
		super();
	}

	public Item(String name, String description, String itemType, String size, double price) {
		super();
		this.name = name;
		this.description = description;
		this.itemType = itemType;
		this.size = size;
		this.price = price;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
