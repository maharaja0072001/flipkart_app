package com.flipkart.service.impl2;

import com.flipkart.dao.Impl.CartDAOImpl;
import com.flipkart.model.Cart;
import com.flipkart.model.User;
import com.flipkart.model.product.Product;
import com.flipkart.service.CartService;

/**
 * <p>
 * Provides the service for the Cart of the user.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class CartServiceImpl implements CartService {

    private static final CartDAOImpl CART_DAO = CartDAOImpl.getInstance();
    private static CartServiceImpl cartServiceInstance ;

    /**
     * <p>
     * Default constructor of the CartServiceImpl class. kept private to restrict from creating object from outside of this class.
     * </p>
     */
    private CartServiceImpl() {}

    /**
     * <p>
     * Creates a single object of CartServiceImpl Class and returns it.
     * </p>
     *
     * @return returns the single instance of CartServiceImpl Class.
     */
    public static synchronized CartServiceImpl getInstance() {
        if (null == cartServiceInstance) {
            cartServiceInstance = new CartServiceImpl();
        }

        return cartServiceInstance;
    }

    /**
     * <p>
     * Adds the product to the cart of the user.
     * </p>
     *
     * @param product Refers the {@link Product} to be added to the cart.
     * @param user Refers the current {@link User}.
     */
    public boolean addItemToCart(final Product product, final User user) {
        return CART_DAO.addItemToCart(product, user);
    }

    /**
     * <p>
     * Removes the specific product from the cart.
     * </p>
     *
     * @param user Refers the current {@link User}
     * @param product Refers the {@link Product} to be removed from the cart.
     */
    public void removeItemFromCart(final Product product, final User user) {
        CART_DAO.removeItemFromCart(product, user);
    }

    /**
     * <p>
     * Gets the cart of the current user and returns it.
     * </p>
     *
     * @param user Refers the current {@link User}
     * @return the {@link Cart} of the user.
     */
    public Cart getUserCart(final User user) {
        return CART_DAO.getUserCart(user);
    }
}
