package com.flipkart.controller;

import com.flipkart.model.Order;
import com.flipkart.service.impl2.OrderServiceImpl;

import java.util.List;

/**
 * <p>
 * Interacts between OrderView and OrderService for adding , viewing and cancelling orders.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class OrderController {

    private static OrderController orderControllerInstance;
    private static final OrderServiceImpl ORDER_SERVICE = OrderServiceImpl.getInstance();

    /**
     * <p>
     * Default constructor of OrderController class. Kept private to restrict from creating object outside this class.
     * </p>
     */
    private OrderController() {}

    /**
     * <p>
     * Creates a single object of OrderController class and returns it.
     * </p>
     *
     * @return the single instance of OrderController class.
     */
    public static synchronized OrderController getInstance() {
        if (null == orderControllerInstance) {
            orderControllerInstance = new OrderController();
        }

        return orderControllerInstance;
    }

    /**
     * <p>
     * Gets all the orders placed by the user.
     * </p>
     *
     * @param userId Refers the id of the user
     * @return  all the {@link Order} of the user.
     */
    public List<Order> getOrders(final int userId) {
        return ORDER_SERVICE.getOrders(userId);
    }

    /**
     * <p>
     * Adds the order of the user.
     * </p>
     *
     * @param userId Refers the id of the user
     * @param order Refers the {@link Order} to be added.
     */
    public void addOrder(final int userId, final Order order) {
        ORDER_SERVICE.addOrder(userId, order);
    }

    /**
     * <p>
     * Cancels the order placed by the user.
     * </p>
     *
     * @param order Refers the {@link Order} to be cancelled.
     */
    public void cancelOrder(final Order order) {
        ORDER_SERVICE.cancelOrder(order);
    }
}




