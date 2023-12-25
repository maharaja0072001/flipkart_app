package com.flipkart.controller;

import com.flipkart.model.product.Product;
import com.flipkart.service.impl2.InventoryServiceImpl;

import java.util.List;

/**
 * <p>
 * Interacts between InventoryView and InventoryService for adding, removing and retrieving products from inventory.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class InventoryController {

    private static final InventoryServiceImpl INVENTORY = InventoryServiceImpl.getInstance();
    private static InventoryController inventoryControllerInstance;

    /**
     * <p>
     * Default constructor of InventoryController class. Kept private to restrict from creating object outside this class.
     * </p>
     */
    private InventoryController() {}

    /**
     * <p>
     * Creates a single object of InventoryController class and returns it.
     * </p>
     *
     * @return the single instance of InventoryController class.
     */
    public static synchronized InventoryController getInstance() {
        if (null == inventoryControllerInstance) {
            inventoryControllerInstance = new InventoryController();
        }

        return inventoryControllerInstance;
    }

    /**
     * <p>
     * Adds the given products to the inventory.
     * </p>
     *
     * @param products the products to be added.
     */
    public void addItemToInventory(final List<Product> products) {
        INVENTORY.addItemToInventory(products);
    }

    /**
     * <p>
     * Removes the given item from the inventory.
     * </p>
     *
     * @param item the item to be removed.
     */
    public void removeItemFromInventory(final Product item) {
        INVENTORY.removeItemFromInventory(item);
    }

    /**
     * <p>
     * Gets all the items from the mobile inventory and returns it.
     * </p>
     *
     * @return the items in the mobile inventory.
     */
    public List<Product> getMobileItems() {
        return INVENTORY.getMobileItems();
    }

    /**
     * <p>
     * Gets all the items from the laptop inventory and returns it
     * </p>
     *
     * @return the items in the laptop inventory.
     */
    public List<Product> getLaptopItems() {
        return INVENTORY.getLaptopItems();
    }

    /**
     * <p>
     * Gets all the items from the clothes inventory and returns it.
     * </p>
     *
     * @return the items in the clothes inventory.
     */
    public List<Product> getClothesItems() {
        return INVENTORY.getClothesItems();
    }
}
