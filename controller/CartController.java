package com.flipkart.controller;

import com.flipkart.model.Cart;
import com.flipkart.model.User;
import com.flipkart.model.product.Product;
import com.flipkart.service.impl2.CartServiceImpl;

/**
 * <p>
 * Interacts between CartView and CartService for adding , removing from the cart of the user.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class CartController {

    private static CartController cartControllerInstance;
    private static final CartServiceImpl CART_SERVICE = CartServiceImpl.getInstance();

    /**
     *<p>
     * Default constructor of the CartController class. kept private to restrict from creating object from outside of this class.
     * </p>
     */
    private CartController() {}

    /**
     * <p>
     * Creates a single object of CartController Class and returns it.
     * </p>
     *
     * @return returns the single instance of CartController Class.
     */
    public static synchronized CartController getInstance() {
        if (null == cartControllerInstance) {
            cartControllerInstance = new CartController();
        }

        return cartControllerInstance;
    }

    /**
     * <p>
     * Adds the product to the cart of the specified user.
     * * </p>
     *
     * @param product Refers the product to be added
     * @param user Refers the user
     */
    public boolean addToCart(final Product product, final User user) {
        return CART_SERVICE.addItemToCart(product, user);
    }

    /**
     * <p>
     * Removes the product from the cart of the specified user.
     * * </p>
     *
     * @param product Refers the product to be added
     * @param user Refers the user
     */
    public void removeItemFromCart(final Product product, final User user) {
        CART_SERVICE.removeItemFromCart(product, user);
    }

    /**
     * <p>
     * Gets the cart of the specified user id and returns it.
     * </p>
     *
     * @param user Refers the user who owns the cart.
     * @return the cart of the user.
     */
    public Cart getUserCart(final User user) {
        return CART_SERVICE.getUserCart(user);
    }

}



