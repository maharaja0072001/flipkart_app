package com.flipkart.service;

import com.flipkart.model.product.Product;

import java.util.List;

/**
 * <p>
 * Provides service for the Inventory.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public interface InventoryService {

    /**
     * <p>
     * Adds the given products to the inventory.
     * </p>
     *
     * @param products Refers the {@link Product} to be added.
     */
    void addItemToInventory(final List<Product> products);

    /**
     * <p>
     * Removes the given item from the inventory.
     * </p>
     *
     * @param product Refers the {@link Product} to be removed.
     */
    void removeItemFromInventory(final Product product);

    /**
     * <p>
     * Gets all the products from the mobile inventory and returns it.
     * </p>
     *
     * @return all the {@link Product} in the mobile inventory.
     */
    List<Product> getMobileItems();

    /**
     * <p>
     * Gets all the products from the laptop inventory and returns it
     * </p>
     *
     * @return all the {@link Product} in the laptop inventory.
     */
    List<Product> getLaptopItems();

    /**
     * <p>
     * Gets all the products from the clothes inventory and returns it.
     * </p>
     *
     * @return all the {@link Product} in the clothes inventory.
     */
    List<Product> getClothesItems();
}
