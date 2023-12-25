package com.flipkart.dao.Impl;

import com.flipkart.custom_exceptions.DatabaseException;
import com.flipkart.dao.DBConnection;
import com.flipkart.model.Cart;
import com.flipkart.model.User;
import com.flipkart.model.product.Clothes;
import com.flipkart.model.product.Laptop;
import com.flipkart.model.product.Mobile;
import com.flipkart.model.product.Product;
import com.flipkart.ProductCategory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * Responsible for storing all the cart details in the database.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class CartDAOImpl {

    private static CartDAOImpl cartDAOImplInstance;

    /**
     * <p>
     * Default constructor of the CartDAOImpl class. kept private to restrict from creating object from outside of this class.
     * </p>
     */
    private CartDAOImpl() {}

    /**
     * <p>
     * Creates a single object of CartDAOImpl Class and returns it.
     * </p>
     *
     * @return returns the single instance of CartDAOImpl Class.
     */
    public static synchronized CartDAOImpl getInstance() {
        if (null == cartDAOImplInstance) {
            cartDAOImplInstance = new CartDAOImpl();
        }

        return cartDAOImplInstance;
    }

    /**
     * <p>
     * Adds the product to the cart in the database.
     * </p>
     *
     * @param product Refers the {@link Product} to be added.
     * @param user Refers the current {@link User}.
     * @return true is the product is added to the cart.
     */
    public boolean addItemToCart(final Product product, final User user) {
        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("insert into cart (user_id , product_id) values(?,?)")) {
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setInt(2, product.getProductId());
            final int updatedRows = preparedStatement.executeUpdate();

            DBConnection.getConnection().commit();

            return  updatedRows > 0;
        } catch (SQLException exception) {
            return false;
        }
    }

    /**
     * <p>
     * Removes the product from the cart in the database.
     * </p>
     *
     * @param product Refers the {@link Product} to be removed from the cart.
     * @param user Refers the current {@link User}.
     */
    public void removeItemFromCart(final Product product, final User user) {
        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("delete from cart where user_id =? and product_id =?")) {
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setInt(2, product.getProductId());
            preparedStatement.executeUpdate();
            DBConnection.getConnection().commit();

        } catch (SQLException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    /**
     * <p>
     * Gets the cart to the user from the database.
     * </p>
     *
     * @param user Refers the current {@link User}.
     * @return {@link Cart} of the user.
     */
    public Cart getUserCart(final User user) {
        final Cart cart = new Cart(user);

        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("select cart.product_id, p.product_category, e.brand,e.model, p.price,c.clothes_type,c.size,c.gender, c.brand, p.quantity from cart join product p on cart.product_id=p.id  left join electronics_inventory e on cart.product_id = e.product_id left join clothes_inventory c on p.id=c.product_id where cart.user_id = ?")) {
            preparedStatement.setInt(1, user.getUserId());
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final int productId = resultSet.getInt(1);
                final String productType = resultSet.getString(2);

                if (ProductCategory.MOBILE.name().equals(productType.toUpperCase())) {
                    final String brand = resultSet.getString(3);
                    final String model = resultSet.getString(4);
                    final float price = resultSet.getFloat(5);
                    final int quantity = resultSet.getInt(10);
                    final Mobile mobile = new Mobile(brand, model, price, quantity);

                    mobile.setProductId(productId);
                    cart.addToCart(mobile);
                }

                if (ProductCategory.LAPTOP == ProductCategory.valueOf(productType.toUpperCase())) {
                    final String brand = resultSet.getString(3);
                    final String model = resultSet.getString(4);
                    final float price = resultSet.getFloat(5);
                    final int quantity = resultSet.getInt(10);
                    final Laptop laptop = new Laptop(brand, model, price, quantity);

                        laptop.setProductId(productId);
                        cart.addToCart(laptop);
                    }

                if (ProductCategory.CLOTHES == ProductCategory.valueOf(productType.toUpperCase())) {
                    final String brand = resultSet.getString(9);
                    final String clothesType = resultSet.getString(6);
                    final String size = resultSet.getString(7);
                    final String gender = resultSet.getString(8);
                    final float price = resultSet.getFloat(5);
                    final int quantity = resultSet.getInt(10);
                    final Clothes clothes = new Clothes(clothesType, gender, size, price, brand, quantity);

                    clothes.setProductId(productId);
                    cart.addToCart(clothes);
                }
            }

            return cart;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
