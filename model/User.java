package com.flipkart.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * Represents an individual user who can register, login, and place the orders. It encapsulates all the user related information.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class User {

    private static int uniqueUserId = 1;
    private String name;
    private String emailId;
    private String mobileNumber;
    private String password;
    private int userId;
    private int cartId;
    private int wishlistId;
    private List<String> addresses;

    public User() {
        this.userId = uniqueUserId++;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setMobileNumber(final String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public int getCartId() {
        return cartId;
    }

    public void addAddress(String address) {
        if (Objects.isNull(addresses)) {
            addresses = new ArrayList<>();
        }
        addresses.add(address);
    }

    public List<String> getAddresses() {
        return addresses;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (null == object) {
            return false;
        }

        if (getClass() != object.getClass()) {
            return false;
        }
        final User user = (User) object;

        return emailId.equals(user.emailId)
                && mobileNumber.equals(user.mobileNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailId, mobileNumber);
    }
}