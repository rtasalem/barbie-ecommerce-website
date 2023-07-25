package com.fdmgroup.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.fdmgroup.api.exception.EmailAlreadyUsedException;
import com.fdmgroup.api.exception.InvalidCredentialsException;
import com.fdmgroup.api.exception.MissingRequiredFieldException;
import com.fdmgroup.api.model.User;
import com.fdmgroup.api.service.UserService;

/**
 * Controller class for handling user-related endpoints.
 */
@RestController
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Endpoint for user login functionality.
	 *
	 * @param email    The email of the user.
	 * @param password The password of the user.
	 * @return A ResponseEntity with a success message if the login is successful,
	 *         or an InvalidCredentialsException if the login fails.
	 */
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam("email") String email,
			@RequestParam("password") String password) {
		log.info("Entering login method");
		User user = userService.login(email, password);
		if (user != null) {
			log.info("Exiting login method with successful login");
			return ResponseEntity.ok("Login successful.");
		} else {
			log.info("Exiting login method with invalid credentials");
			throw new InvalidCredentialsException("Invalid credentials for email: " + email);
		}
	}

	/**
	 * Endpoint for creating a new user account.
	 *
	 * @param user The User object containing user details.
	 * @return A ResponseEntity with a success message if the account is created
	 *         successfully, or a conflict status if the email is already used for
	 *         another account.
	 */
	@PostMapping("/create-account")
	public ResponseEntity<String> createAccount(@RequestBody User user) {
		log.info("Entering createAccount method");
		validateUser(user);

		if (userService.isEmailUsed(user.getEmail())) {
			log.info("Exiting createAccount method with email already used");
			throw new EmailAlreadyUsedException("Email is already used for an account.");
		}

		User createdUser = userService.createAccount(user);
		if (createdUser != null) {
			log.info("Exiting createAccount method with account creation success");
			return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully.");
		}

		log.info("Exiting createAccount method with account creation failure");
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Account already exists.");
	}

	/**
	 * Endpoint for user logout.
	 *
	 * @return A ResponseEntity with a success message for successful logout.
	 */
	@GetMapping("/logout")
	public ResponseEntity<String> logout() {
		log.info("Entering logout method");
		userService.logout();
		log.info("Exiting logout method");
		return ResponseEntity.ok("Logged out successfully.");
	}

	/**
	 * Endpoint for updating user details.
	 *
	 * @param userId    The ID of the user to be updated.
	 * @param firstName The updated first name.
	 * @param lastName  The updated last name.
	 * @param address   The updated address.
	 * @return A ResponseEntity with a success message if the update is successful,
	 *         or an internal server error status if the update fails.
	 */
	@PutMapping("/update-details/{userId}")
	public ResponseEntity<String> updateUserDetails(@PathVariable Long userId,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("address") String address) {

		log.info("Entering updateUserDetails method");
		User updatedUser = userService.updateUserDetails(userId, firstName, lastName, address);
		if (updatedUser != null) {
			log.info("Exiting updateUserDetails method with user details update success");
			return ResponseEntity.ok("User details updated successfully.");
		} else {
			log.info("Exiting updateUserDetails method with user details update failure");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user details.");
		}
	}

	/**
	 * Endpoint for retrieving a list of all users.
	 *
	 * @return A ResponseEntity with the list of all users if available, or a no
	 *         content status if the user list is empty.
	 */
	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers() {
		log.info("Entering getAllUsers method");
		List<User> users = userService.getAllUsers();
		if (users.isEmpty()) {
			log.info("Exiting getAllUsers method with no content");
			return ResponseEntity.noContent().build();
		} else {
			log.info("Exiting getAllUsers method with user list");
			return ResponseEntity.ok(users);
		}
	}

	/**
	 * Endpoint for deleting a user with the specified ID.
	 *
	 * @param userId The ID of the user to be deleted.
	 * @return A ResponseEntity with a success message if the deletion is
	 *         successful, or a not found status if the user is not found.
	 */
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
		log.info("Entering deleteUser method");
		boolean isDeleted = userService.deleteUser(userId);
		if (isDeleted) {
			log.info("Exiting deleteUser method with user deletion success");
			return ResponseEntity.ok("User deleted successfully.");
		} else {
			log.info("Exiting deleteUser method with user not found");
			return ResponseEntity.notFound().build();
		}
	}

	// Helper method to validate required fields in the User object
	public void validateUser(User user) {
		if (user.getFirstName() == null || user.getFirstName().isEmpty() || user.getLastName() == null
				|| user.getLastName().isEmpty() || user.getAddress() == null || user.getAddress().isEmpty()
				|| user.getEmail() == null || user.getEmail().isEmpty() || user.getPassword() == null
				|| user.getPassword().isEmpty()) {
			log.info("Required fields are missing. Throwing MissingRequiredFieldException");
			throw new MissingRequiredFieldException("Required fields are missing.");
		}
	}
}
