package com.flipkart.view;

import com.flipkart.controller.WishlistController;
import com.flipkart.model.User;
import com.flipkart.model.Wishlist;
import com.flipkart.model.product.Product;

import java.util.List;

/**
 * <p>
 * Responsible for viewing wishlist of the user and adding the item to the cart
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class WishlistView extends View{

    private static final WishlistController WISHLIST_CONTROLLER = WishlistController.getInstance();
    private static final CartView CART_VIEW = CartView.getInstance();
    private static WishlistView wishlistViewInstance;

    /**
     * <p>
     * Default constructor of WishlistView class. Kept private to restrict from creating object outside this class.
     * </p>
     */
    private WishlistView() {}

    /**
     * <p>
     * Creates a single object of WishlistView class and returns it.
     * </p>
     *
     * @return the single instance of WishlistView class.
     */
    public static synchronized WishlistView getInstance() {
        if (null == wishlistViewInstance) {
            wishlistViewInstance= new WishlistView();
        }

        return wishlistViewInstance;
    }

    /**
     * <p>
     * Shows the wishlist items to the user and the user can add the items to the cart
     * </p>
     *
     * @param user Refers {@link User} to show the wishlist of that user.
     */
    public void viewWishlist(final User user) {
        final Wishlist wishlist = WISHLIST_CONTROLLER.getUserWishlist(user);

        if (null == wishlist || null == wishlist.getWishlistItems()) {
            System.out.println("Wishlist is empty");
            return;
        }
        final List<Product> wishlistItems = wishlist.getWishlistItems();

        System.out.println("Wishlist :");
        showItems(wishlistItems);
        final Integer productId = getChoice("Enter the product id to add to cart or to remove from wishlist: [Press '$' to go back] ");

        if (null == productId) {
            return;
        }

        if (!(productId > wishlistItems.size() || productId <= 0)) {
            final Product product = wishlistItems.get(productId - 1);

            loop:
            while (true) {
                System.out.println("Enter '1' to place order or '2' to remove from cart.");

                final Integer choice = getChoice("Enter a choice :");

                if (null == choice) {
                    return;
                }

                switch (choice) {
                    case 1:
                        if (!(0 == product.getQuantity())) {
                            CART_VIEW.addToCart(product, user);
                        } else {
                            System.out.println("Item is out of stock");
                        }
                        break loop;
                    case 2:
                        WISHLIST_CONTROLLER.removeItemFromWishlist(product, user);
                        System.out.println("Item removed");
                        break loop;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        } else {
            System.out.println("Invalid product id");
            viewWishlist(user);
        }
    }

    /**
     * <p>
     * Adds the specific product to the wishlist
     * </p>
     *
     * @param product Refers the product to be added
     * @param user Refers {@link User} who owns the wishlist.
     */
    public boolean addToWishlist(final Product product, final User user) {
        return WISHLIST_CONTROLLER.addToWishlist(product, user);
    }
}



