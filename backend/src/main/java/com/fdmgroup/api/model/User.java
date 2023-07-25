package com.fdmgroup.api.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

import java.util.Objects;

import javax.validation.constraints.NotBlank;

@Entity
@Table(name="users")
public class User {

	// ATTRIBUTES

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_GEN")
	@SequenceGenerator(name = "USER_ID_GEN", sequenceName = "USER_ID_SEQ", allocationSize = 1)
	private Long userId;

	@NotBlank(message = "First name is required")
	@Column(nullable = false)
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Column( nullable = false)
	private String lastName;

	@NotBlank(message = "Email is required")
	@Column( nullable = false, unique = true)
	private String email;

	@NotBlank(message = "Password is required")
	@Column( nullable = false)
	private String password;

	@NotBlank(message = "Address is required")
	@Column( nullable = false)
	private String address;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	private Favourites favourites;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	private Basket basket;

	// CONSTRUCTORS
	public User() {
		super();
	}

	public User(Long userId, @NotBlank(message = "First name is required") String firstName,
			@NotBlank(message = "Last name is required") String lastName,
			@NotBlank(message = "Email is required") String email,
			@NotBlank(message = "Password is required") String password,
			@NotBlank(message = "Address is required") String address) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.address = address;
	}

	// GETTERS AND SETTERS

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public Favourites getFavourites() {
		return favourites;
	}

	public void setFavourites(Favourites favourites) {
		this.favourites = favourites;
	}

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

	// OTHER METHODS

	@Override
	public int hashCode() {
		return Objects.hash(address, email, firstName, lastName, password, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(address, other.address) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password) && userId == other.userId;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", address=" + address + "]";
	}

}
