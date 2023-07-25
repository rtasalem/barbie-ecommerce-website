package com.fdmgroup.api.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Favourites {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FAVOURITES_ID_GEN")
	@SequenceGenerator(name = "FAVOURITES_ID_GEN", sequenceName = "FAVOURITES_ID_SEQ", allocationSize = 1)

	// possibly using a foreign key from the user class, annotation in the item and
	// user classes as well
	// one user has one fav basket
	private int favourites_id; // (questionable rn) possibly favourties_id

	@OneToMany(cascade = CascadeType.PERSIST)
	// reason for <Item> is so the info can be taken from the Item Model class.
	private List<Item> favourites_list;

	@OneToOne(cascade = CascadeType.PERSIST)
	private User userId; // got to check if this annotation matches with Andrea
	// one fav to one user

	public Favourites() {
		super();
		this.favourites_list = new ArrayList<>();
	}

	public int getFavourites_id() {
		return favourites_id;
	}

	public void setFavourites_id(int favourties_id) {
		this.favourites_id = favourties_id;
	}

	public List<Item> getFavourites_list() {
		return favourites_list;
	}

	public void setFavourites_list(List<Item> favourites_list) {
		this.favourites_list = favourites_list;
	}

}
