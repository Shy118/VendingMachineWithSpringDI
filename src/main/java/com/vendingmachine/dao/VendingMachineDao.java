package com.vendingmachine.dao;

import java.util.List;

import com.vendingmachine.dto.Item;

public interface VendingMachineDao {
	
    /**
     * Returns a List of all items in the inventory.
     *
     * @return List containing all items in the inventory.
     * @throws VendingMachinePersistenceException 
     */
    List<Item> getAllItems() throws VendingMachinePersistenceException;

    /**
     * Returns the Item object associated with the given item id.
     * Returns null if no such item exists
     *
     * @param itemId ID of the item to retrieve
     * @return the Item object associated with the given item id,  
     * null if no such item exists
     * @throws VendingMachinePersistenceException 
     */
    Item getItem(String itemId) throws VendingMachinePersistenceException;

    /**
     * Returns the item object associated with the given item id setting quantity - 1.
     *
     * @param itemId ID of the item to purchase
     * @return the Item object associated with the given item id after purchased,  
     * 
     * @throws VendingMachinePersistenceException 
     */
    Item purchaseItem(String itemId) throws VendingMachinePersistenceException;
}
