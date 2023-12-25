package com.flipkart.dao.Impl;

import com.flipkart.custom_exceptions.DatabaseException;
import com.flipkart.dao.DBConnection;
import com.flipkart.ProductCategory;
import com.flipkart.model.User;
import com.flipkart.model.Wishlist;
import com.flipkart.model.product.Clothes;
import com.flipkart.model.product.Laptop;
import com.flipkart.model.product.Mobile;
import com.flipkart.model.product.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * Provides DAO for wishlist.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class WishlistDAOImpl {

    private static WishlistDAOImpl wishlistDAOInstance;

    /**
     * <p>
     * Default constructor of the WishlistDAOImpl class. kept private to restrict from creating object from outside of this class.
     * </p>
     */
    private WishlistDAOImpl() {}

    /**
     * <p>
     * Creates a single object of WishlistDAOImpl Class and returns it.
     * </p>
     *
     * @return returns the single instance of WishlistDAOImpl Class.
     */
    public static synchronized WishlistDAOImpl getInstance() {
        if (null == wishlistDAOInstance) {
            wishlistDAOInstance = new WishlistDAOImpl();
        }

        return wishlistDAOInstance;
    }

    /**
     * <p>
     * Adds the product to the wishlist of the specified user.
     * </p>
     *
     * @param product Refers the product to be added
     * @param user Refers the user
     * @return the wishlist of the user.
     */
    public boolean addToWishlist(final Product product, final User user) {
        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("insert into wishlist (user_id , product_id) values(?,?)")) {
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
     * Removes the product from the wishlist of the specified user.
     * * </p>
     *
     * @param product Refers the product to be removed.
     * @param user Refers the user
     */
    public void removeFromWishlist(final Product product, final User user) {
        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("delete from wishlist where user_id =? and product_id =?")) {
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
     * Gets the wishlist of the specified user id and returns it.
     * </p>
     *
     * @param user Refers the user who owns the cart.
     * @return the wishlist of the user.
     */
    public Wishlist getUserWishlist(final User user) {
        final Wishlist wishlist = new Wishlist(user);

        try (final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("select w.product_id, p.product_category, e.brand,e.model, p.price,c.clothes_type,c.size,c.gender, c.brand ,p.quantity from wishlist w join product p on w.product_id=p.id left join electronics_inventory e on w.product_id = e.product_id left join clothes_inventory c on p.id=c.product_id where w.user_id = ?")) {
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
                    wishlist.addItemToWishlist(mobile);
                }

                if (ProductCategory.LAPTOP == ProductCategory.valueOf(productType.toUpperCase())) {
                    final String brand = resultSet.getString(3);
                    final String model = resultSet.getString(4);
                    final float price = resultSet.getFloat(5);
                    final int quantity = resultSet.getInt(10);
                    final Laptop laptop = new Laptop(brand, model, price, quantity);

                    laptop.setProductId(productId);
                    wishlist.addItemToWishlist(laptop);
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
                    wishlist.addItemToWishlist(clothes);
                }
            }

            return wishlist;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
