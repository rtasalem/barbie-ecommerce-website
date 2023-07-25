package com.fdmgroup.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fdmgroup.api.model.User;
import com.fdmgroup.api.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service class that handles user-related operations.
 */
@Service
public class UserService {

    private final static Logger log = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Finds a user by email and password for login functionality.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     * @return The user object if a valid user is found with the given email and password,
     *         or null if no valid user is found.
     */
    public User login(String email, String password) {
        log.info("Entering login method");
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                log.info("Exiting login method with a valid user");
                return user;
            }
        }
        log.info("Exiting login method with no valid user");
        return null;
    }

    /**
     * Creates a new user account if the email is not already used.
     *
     * @param user The User object containing user details.
     * @return The newly created user object if the account is successfully created, 
     *         or null if a user with the same email already exists.
     */
    public User createAccount(User user) {
        log.info("Entering createAccount method");
        if (!userRepository.existsByEmail(user.getEmail())) {
            // Assuming userId is generated elsewhere
            User savedUser = userRepository.save(user);
            log.info("Exiting createAccount method with a new user account created");
            return savedUser;
        }
        log.info("Exiting createAccount method - User already exists with the same email");
        return null;
    }

    /**
     * Logs out the user (No implementation needed).
     */
    public void logout() {
        log.info("Entering logout method");
        // Implement logout logic if necessary
        log.info("Exiting logout method");
    }

    /**
     * Updates user details with the provided information.
     *
     * @param userId The ID of the user to be updated.
     * @param firstName The updated first name.
     * @param lastName The updated last name.
     * @param address The updated address.
     * @return The updated User object if the user is found and updated successfully, 
     *         or null if the user is not found.
     */
    public User updateUserDetails(Long userId, String firstName, String lastName, String address) {
        log.info("Entering updateUserDetails method");

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAddress(address);
            User updatedUser = userRepository.save(user);
            log.info("Exiting updateUserDetails method with user details updated");
            return updatedUser;
        }

        log.info("Exiting updateUserDetails method - User not found");
        return null;
    }

    /**
     * Checks if the given email is already used by an existing user.
     *
     * @param email The email to be checked.
     * @return true if the email is already used, false otherwise.
     */
    public boolean isEmailUsed(String email) {
        log.info("Entering isEmailUsed method");

        // Check if the email exists in the database using the UserRepository
        Optional<User> userOptional = userRepository.findByEmail(email);
        boolean emailUsed = userOptional.isPresent();

        log.info("Exiting isEmailUsed method");
        return emailUsed;
    }

    /**
     * Retrieves a list of all users from the database.
     *
     * @return The list of all users.
     */
    public List<User> getAllUsers() {
        log.info("Entering getAllUsers method");
        List<User> users = userRepository.findAll();
        log.info("Exiting getAllUsers method");
        return users;
    }

    /**
     * Deletes a user with the specified ID.
     *
     * @param userId The ID of the user to be deleted.
     * @return true if the user is deleted successfully, false if the user is not found.
     */
    public boolean deleteUser(Long userId) {
        log.info("Entering deleteUser method");
        // Check if the user exists
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            log.info("Exiting deleteUser method");
            return false; // User not found
        }

        // Delete the user
        userRepository.deleteById(userId);
        log.info("Exiting deleteUser method");
        return true; // Deletion successful
    }
}
