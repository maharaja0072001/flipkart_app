package com.flipkart.view;

import com.flipkart.InputHandler;
import com.flipkart.model.product.Product;
import com.flipkart.view.datavalidation.UserDataValidator;

import java.util.List;
import java.util.Scanner;

/**
 * <p>
 * Common view for all the view class. Contains common methods of all view class
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public abstract class View {

    private final Scanner scanner = InputHandler.getScanner();
    private static final UserDataValidator USER_DATA_VALIDATOR = UserDataValidator.getInstance();

    /**
     * <p>
     * Gets the choice from the user .
     * </p>
     * @param message Refers the message to print.
     * @return the choice
     */
    public Integer getChoice(final String message) {
        System.out.println(message);

        try {
            final String choice = scanner.nextLine().trim();

            if (USER_DATA_VALIDATOR.containsToNavigateBack(choice)) {
                return null;
            }

            return Integer.parseInt(choice);
        } catch (NumberFormatException exception) {
            System.out.println("Input is invalid. Enter a valid number");
        }

        return getChoice(message);
    }

    /**
     * <p>
     * Shows the items to the user .
     * </p>
     * @param products Refers the products to be shown.
     */
    public void showItems(final List<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            String quantityStatus = "";

            if (0 == products.get(i).getQuantity()) {
                quantityStatus = "(Out of Stock)";
            }
            System.out.println(String.format("[%d : %s%s]", i+1, products.get(i), quantityStatus));
        }
    }
}
