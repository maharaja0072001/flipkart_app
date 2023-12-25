package com.flipkart.service.impl2;

import com.flipkart.dao.Impl.InventoryDAOImpl;
import com.flipkart.model.product.Product;
import com.flipkart.service.InventoryService;

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

    private static final InventoryDAOImpl INVENTORY_DAO = InventoryDAOImpl.getInstance();
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
    public static synchronized InventoryServiceImpl getInstance() {
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
        INVENTORY_DAO.addItemToInventory(products);
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
        INVENTORY_DAO.removeItemFromInventory(product);
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
        return INVENTORY_DAO.getMobileItems();
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
        return INVENTORY_DAO.getLaptopItems();
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
        return INVENTORY_DAO.getClothesItems();
    }
}