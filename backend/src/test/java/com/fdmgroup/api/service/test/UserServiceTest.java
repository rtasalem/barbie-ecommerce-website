package com.fdmgroup.api.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.api.model.User;
import com.fdmgroup.api.repository.UserRepository;
import com.fdmgroup.api.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	private User testUser;

	@BeforeEach
	public void setUp() {

		// Create a test user for use in the test cases
		testUser = new User();
		testUser.setUserId(1L);
		testUser.setFirstName("John");
		testUser.setLastName("Doe");
		testUser.setEmail("john.doe@example.com");
		testUser.setPassword("password123");
		testUser.setAddress("123 Main Street");
	}

	@Test
    void testLogin_Success() {
        // Stub the userRepository to return the test user when findByEmail is called
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(testUser));

        // Perform the login and assert the result
        User loggedInUser = userService.login("john.doe@example.com", "password123");
        assertNotNull(loggedInUser);
        assertEquals(testUser, loggedInUser);
    }

	@Test
    void testLogin_Failure() {
        // Stub the userRepository to return an empty Optional when findByEmail is called
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // Perform the login operation
        User loggedInUser = userService.login("nonexistent@example.com", "password123");

        // Verify that the userRepository method was called with the correct email
        verify(userRepository).findByEmail("nonexistent@example.com");

        // Verify that the login operation returns null for a non-existent user
        assertNull(loggedInUser);
    }

	@Test
    void testCreateAccount_Success() {
        // Stub the userRepository to return false when existsByEmail is called
        when(userRepository.existsByEmail("newuser@example.com")).thenReturn(false);

        // Stub the userRepository to return the test user when save is called
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Create a new user
        User newUser = new User();
        newUser.setFirstName("New");
        newUser.setLastName("User");
        newUser.setEmail("newuser@example.com");
        newUser.setPassword("newpassword");
        newUser.setAddress("456 New Street");

        // Perform the createAccount operation
        User createdUser = userService.createAccount(newUser);

        // Verify that the userRepository methods were called with the correct email and user
        verify(userRepository).existsByEmail("newuser@example.com");
        verify(userRepository).save(newUser);

        // Verify that the createdUser matches the testUser
        assertNotNull(createdUser);
        assertEquals(testUser, createdUser);
    }

	@Test
    void testCreateAccount_UserExists() {
        // Stub the userRepository to return true when existsByEmail is called
        when(userRepository.existsByEmail("john.doe@example.com")).thenReturn(true);

        // Create a new user with an existing email
        User newUser = new User();
        newUser.setFirstName("New");
        newUser.setLastName("User");
        newUser.setEmail("john.doe@example.com");
        newUser.setPassword("newpassword");
        newUser.setAddress("456 New Street");

        // Perform the createAccount operation
        User createdUser = userService.createAccount(newUser);

        // Verify that the userRepository method was called with the correct email
        verify(userRepository).existsByEmail("john.doe@example.com");

        // Verify that the createdUser is null due to the existing email
        assertNull(createdUser);
    }

	@Test
	public void testUpdateUserDetails_WithValidUserId_ShouldUpdateUserDetails() {
		// Arrange
		Long userId = 1L;
		String firstName = "John";
		String lastName = "Doe";
		String address = "123 Main Street";

		User existingUser = new User();
		existingUser.setUserId(userId);
		existingUser.setFirstName("Alice");
		existingUser.setLastName("Smith");
		existingUser.setAddress("456 Park Avenue");

		when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
		when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

		// Act
		User updatedUser = userService.updateUserDetails(userId, firstName, lastName, address);

		// Assert
		assertNotNull(updatedUser);
		assertEquals(userId, updatedUser.getUserId());
		assertEquals(firstName, updatedUser.getFirstName());
		assertEquals(lastName, updatedUser.getLastName());
		assertEquals(address, updatedUser.getAddress());
	}

	@Test
	public void testUpdateUserDetails_WithInvalidUserId_ShouldReturnNull() {
		// Arrange
		Long userId = 999L; // Non-existing user ID
		String firstName = "John";
		String lastName = "Doe";
		String address = "123 Main Street";

		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		// Act
		User updatedUser = userService.updateUserDetails(userId, firstName, lastName, address);

		// Assert
		assertNull(updatedUser);
	}

	@Test
	void testLogout_ShouldCallLogoutMethod() {
		// Call the logout method
		userService.logout();

	}

	@Test
	void testIsEmailUsed_EmailExists_ShouldReturnTrue() {
	    // Stub the userRepository to return the test user when findByEmail is called
	    when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(testUser));

	    // Perform the isEmailUsed operation
	    boolean emailUsed = userService.isEmailUsed("john.doe@example.com");

	    // Verify that the userRepository method was called with the correct email
	    verify(userRepository).findByEmail("john.doe@example.com");

	    // Verify that isEmailUsed returns true for an existing email
	    assertTrue(emailUsed);
	}

	@Test
	void testIsEmailUsed_EmailDoesNotExist_ShouldReturnFalse() {
	    // Stub the userRepository to return an empty Optional when findByEmail is called
	    when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

	    // Perform the isEmailUsed operation
	    boolean emailUsed = userService.isEmailUsed("nonexistent@example.com");

	    // Verify that the userRepository method was called with the correct email
	    verify(userRepository).findByEmail("nonexistent@example.com");

	    // Verify that isEmailUsed returns false for a non-existing email
	    assertFalse(emailUsed);
	}

	@Test
	public void testGetAllUsers() {
		// Create some sample User entities
		User user1 = new User(1L, "John", "Doe", "john.doe@example.com", "mypassword", "123 Main St");
		User user2 = new User(2L, "Jane", "Smith", "jane.smith@example.com", "password123", "456 Elm Avenue");

		List<User> userList = new ArrayList<>();
		userList.add(user1);
		userList.add(user2);

		// Mock the behavior of userRepository.findAll() to return the sample user list
		when(userRepository.findAll()).thenReturn(userList);

		// Call the method under test
		List<User> result = userService.getAllUsers();

		// Verify the result
		assertEquals(2, result.size());
		assertEquals(user1, result.get(0));
		assertEquals(user2, result.get(1));
	}

	@Test
    public void testGetAllUsersEmptyList() {
        // Mock the behavior of userRepository.findAll() to return an empty list
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        // Call the method under test
        List<User> result = userService.getAllUsers();

        // Verify the result
        assertEquals(0, result.size());
    }
	
	 @Test
	    void testDeleteUser_Success() {
	        // Arrange
	        Long userId = 1L;
	        User user = new User();
	        user.setUserId(userId);

	        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
	        doNothing().when(userRepository).deleteById(userId);

	        // Act
	        boolean isDeleted = userService.deleteUser(userId);

	        // Assert
	        assertTrue(isDeleted);
	        verify(userRepository, times(1)).findById(userId);
	        verify(userRepository, times(1)).deleteById(userId);
	    }

	    @Test
	    void testDeleteUser_UserNotFound() {
	        // Arrange
	        Long userId = 1L;

	        when(userRepository.findById(userId)).thenReturn(Optional.empty());

	        // Act
	        boolean isDeleted = userService.deleteUser(userId);

	        // Assert
	        assertFalse(isDeleted);
	        verify(userRepository, times(1)).findById(userId);
	        verify(userRepository, never()).deleteById(any());
	    }

}
