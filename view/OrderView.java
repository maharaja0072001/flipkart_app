package com.flipkart.view;

import com.flipkart.InputHandler;
import com.flipkart.OrderStatus;
import com.flipkart.PaymentMode;
import com.flipkart.controller.OrderController;
import com.flipkart.controller.UserController;
import com.flipkart.model.Order;
import com.flipkart.model.User;
import com.flipkart.model.product.Product;
import com.flipkart.view.datavalidation.UserDataValidator;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * <p>
 * Responsible for viewing the orders placed by the user.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class OrderView extends View {

    private static final UserDataValidator USER_DATA_VALIDATOR = UserDataValidator.getInstance();
    private static final OrderController ORDER_CONTROLLER = OrderController.getInstance();
    private static final UserController USER_CONTROLLER = UserController.getInstance();
    private final Scanner scanner = InputHandler.getScanner();
    private static OrderView orderViewInstance;

    /**
     * <p>
     * Default constructor of OrderView class. Kept private to restrict from creating object outside this class.
     * </p>
     */
    private OrderView() {}

    /**
     * <p>
     * Creates a single object of OrderView class and returns it.
     * </p>
     *
     * @return the single instance of OrderView class.
     */
    public static OrderView getInstance() {
        if (null == orderViewInstance) {
            orderViewInstance = new OrderView();
        }

        return orderViewInstance;
    }

    /**
     * <p>
     * Shows the order details of the user and user can cancels the order.
     * </p>
     *
     * @param user Refers {@link User} whose orders will be shown.
     */
    public void viewOrCancelOrder(final User user) {
        final List<Order> orders = ORDER_CONTROLLER.getOrders(user.getUserId());

        if (null == orders || orders.isEmpty()) {
            System.out.println("No orders found");

            return;
        } else {
            for (int i = 0; i < orders.size(); i++) {
                System.out.println(String.format("%d :[%s]\n", i+1, orders.get(i)));
            }
        }
        final int index = getIndex(orders);

        if (0 == index) {
            return;
        }
        final Order order = orders.get(index - 1) ;

        ORDER_CONTROLLER.cancelOrder(order);
        System.out.println("Your order has been cancelled");
    }

    /**
     * <p>
     * Gets the index from the user.
     * </p>
     *
     * @param orders Refers the list of {@link Order}
     * @return the id of the order.
     */
    private int getIndex(final List<Order> orders) {
        System.out.println("Enter the index of order to cancel: [Press '$' to go back");
         try {
             final String index = scanner.nextLine();

             if (USER_DATA_VALIDATOR.containsToNavigateBack(index)) {
                 return 0;
             }

             if (orders.size() < Integer.parseInt(index) || Integer.parseInt(index) <= 0) {
                 System.out.println("Enter a valid index");

                 return getIndex(orders);
             }

             return Integer.parseInt(index);
         } catch (NumberFormatException exception) {
             System.out.println("Enter a valid index");
         }

        return getIndex(orders);
    }

    /**
     * <p>
     * Places the order for the user.
     * </p>
     *
     * @param product Refers the product to be placed.
     * @param user Refers the current {@link User}
     */
    public void placeOrder(final Product product, final User user){
        final Integer productQuantity = getQuantity(product, user);

        if (null == productQuantity) {
            return;
        }
        final String address = getAddress(USER_CONTROLLER.getAddresses(user), user);

        if (USER_DATA_VALIDATOR.isNull(address)) {
            return;
        }
        final PaymentMode paymentMode = getPaymentMode();

        if (null == paymentMode) {
            return;
        }
        ORDER_CONTROLLER.addOrder(user.getUserId(), new Order.OrderBuilder(user.getUserId(), product.getProductId(),
                paymentMode).setAddress(address).setQuantity(productQuantity).setTotalAmount(productQuantity * product.getPrice()).setProductName(product.toString()).setOrderStatus(OrderStatus.PLACED).buildOrder());
        product.setQuantity(product.getQuantity() - productQuantity);
        System.out.println("Order placed successfully");
    }

    /**
     * <p>
     * Gets the quantity of product to order from the user.
     * </p>
     *
     * @param product Refers the product to be ordered.
     * @param user Refers the current {@link User}
     * @return the quantity of product.
     */
    private Integer getQuantity(final Product product, final User user) {
        System.out.println("Enter the quantity : [Press '$' to go back]");
        final String quantity = scanner.nextLine().trim();

        if (USER_DATA_VALIDATOR.containsToNavigateBack(quantity)) {
            return null;
        }

        try {
            if (Integer.parseInt(quantity) > product.getQuantity()) {
                System.out.println(String.format("Enter a valid quantity. Available quantity :%d", product.getQuantity()));
                placeOrder(product, user);

                return null;
            }

            return Integer.parseInt(quantity);
        } catch (NumberFormatException exception) {
            System.out.println("Enter a valid quantity");
            placeOrder(product, user);

            return null;
        }
    }
    /**
     * <p>
     * Gets the payment mode from user and returns it.
     * </p>
     *
     * @return the payment of the user.
     */
    private PaymentMode getPaymentMode() {
        final Integer choice = getChoice("Choose your payment mode : \n1.Cash On Delivery\n2.Credit or Debit card\n3.Net Banking\n4.UPI");

        if (USER_DATA_VALIDATOR.isNull(choice)) {
            return null;
        }

        switch (choice) {
            case 1:
                return PaymentMode.CASH_ON_DELIVERY;
            case 2:
                return PaymentMode.CREDIT_OR_DEBIT_CARD;
            case 3:
                return PaymentMode.NET_BANKING;
            case 4:
                return PaymentMode.UPI;
            default:
                System.out.println("Enter a valid choice");
        }

        return getPaymentMode();
    }

    /**
     * <p>
     * Gets the address of the user.
     * </p>
     *
     * @param addresses Refers the addresses of the user.
     * @param user Refers the current {@link User}
     * @return the address of the user.
     */
    private String getAddress(final List<String> addresses, final User user) {
        if (Objects.isNull(addresses)) {
            System.out.println("Enter a new address : [Press '$' to go back]");
            final String newAddress = scanner.nextLine().trim();

            if (USER_DATA_VALIDATOR.containsToNavigateBack(newAddress)) {
                return null;
            }
            USER_CONTROLLER.addAddress(user, newAddress);

            return newAddress;
        }
        System.out.println(addresses);
        final Integer choice = getChoice("Press '1' to select the address or '2' to add new address");

        if (USER_DATA_VALIDATOR.isNull(choice)) {
            return null;
        }

        while (true) {
            switch (choice) {
                case 1:
                    System.out.println("Enter the index:");
                    final String index = scanner.nextLine().trim();

                    if (USER_DATA_VALIDATOR.containsToNavigateBack(index)) {
                        return index;
                    }

                    return USER_CONTROLLER.getAddresses(user).get(Integer.parseInt(index));
                case 2:
                    System.out.println("Enter a new address:");
                    final String newAddress = scanner.nextLine().trim();

                    if (USER_DATA_VALIDATOR.containsToNavigateBack(newAddress)) {
                        return null;
                    }
                    USER_CONTROLLER.addAddress(user, newAddress);

                    return newAddress;
                default :
                    System.out.println("Enter a valid choice");
            }
        }
    }
}
