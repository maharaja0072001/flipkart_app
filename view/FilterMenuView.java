package com.flipkart.view;

import com.flipkart.model.User;
import com.flipkart.model.product.Product;
import com.flipkart.view.datavalidation.UserDataValidator;
import com.flipkart.view.filter.PriceFilter;
import com.flipkart.view.filter.RateHighToLowFilter;
import com.flipkart.view.filter.RateLowToHighFilter;

import java.util.List;

/**
 * <p>
 * Responsible for showing the filter menu which is useful to get filtered items.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class FilterMenuView extends View {

    private static final CartView CART_VIEW = CartView.getInstance();
    private static final WishlistView WISHLIST_VIEW = WishlistView.getInstance();
    private static final UserDataValidator USER_DATA_VALIDATOR = UserDataValidator.getInstance();
    private static FilterMenuView filterMenuViewInstance;

    /**
     * <p>
     * Default constructor of FilterMenuView class. Kept private to restrict from creating object outside this class.
     * </p>
     */
    private FilterMenuView() {}

    /**
     * <p>
     * Creates a single object of FilterMenuView class and returns it.
     * </p>
     *
     * @return the single instance of FilterMenuView class.
     */
    public static synchronized FilterMenuView getInstance() {
        if (null == filterMenuViewInstance) {
            filterMenuViewInstance = new FilterMenuView();
        }

        return filterMenuViewInstance;
    }

    /**
     * <p>
     * Shows the filter menu to the user to filter the items presented in the inventory.
     * </p>
     *
     * @param user Refers the current {@link User}.
     * @param inventoryItems Refers all the {@link Product} in the inventory.
     */
    public void showFilterMenu(final User user, final List<Product> inventoryItems) {
        Product product;
        final Integer choice = getChoice("Filter By:[Press '$' to go back]\n1.Rate Low to High\n2.Rate High to Low\n3.Price");

        if (USER_DATA_VALIDATOR.isNull(choice)) {
            return;
        }

        switch (choice) {
            case 1:
                final List<Product> itemsFilteredByLowToHigh = RateLowToHighFilter.getInstance().getFilteredItems(inventoryItems);

                showItems(itemsFilteredByLowToHigh);
                product = getProduct(itemsFilteredByLowToHigh, user);

                if (null == product) {
                    showFilterMenu(user, inventoryItems);

                    return;
                }
                break;
            case 2:
                final List<Product> itemsFilteredByHighToLow = RateHighToLowFilter.getInstance().getFilteredItems(inventoryItems);

                showItems(itemsFilteredByHighToLow);
                product = getProduct(itemsFilteredByHighToLow, user);

                if (null == product) {
                    showFilterMenu(user, inventoryItems);

                    return;
                }
                break;
            case 3:
                final List<Product> itemsFilteredByPrice = PriceFilter.getInstance().getFilteredItems(inventoryItems);

                if (USER_DATA_VALIDATOR.isNull(itemsFilteredByPrice)) {
                    showFilterMenu(user, inventoryItems);

                    return;
                }
                showItems(itemsFilteredByPrice);
                product = getProduct(itemsFilteredByPrice, user);

                if (null == product) {
                    showFilterMenu(user, inventoryItems);

                    return;
                }
                break;
            default:
                System.out.println("Enter a valid choice ");
                break;
        }
        showFilterMenu(user, inventoryItems);
    }

    /**
     * <p>
     * Gets the specific item from the inventory which was selected by the user and return it.
     * </p>
     *
     * @return the {@link Product} selected by the user.
     * @param products Refers all the {@link Product} in the inventory.
     */

    private Product getProduct(final List<Product> products, final User user) {
        if (null == products || products.isEmpty()) {
            return null;
        }
        final Integer index = getChoice("Enter the product id : [Press '$' to go back]");

        if (USER_DATA_VALIDATOR.isNull(index)) {
            return null;
        }

        if (index > products.size() || index <= 0) {
            System.out.println("Enter a valid product id");
            getProduct(products, user);

            return null;
        }
        final Product product = products.get(index - 1);

        addItemToCartOrWishlist(product, user);

        return getProduct(products, user);
    }

    /**
     * <p>
     * Gets the choice from the user to add the product to the cart or wishlist.
     * </p>
     *
     * @param product Refers the {@link Product} to be added to cart or wishlist
     * @param user Refers the current {@link User}.
     */
    public void addItemToCartOrWishlist(final Product product, final User user) {
        final Integer choice = getChoice("Enter '1' to add to cart or '2' to add to wishlist. Press '$' to go back");

        if (USER_DATA_VALIDATOR.isNull(choice)) {
            return;
        }

        switch (choice) {
            case 1:
                CART_VIEW.addToCart(product, user);
                break;
            case 2:
                if (WISHLIST_VIEW.addToWishlist(product, user)) {
                    System.out.println("Item added to wishlist");
                } else {
                    System.out.println("Item already is in the wishlist");
                }
                break;
            default:
                System.out.println("Enter a valid choice");
                addItemToCartOrWishlist(product, user);
                break;
        }
    }
}