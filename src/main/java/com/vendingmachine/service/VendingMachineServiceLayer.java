package com.vendingmachine.service;

import java.util.List;
import java.util.Map;

import com.vendingmachine.dao.VendingMachinePersistenceException;
import com.vendingmachine.dto.Item;
import com.vendingmachine.utility.Coins;

public interface VendingMachineServiceLayer {

	List<Item> getAllItems() throws VendingMachinePersistenceException;

	Item getItem(String itemId) throws VendingMachinePersistenceException;

	Map<Coins, Integer> purchaseItem(String itemId, String inputAmount) throws VendingMachinePersistenceException, NoItemIdFoundInInventoryException, OutOfStockException, InsufficientFundsException;

}
