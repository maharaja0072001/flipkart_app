package com.flipkart.dao.Impl;

import com.flipkart.OrderStatus;
import com.flipkart.PaymentMode;
import com.flipkart.ProductCategory;
import com.flipkart.custom_exceptions.DatabaseException;
import com.flipkart.dao.DBConnection;
import com.flipkart.model.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Responsible for storing all the order details in the database.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class OrderDAOImpl {

    private static OrderDAOImpl orderDAOInstance;

    /**
     * <p>
     * Default constructor of the OrderDAOImpl class. kept private to restrict from creating object from outside of this class.
     * </p>
     */
    private OrderDAOImpl() {}

    /**
     * <p>
     * Creates a single object of OrderDAOImpl Class and returns it.
     * </p>
     *
     * @return returns the single instance of OrderDAOImpl Class.
     */
    public static OrderDAOImpl getInstance() {
        if (null == orderDAOInstance) {
            orderDAOInstance = new OrderDAOImpl();
        }

        return orderDAOInstance;
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
        final int productId = order.getProductId();
        final int quantity = order.getQuantity();
        final float totalAmount = order.getTotalAmount();
        final String address = order.getAddress();
        final PaymentMode paymentMode = order.getPaymentMode();
        final OrderStatus orderStatus = order.getOrderStatus();

        try(final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("insert into orders(user_id, product_id, address, payment_mode, quantity, total_amount, order_status) values (?,?,?,?,?,?,?) ")) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);
            preparedStatement.setString(3, address);
            preparedStatement.setObject(4, paymentMode, Types.OTHER);
            preparedStatement.setInt(5, quantity);
            preparedStatement.setFloat(6, totalAmount);
            preparedStatement.setObject(7,orderStatus, Types.OTHER);
            preparedStatement.executeUpdate();
            DBConnection.getConnection().commit();
            updateQuantity(productId, quantity);
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Updates the quantity of product when order is placed by the user.
     * </p>
     *
     * @param productId Refers the id of the product.
     * @param quantity Refers the quantity to be updated.
     */
    private void updateQuantity(final int productId, final int quantity) {
        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("update product set quantity = quantity - ? where id =?")) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productId);
            preparedStatement.executeUpdate();
            DBConnection.getConnection().commit();
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Cancels the order placed by the user.
     * </p>
     *
     * @param order Refers the {@link Order} to be cancelled.
     */
    public void cancelOrder(final Order order) {
        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("update orders set order_status =? where id =?")) {

            preparedStatement.setObject(1, OrderStatus.CANCELLED, Types.OTHER);
            preparedStatement.setInt(2, order.getId());
            preparedStatement.executeUpdate();
            DBConnection.getConnection().commit();
            updateQuantity(order.getProductId(), -order.getQuantity());
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
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
        final List<Order> orders = new ArrayList<>();

        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("select o.id,o.product_id, o.payment_mode,o.quantity,o.total_amount, o.address,o.order_status, p.product_category, e.brand,e.model, p.price,c.clothes_type,c.size,c.gender, c.brand from orders o join product p on o.product_id=p.id  left join electronics_inventory e on o.product_id = e.product_id left join clothes_inventory c on o.product_id=c.product_id where o.user_id = ?")) {
            preparedStatement.setInt(1, userId);
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final int orderId = resultSet.getInt(1);
                final int productId = resultSet.getInt(2);
                String productName = null;
                final PaymentMode paymentMode = PaymentMode.valueOf(resultSet.getString(3));
                final int quantity = resultSet.getInt(4);
                final float totalAmount = resultSet.getFloat(5);
                final String address = resultSet.getString(6);
                final OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString(7));
                final String productCategory = resultSet.getString(8);

                if (ProductCategory.MOBILE == ProductCategory.valueOf(productCategory.toUpperCase()) || ProductCategory.LAPTOP == ProductCategory.valueOf(productCategory.toUpperCase())) {
                    final String brand = resultSet.getString(9);
                    final String model = resultSet.getString(10);
                    final float price = resultSet.getFloat(11);
                    productName = String.format("Product name : %s %s - Rs :%.2f", brand,model,price);
                }

                if (ProductCategory.CLOTHES == ProductCategory.valueOf(productCategory.toUpperCase())) {
                    final float price = resultSet.getFloat(11);
                    final String clothesType = resultSet.getString(12);
                    final String size = resultSet.getString(13);
                    final String gender = resultSet.getString(14);
                    final String brand = resultSet.getString(15);

                    productName = String.format("%s brand :%s size : %s gender: %s - Rs :%.2f ",clothesType,brand,size,gender,price);
                }
                final Order order = new Order.OrderBuilder(userId, productId, paymentMode).setId(orderId).setProductName(productName).setTotalAmount(totalAmount).setQuantity(quantity).setAddress(address).setOrderStatus(orderStatus).buildOrder();

                orders.add(order);
            }

            return orders;
        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }
}
