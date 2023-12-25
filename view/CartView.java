package com.flipkart.view;

import com.flipkart.controller.CartController;
import com.flipkart.model.Cart;
import com.flipkart.model.User;
import com.flipkart.model.product.Product;

import java.util.List;

/**
 * <p>
 * Responsible for viewing the user cart and placing order.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class CartView extends View{

    private static CartView cartViewInstance;
    private static final CartController CART_CONTROLLER = CartController.getInstance();
    private static final OrderView ORDER_VIEW = OrderView.getInstance();

    /**
     * <p>
     * Default constructor of CartView class. Kept private to restrict from creating object outside this class.
     * </p>
     */
    private CartView() {}

    /**
     * <p>
     * Creates a single object of CartView class and returns it.
     * </p>
     *
     * @return the single instance of CartView class.
     */
    public static synchronized CartView getInstance() {
        if (null == cartViewInstance) {
            cartViewInstance = new CartView();
        }

        return cartViewInstance;
    }

    /**
     * <p>
     * Adds the specific product to the cart.
     * </p>
     *
     * @param user    Refers the current {@link User}
     * @param product Refers the {@link Product} to be added to the cart
     */
    public void addToCart(final Product product, final User user) {
        if (0 == product.getQuantity()) {
            System.out.println("The item is out of stock");

            return;
        }

        if (CART_CONTROLLER.addToCart(product, user)) {
            System.out.println("Item added to the cart");
        } else {
            System.out.println("Item is already in the cart");
        }
    }

    /**
     * <p>
     * Shows the items presented in the cart and user can place the order of the items present in the cart.
     * </p>
     *
     * @param user Refers the current {@link User}
     */
    public void viewCart(final User user) {
        final Cart cart = CART_CONTROLLER.getUserCart(user);

        if (null == cart || null == cart.getCartItems()) {
            System.out.println("Cart is empty");

            return;
        }
        final List<Product> cartItems = cart.getCartItems();

        System.out.println("Cart :");
        showItems(cartItems);
        System.out.println(String.format("Total amount : Rs-%.2f", cart.getTotalAmount()));
        final Integer productId = getChoice("Enter the product id to order the item or to remove from cart: [Press '$' to go back] ");

        if (null == productId) {
            return;
        }

        if (!(productId > cartItems.size() || productId <= 0)) {
            final Product item = cartItems.get(productId - 1);

            loop:
            while (true) {
                System.out.println("Enter '1' to place order or '2' to remove from cart.");

                final Integer choice = getChoice("Enter a choice :");

                if (null == choice) {
                    return;
                }

                switch (choice) {
                    case 1:
                        if (!(0 == item.getQuantity())) {
                            ORDER_VIEW.placeOrder(item, user);
                        } else {
                            System.out.println("Item is out of stock");
                        }
                        break loop;
                    case 2:
                        CART_CONTROLLER.removeItemFromCart(item, user);
                        System.out.println("Item removed");
                        break loop;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        } else {
            System.out.println("Invalid product id");
            viewCart(user);
        }
    }
}

