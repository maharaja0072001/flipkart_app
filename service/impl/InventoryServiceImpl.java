package com.flipkart.service.impl;

import com.flipkart.model.product.Product;
import com.flipkart.service.InventoryService;
import com.flipkart.ProductCategory;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * Provides the service for the Inventory. Responsible for storing all the products.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class InventoryServiceImpl implements InventoryService {

    private static final List<Product> MOBILE_INVENTORY = new ArrayList<>();
    private static final List<Product> LAPTOP_INVENTORY = new ArrayList<>();
    private static final List<Product> CLOTHES_INVENTORY = new ArrayList<>();
    private static InventoryServiceImpl inventoryInstance;

    /**
     * <p>
     * Default constructor of InventoryServiceImpl class. Kept private to restrict from creating object outside this class.
     * </p>
     */
    private InventoryServiceImpl() {}

    /**
     * <p>
     * Creates a single object of InventoryServiceImpl class and returns it.
     * </p>
     *
     * @return the single instance of InventoryController class.
     */
    public static InventoryServiceImpl getInstance() {
        if (null == inventoryInstance) {
            inventoryInstance = new InventoryServiceImpl();
        }

        return inventoryInstance;
    }

    /**
     * <p>
     * Adds the given products to the inventory.
     * </p>
     *
     * @param products Refers the {@link Product} to be added.
     */
    @Override
    public void addItemToInventory(final List<Product> products) {
        for (final Product product : products) {
            if (ProductCategory.MOBILE == product.getProductCategory()) {
                MOBILE_INVENTORY.add(product);
            }

            if (ProductCategory.LAPTOP == product.getProductCategory()) {
                LAPTOP_INVENTORY.add(product);
            }

            if (ProductCategory.CLOTHES == product.getProductCategory()) {
                CLOTHES_INVENTORY.add(product);
            }
        }
    }

    /**
     * <p>
     * Removes the given item from the inventory.
     * </p>
     *
     * @param product Refers the {@link Product} to be removed.
     */
    @Override
    public void removeItemFromInventory(final Product product) {
        if (ProductCategory.MOBILE == product.getProductCategory()) {
           MOBILE_INVENTORY.remove(product);
        }

        if (ProductCategory.LAPTOP == product.getProductCategory()) {
            LAPTOP_INVENTORY.remove(product);
        }

        if (ProductCategory.LAPTOP == product.getProductCategory()) {
            CLOTHES_INVENTORY.remove(product);
        }
    }

    /**
     * <p>
     * Gets all the products from the mobile inventory and returns it.
     * </p>
     *
     * @return all the {@link Product} in the mobile inventory.
     */
    @Override
    public List<Product> getMobileItems() {
        return MOBILE_INVENTORY;
    }

    /**
     * <p>
     * Gets all the products from the laptop inventory and returns it
     * </p>
     *
     * @return all the {@link Product} in the laptop inventory.
     */
    @Override
    public List<Product> getLaptopItems() {
        return  LAPTOP_INVENTORY;
    }

    /**
     * <p>
     * Gets all the products from the clothes inventory and returns it.
     * </p>
     *
     * @return all the {@link Product} in the clothes inventory.
     */
    @Override
    public List<Product> getClothesItems() {
        return CLOTHES_INVENTORY;
    }
}
