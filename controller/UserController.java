package com.flipkart.controller;

import com.flipkart.model.User;
import com.flipkart.service.impl2.UserServiceImpl;

import java.util.List;

/**
 * <p>
 * Interacts between UserView and UserService for creating new user and getting existing user for login.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class UserController {

    private static UserController userControllerInstance;
    private static final UserServiceImpl USER_SERVICE = UserServiceImpl.getInstance();

    /**
     * <p>
     * Default constructor of UserController class. Kept private to restrict from creating object outside this class.
     * </p>
     */
    private UserController() {}

    /**
     * <p>
     * Creates a single object of UserController class and returns it.
     * </p>
     *
     * @return the single instance of UserController class.
     */
    public static synchronized UserController getInstance() {
        if (null == userControllerInstance) {
            userControllerInstance = new UserController();
        }

        return userControllerInstance;
    }

    /**
     * <p>
     * Checks if the user already exists. if not then creates a new user.
     * </p>
     *
     * @return true if the user created or false if user already exists.
     * @param user Refers the {@link User}to be created.
     */
    public boolean createUser(final User user) {
        return USER_SERVICE.createNewUser(user);
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
        return USER_SERVICE.getExistingUser(mobileNumber, emailId, password);
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
        USER_SERVICE.addAddress(user, address);
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
        return USER_SERVICE.getAddresses(user);
    }

    /**
     * <p>
     * Updates the details of the user.
     * </p>
     *
     * @param user Refers the current {@link User}.
     */
    public void updateUser(final User user) {
        USER_SERVICE.updateUser(user);
    }
}
