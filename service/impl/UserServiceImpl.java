package com.flipkart.service.impl;

import com.flipkart.model.User;
import com.flipkart.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * Provides the service for the User.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class UserServiceImpl implements UserService {

    private static UserServiceImpl userServiceInstance;
    private static final Map<String, User> USERS = new HashMap<>();

    /**
     * <p>
     * Default constructor of the UserServiceImpl class. kept private to restrict from creating object from outside of this class.
     * </p>
     */
    private UserServiceImpl() {}

    /**
     * <p>
     * Creates a single object of UserServiceImpl Class and returns it.
     * </p>
     *
     * @return returns the single instance of UserServiceImpl Class.
     */
    public static UserServiceImpl getInstance() {
        if (null == userServiceInstance) {
            userServiceInstance = new UserServiceImpl();
        }

        return userServiceInstance;
    }

    /**
     * <p>
     * Checks if the user already exists. if not then creates a new user.
     * </p>
     *
     * @return true if the user created or false if user already exists.
     * @param user Refers the {@link User}to be created.
     */
    public boolean createNewUser(final User user) {
        if (USERS.containsKey(user.getMobileNumber())) {
            return false;
        } else {
            USERS.put(user.getMobileNumber(), user);

            return true;
        }
    }

    /**
     * <p>
     * Gets the existing user by the given credentials.
     * </p>
     *
     * @param mobileNumber Refers the mobile number of the user
     * @param password Refers the password of the user.
     * @return {@link User} if the credentials are correct and the user exists or null otherwise.
     */
    public User getExistingUser(final String mobileNumber, final String emailId, final String password) {
        User user = null;

        if (Objects.isNull(emailId)) {
            user = USERS.get(mobileNumber);
        } else {
            for (final User existingUser : USERS.values()) {
                if (existingUser.getEmailId().equals(emailId)) {
                    user = existingUser;
                }
            }
        }

        if (null != user && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }

    /**
     * <p>
     * Adds the address of the user.
     * </p>
     *
     * @param user Refers the current {@link User} .
     * @param address Refers the address to be added.
     */
    public void addAddress(final User user, final String address) {
        user.addAddress(address);
    }

    /**
     * <p>
     * Gets all the addresses of the user.
     * </p>
     *
     * @param user Refers the current {@link User}.
     * @return the list of all the address.
     */
    public List<String> getAddresses(final User user) {
        return user.getAddresses();
    }

    /**
     * <p>
     * Updates the details of the user.
     * </p>
     *
     * @param user Refers the current {@link User}.
     */
    public void updateUser(final User user) {
        USERS.put(user.getMobileNumber(), user);
    }

}