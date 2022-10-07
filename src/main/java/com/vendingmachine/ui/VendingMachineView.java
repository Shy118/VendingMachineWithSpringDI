package com.vendingmachine.ui;

import java.util.List;
import java.util.Map;

import com.vendingmachine.dto.Item;
import com.vendingmachine.utility.Coins;

public class VendingMachineView {
	private UserIO io;
	
    public VendingMachineView(UserIO io) {
		this.io = io;
	}

	public int printMenuAndGetSelection() {
        io.println("1. Insert Coins");
	    io.println("2. Exit");
	    
	    return io.readInt("Please select from the above choices.", 1, 2);
	}
    
    public void displayVendingMachineBanner() {
        io.println("=============== Vending Machine ===============");
    }
    
    public void displayItemList(List<Item> itemList) {
        for (Item currentItem : itemList) {
            String itemInfo = String.format("#%2s : %-16s $%4s Qty - %2d",
				currentItem.getItemId(),
				currentItem.getItemName(),
				currentItem.getCost(),
				currentItem.getQuantity());
            io.println(itemInfo);
        }
    }
    
    public String insertCoins() {
    	return io.readString("Please insert Coins (in dollars)");
    }
    
    public void displayItemBanner () {
        io.println("=============== Display Item ===============");
    }

    public String getItemIdChoice() {
        return io.readString("Please enter the Item ID you like to purchase.");
    }
    
    public void displayPurchaseSuccessBanner() {
    	io.println("=============== Item Purchased Successfully ===============");
    }
    
    public void displayPurchasedItem(String name) {
    	io.println("Vending " + name + ". . .");
    }
    
    public void displayChange(Map<Coins, Integer> change) {
    	io.println("Please take your change: ");
    	change.keySet().forEach(c -> io.print(c + ": " + change.get(c) + " "));
    }
    
    public void displayExitBanner() {
        io.println("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.println("Unknown Command!!!");
    }
    
    public void displayErrorMessage(String errorMsg) {
        io.println("=============== ERROR ===============");
        io.println(errorMsg);
    }
}
