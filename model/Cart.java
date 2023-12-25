package com.flipkart.model;

import com.flipkart.model.product.Product;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * Represents a cart for the user to add the items to the cart and placing the orders.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class Cart {

    private static int uniqueId = 1;
    private List<Product> cartItems;
    private float totalAmountInCart;
    private int id;
    private User user;

    /**
     * <p>
     * Constructor of the Cart class.
     * </p>
     *
     * @param user refers the user who owns the cart.
     */
    public Cart(final User user) {
        this.user = user;
        id = uniqueId++;
        user.setCartId(id);
    }

    public boolean addToCart(final Product product) {
        if (null == cartItems) {
            cartItems = new LinkedList<>();
        }

        if (!cartItems.contains(product)) {
            totalAmountInCart += product.getPrice();

            return cartItems.add(product);
        }

        return false;
    }

    public void removeFromCart(final Product item) {
        cartItems.remove(item);
        totalAmountInCart -= item.getPrice();
    }

    public List<Product> getCartItems() {
        return cartItems;
    }

    public float getTotalAmount() {
        return totalAmountInCart;
    }

    public void setId(final int id) {
        this.id = id;
    }
}
