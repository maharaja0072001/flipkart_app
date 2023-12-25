package com.flipkart.service;

import com.flipkart.model.Order;

import java.util.List;

/**
 * <p>
 * Provides the service for the Order.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public interface OrderService {

   /**
    * <p>
    * Adds the order placed by the user.
    * </p>
    *
    * @param userId Refers the id of the user
    * @param order Refers the {@link Order} to be added.
    */
   void addOrder(final int userId, final Order order);

   /**
    * <p>
    * Gets all the orders placed by the user.
    * </p>
    *
    * @param userId Refers the id of the user
    * @return  all the {@link Order} of the user.
    */
   List<Order> getOrders(final int userId);

   /**
    * <p>
    * Cancels the order placed by the user.
    * </p>
    *
    * @param order Refers the {@link Order} to be cancelled.
    */
   void cancelOrder(final Order order);
}
