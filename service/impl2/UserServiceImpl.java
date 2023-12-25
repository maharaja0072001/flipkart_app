package com.flipkart.service.impl2;

import com.flipkart.dao.Impl.UserDAOImpl;
import com.flipkart.service.UserService;
import com.flipkart.model.User;

import java.util.List;

/**
 * <p>
 * Provides the service for the User.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class UserServiceImpl implements UserService {

    private static final UserDAOImpl USER_DAO = UserDAOImpl.getInstance();
    private static UserServiceImpl userService;

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
        if (null == userService) {
            userService = new UserServiceImpl();
        }

        return userService;
    }

    /**
     * <p>
     * Checks if the user already exists. if not then creates a new user.
     * </p>
     *
     * @return true if the user created or false if user already exists.
     * @param user Refers the {@link User}to be created.
     */
    @Override
    public boolean createNewUser(final User user) {
        return USER_DAO.createNewUser(user);
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
    @Override
    public User getExistingUser(final String mobileNumber, final String emailId, final String password) {
        return USER_DAO.getExistingUser(mobileNumber, emailId, password);
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
        USER_DAO.addAddress(user, address);
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
        return USER_DAO.getAddresses(user);
    }

    /**
     * <p>
     * Updates the details of the user.
     * </p>
     *
     * @param user Refers the current {@link User}.
     */
    public void updateUser(final User user) {
        USER_DAO.updateUser(user);
    }
}
