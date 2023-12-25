package com.flipkart.dao.Impl;

import com.flipkart.custom_exceptions.DatabaseException;
import com.flipkart.dao.DBConnection;
import com.flipkart.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * Responsible for all the user details in the database.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class UserDAOImpl {

    private static UserDAOImpl userServiceInstance;

    /**
     * <p>
     * Default constructor of the UserDAOImpl class. kept private to restrict from creating object from outside of this class.
     * </p>
     */
    private UserDAOImpl() {}

    /**
     * <p>
     * Creates a single object of UserDAOImpl Class and returns it.
     * </p>
     *
     * @return returns the single instance of UserDAOImpl Class.
     */
    public static UserDAOImpl getInstance() {
        if (null == userServiceInstance) {
            userServiceInstance = new UserDAOImpl();
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
        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("insert into users(name, mobile_number, email, password) values(?, ?, ?, crypt(?,gen_salt('bf'))) returning id")) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getMobileNumber());
            preparedStatement.setString(3, user.getEmailId());
            preparedStatement.setString(4, user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            final int userId = resultSet.getInt(1) ;

            user.setUserId(userId);

            return true;
        } catch (SQLException exception) {
            return false;
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
    public User getExistingUser(final String mobileNumber,final String email, final String password) {
        String query ;

        if (Objects.isNull(email)) {
            query = "select id, name, mobile_number, email, password from users where mobile_number=? and password=crypt(?,password)";
        } else {
            query = "select id, name, mobile_number, email, password from users where email=? and password=crypt(?,password)";
        }

        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(query)) {
            if (Objects.isNull(email)) {
                preparedStatement.setString(1, mobileNumber);
            } else {
                preparedStatement.setString(1, email);
            }
            preparedStatement.setString(2,password);
            final ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }
            final User user = new User();

            user.setUserId(resultSet.getInt(1));
            user.setName(resultSet.getString(2));
            user.setMobileNumber(resultSet.getString(3));
            user.setEmailId(resultSet.getString(4));
            user.setPassword(resultSet.getString(5));

            return user;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Updates the details of the user.
     * </p>
     *
     * @param user Refers the current {@link User}.
     */
    public void updateUser(final User user) {
        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("update users set name=? ,email=?, password=?, mobile_number=? where id =?")) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmailId());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getMobileNumber());
            preparedStatement.setInt(5, user.getUserId());
            preparedStatement.executeUpdate();
            DBConnection.getConnection().commit();
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
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
        final List<String> addresses = new ArrayList<>();

        try(final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("select address from address join users on users.id=address.user_id where user_id =?")) {
            preparedStatement.setInt(1, user.getUserId());
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final String address = resultSet.getString(1);
               addresses.add(address);
            }
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }

        return addresses;
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
        try(final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("insert into address(user_id, address) values (?,?)")) {
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setString(2, address);
            preparedStatement.executeUpdate();
            DBConnection.getConnection().commit();
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }
}
