package com.fdmgroup.api.controller.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fdmgroup.api.controller.UserController;
import com.fdmgroup.api.exception.EmailAlreadyUsedException;
import com.fdmgroup.api.exception.InvalidCredentialsException;
import com.fdmgroup.api.exception.MissingRequiredFieldException;
import com.fdmgroup.api.model.User;
import com.fdmgroup.api.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.*;

//@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testLogin_Success() {
		// Arrange
		String email = "test@example.com";
		String password = "password";
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		when(userService.login(email, password)).thenReturn(user);

		// Act
		ResponseEntity<String> response = userController.login(email, password);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Login successful.", response.getBody());
	}

	@Test
	void testLogin_InvalidCredentialsException() {
		// Arrange
		String email = "test@example.com";
		String password = "wrongPassword";
		when(userService.login(email, password)).thenReturn(null);

		// Act & Assert
		assertThrows(InvalidCredentialsException.class, () -> userController.login(email, password));
	}

	@Test
	void testCreateAccount_Success() {
		// Arrange
		User user = new User();
		user.setEmail("test@example.com");
		user.setPassword("password");
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setAddress("123 Main St");
		when(userService.isEmailUsed(any())).thenReturn(false);
		when(userService.createAccount(any())).thenReturn(user);

		// Act
		ResponseEntity<String> response = userController.createAccount(user);

		// Assert
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Account created successfully.", response.getBody());
	}

	@Test
	void testCreateAccount_EmailAlreadyUsedException() {
		// Arrange
		User user = new User();
		user.setEmail("test@example.com");
		when(userService.isEmailUsed(any())).thenReturn(true);

		// Partially mock the userController to avoid the call to validateUser during
		// the test
		UserController userControllerSpy = Mockito.spy(userController);
		doNothing().when(userControllerSpy).validateUser(any());

		// Mock the UserService.createAccount to throw EmailAlreadyUsedException
		when(userService.createAccount(any()))
				.thenThrow(new EmailAlreadyUsedException("Email is already used for an account."));

		// Act & Assert
		assertThrows(EmailAlreadyUsedException.class, () -> userControllerSpy.createAccount(user));
	}

	@Test
	void testUpdateUserDetails_Success() {
		// Arrange
		Long userId = 1L;
		String firstName = "John";
		String lastName = "Doe";
		String address = "123 Main St";

		// Create a mock User object with the updated details
		User updatedUser = new User();
		updatedUser.setUserId(userId);
		updatedUser.setFirstName(firstName);
		updatedUser.setLastName(lastName);
		updatedUser.setAddress(address);

		// Mock the UserService.updateUserDetails to return the updatedUser object
		when(userService.updateUserDetails(eq(userId), eq(firstName), eq(lastName), eq(address)))
				.thenReturn(updatedUser);

		// Act
		ResponseEntity<String> response = userController.updateUserDetails(userId, firstName, lastName, address);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("User details updated successfully.", response.getBody());
	}

	@Test
	void testUpdateUserDetails_InternalServerError() {
		// Arrange
		Long userId = 1L;
		String firstName = "John";
		String lastName = "Doe";
		String address = "123 Main St";

		// Mock the UserService.updateUserDetails to return null, indicating a failed
		// update
		when(userService.updateUserDetails(eq(userId), eq(firstName), eq(lastName), eq(address))).thenReturn(null);

		// Act
		ResponseEntity<String> response = userController.updateUserDetails(userId, firstName, lastName, address);

		// Assert
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("Failed to update user details.", response.getBody());
	}

	@Test
	public void testLogout_Success() {
		// Arrange: No specific actions required, as logout() does not have a return
		// value.

		// Act
		ResponseEntity<String> response = userController.logout();

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Logged out successfully.", response.getBody());

		// Verify that the userService.logout() method was called once.
		verify(userService, times(1)).logout();
	}

	@Test
	void testCreateAccount_MissingRequiredFields() {
		// Arrange
		User user = new User();

		// Act & Assert
		assertThrows(MissingRequiredFieldException.class, () -> userController.createAccount(user));
	}

	@Test
	public void testGetAllUsers() {
		// Prepare test data
		List<User> users = new ArrayList<>();
		User user1 = new User(1L, "John", "Doe", "john.doe@example.com", "mypassword", "123 Main St");
		User user2 = new User(2L, "Jane", "Smith", "jane.smith@example.com", "password456", "456 Elm Avenue");
		users.add(user1);
		users.add(user2);

		// Mock the userService.getAllUsers() method
		Mockito.when(userService.getAllUsers()).thenReturn(users);

		// Call the controller method
		ResponseEntity<List<User>> responseEntity = userController.getAllUsers();

		// Assert the response
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(users, responseEntity.getBody());
	}

	@Test
	public void testGetAllUsersEmptyList() {  
		// Prepare an empty list of users
		List<User> emptyList = new ArrayList<>();

		// Mock the userService.getAllUsers() method to return an empty list
		Mockito.when(userService.getAllUsers()).thenReturn(emptyList);

		// Call the controller method
		ResponseEntity<List<User>> responseEntity = userController.getAllUsers();

		// Assert the response
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());
	}
	
	@Test
    public void testDeleteUser_Success() {
        Long userId = 1L;
        when(userService.deleteUser(userId)).thenReturn(true);

        ResponseEntity<String> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully.", response.getBody());
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    public void testDeleteUser_UserNotFound() {
        Long userId = 1L;
        when(userService.deleteUser(userId)).thenReturn(false);

        ResponseEntity<String> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).deleteUser(userId);
    }

}
