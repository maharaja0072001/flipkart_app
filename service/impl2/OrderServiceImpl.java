package com.flipkart.service.impl2;

import com.flipkart.dao.Impl.OrderDAOImpl;
import com.flipkart.model.Order;
import com.flipkart.service.OrderService;

import java.util.List;

/**
 * <p>
 * Provides the service for the Order.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class OrderServiceImpl implements OrderService {

    private static final OrderDAOImpl ORDER_DAO = OrderDAOImpl.getInstance();
    private static OrderServiceImpl orderServiceInstance;

    /**
     * <p>
     * Default constructor of the OrderServiceImpl class. kept private to restrict from creating object from outside of this class.
     * </p>
     */
    private OrderServiceImpl() {}

    /**
     * <p>
     * Creates a single object of OrderServiceImpl Class and returns it.
     * </p>
     *
     * @return returns the single instance of OrderServiceImpl Class.
     */
    public static synchronized OrderServiceImpl getInstance() {
        if (null == orderServiceInstance) {
            orderServiceInstance = new OrderServiceImpl();
        }

        return orderServiceInstance;
    }

    /**
     * <p>
     * Adds the order placed by the user.
     * </p>
     *
     * @param userId Refers the id of the user
     * @param order Refers the {@link Order} to be added.
     */
    public void addOrder(final int userId, final Order order) {
        ORDER_DAO.addOrder(userId, order);
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
        return ORDER_DAO.getOrders(userId);
    }

    /**
     * <p>
     * Cancels the order placed by the user.
     * </p>
     *
     * @param order Refers the {@link Order} to be cancelled.
     */
    public void cancelOrder(final Order order) {
        ORDER_DAO.cancelOrder(order);
    }
}
