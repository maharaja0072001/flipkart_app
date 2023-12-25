package com.flipkart.model;

import com.flipkart.model.product.Product;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * Represents a wishlist for the user to add the items to the wishlist and can move them to cart for ordering.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class Wishlist {

    private static int uniqueId = 1;
    private int id;
    private User user;
    private List<Product> wishlistItems;

    /**
     * <p>
     * Constructor of Wishlist class.
     * </p>
     *
     * @param user refers the {@link User} who owns the Wishlist.
     */
    public Wishlist(final User user) {
        this.user = user;
        id = uniqueId++;

        user.setWishlistId(id);
    }

    /**
     * <p>
     * Adds the specific product to the wishlist
     * </p>
     *
     * @param product Refers {@link Product} to be added to the wishlist.
     * @return true if the product is added.
     */
    public boolean addItemToWishlist(final Product product) {
        if (null == wishlistItems) {
            wishlistItems =  new LinkedList<>();
        }

        return wishlistItems.add(product);
    }

    /**
     * <p>
     * Removes the specific product from the wishlist
     * </p>
     *
     * @param product Refers {@link Product} the product to be removed.
     */
    public void removeItemFromWishlist(final Product product) {
        wishlistItems.remove(product);
    }

    /**
     * <p>
     * Gets the products in the wishlist and returns it.
     * </p>
     *
     * @return all the {@link Product} in the wishlist.
     */
    public List<Product> getWishlistItems() {
        return wishlistItems;
    }

    public void setId(final int id) {
        this.id = id;
    }
}
