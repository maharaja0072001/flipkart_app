package com.flipkart.controller;

import com.flipkart.model.User;
import com.flipkart.model.Wishlist;
import com.flipkart.model.product.Product;
import com.flipkart.service.impl2.WishlistServiceImpl;

/**
 * <p>
 * Responsible for handling requests and responses from WishlistView clas and WishlistService class.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class WishlistController {

    private static WishlistController wishlistControllerInstance;
    private static final WishlistServiceImpl WISHLIST_SERVICE = WishlistServiceImpl.getInstance();

    /**
     *<p>
     * Default constructor of the WishlistController class. kept private to restrict from creating object from outside of this class.
     * </p>
     */
    private WishlistController() {}

    /**
     * <p>
     * Creates a single object of WishlistController Class and returns it.
     * </p>
     *
     * @return returns the single instance of WishlistController Class.
     */
    public static synchronized WishlistController getInstance() {
        if (null == wishlistControllerInstance) {
            wishlistControllerInstance = new WishlistController();
        }

        return wishlistControllerInstance;
    }

    /**
     * <p>
     * Adds the product to the wishlist of the specified user.
     * </p>
     *
     * @param product Refers the product to be added
     * @param user Refers the user
     * @return the wishlist of the user.
     */
    public boolean addToWishlist(final Product product, final User user) {
        return WISHLIST_SERVICE.addToWishlist(product, user);
    }

    /**
     * <p>
     * Removes the product from the wishlist of the specified user.
     * * </p>
     *
     * @param product Refers the product to be removed.
     * @param user Refers the user
     */
    public void removeItemFromWishlist(final Product product, final User user) {
        WISHLIST_SERVICE.removeFromWishlist(product, user);
    }

    /**
     * <p>
     * Gets the wishlist of the specified user id and returns it.
     * </p>
     *
     * @param user Refers the user who owns the cart.
     * @return the wishlist of the user.
     */
    public Wishlist getUserWishlist(final User user) {
        return WISHLIST_SERVICE.getUserWishlist(user);
    }
}
