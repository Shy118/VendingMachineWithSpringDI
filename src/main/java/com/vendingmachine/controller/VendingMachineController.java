package com.vendingmachine.controller;

import java.util.List;
import java.util.Map;

import com.vendingmachine.dao.VendingMachinePersistenceException;
import com.vendingmachine.dto.Item;
import com.vendingmachine.service.InsufficientFundsException;
import com.vendingmachine.service.NoItemIdFoundInInventoryException;
import com.vendingmachine.service.OutOfStockException;
import com.vendingmachine.service.VendingMachineServiceLayer;
import com.vendingmachine.ui.VendingMachineView;
import com.vendingmachine.utility.Coins;

public class VendingMachineController {
	private VendingMachineServiceLayer service;
	private VendingMachineView view;

	public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view) {
	    this.service = service;
	    this.view = view;
	} //parameterized constructor

	public void run() {
        int menuSelection = 0;
        
        try {
			displayItems();
		    menuSelection = getMenuSelection();

		    switch (menuSelection) {
		        case 1:
		        	makePurchase();
		            break;
		        case 2:
		        	exitMessage();
		            break;
		        default:
		        	unknownCommand();
			} //switch-case
			
		} //try
        catch (VendingMachinePersistenceException | NoItemIdFoundInInventoryException| OutOfStockException | InsufficientFundsException e) 
        {
			view.displayErrorMessage(e.getMessage());
		} //catch
    } //run
	
    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }
    
    private void displayItems() throws VendingMachinePersistenceException {
    	List<Item> itemList = service.getAllItems();
    	view.displayItemList(itemList);
    }
    
    private void makePurchase() throws VendingMachinePersistenceException, NoItemIdFoundInInventoryException,  OutOfStockException, InsufficientFundsException {
    	String inputAmount = view.insertCoins();
    	String itemId = view.getItemIdChoice();
    	Map<Coins, Integer> change = service.purchaseItem(itemId , inputAmount);
    	view.displayPurchaseSuccessBanner();
    	viewPurchasedItem(itemId);
    	view.displayChange(change);
    }
    
    private void viewPurchasedItem(String itemId) throws VendingMachinePersistenceException {
    	Item purchasedItem = service.getItem(itemId);
    	view.displayPurchasedItem(purchasedItem.getItemName());
    }
    
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
    
} //class
