package com.flipkart.service.impl;

import com.flipkart.model.User;
import com.flipkart.model.Wishlist;
import com.flipkart.model.product.Product;
import com.flipkart.service.WishlistService;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Provides the service for the Wishlist.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class WishlistServiceImpl implements WishlistService {

    private static WishlistServiceImpl wishlistServiceInstance;
    private static final Map<Integer, Wishlist> WISHLISTS = new HashMap<>();

    /**
     * <p>
     * Default constructor of the WishlistServiceImpl class. kept private to restrict from creating object from outside of this class.
     * </p>
     */
    private WishlistServiceImpl() {}

    /**
     * <p>
     * Creates a single object of WishlistServiceImpl Class and returns it.
     * </p>
     *
     * @return returns the single instance of WishlistServiceImpl Class.
     */
    public static WishlistServiceImpl getInstance() {
        if (null == wishlistServiceInstance) {
            wishlistServiceInstance = new WishlistServiceImpl();
        }

        return wishlistServiceInstance;
    }

    /**
     * <p>
     * Adds the specific product to the wishlist
     * </p>
     *
     * @param user Refers the current {@link User}
     * @param product Refers {@link Product} to be added to the wishlist.
     * @return true if the product is added.
     */
    public boolean addToWishlist(final Product product, final User user) {
        if (!WISHLISTS.containsKey(user.getUserId())) {
            WISHLISTS.put(user.getUserId(), new Wishlist(user));
        }
        final Wishlist wishlist = WISHLISTS.get(user.getUserId());

        return wishlist.addItemToWishlist(product);
    }

    /**
     * <p>
     * Removes the specific product from the wishlist
     * </p>
     *
     * @param user Refers the current {@link User}
     * @param product Refers {@link Product} the product to be removed.
     */
    public void removeFromWishlist(final Product product, final User user) {
        final Wishlist wishlist = WISHLISTS.get(user.getUserId());

        wishlist.removeItemFromWishlist(product);
    }

    /**
     * <p>
     * Gets the wishlist of the current user and returns it.
     * </p>
     *
     * @param user Refers the current {@link User}.
     * @return the {@link Wishlist} of the user.
     */
    public Wishlist getUserWishlist(final User user) {
        return WISHLISTS.get(user.getUserId());
    }
}
