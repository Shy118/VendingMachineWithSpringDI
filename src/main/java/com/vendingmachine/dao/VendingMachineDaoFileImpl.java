package com.vendingmachine.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.vendingmachine.dto.Item;

public class VendingMachineDaoFileImpl implements VendingMachineDao {
	private Map<String, Item> items = new HashMap<>();
	public final String INVENTORY_FILE;
	public static final String DELIMITER = "::";
	
	public VendingMachineDaoFileImpl(){
		INVENTORY_FILE = "inventory.txt";
	}

	public VendingMachineDaoFileImpl(String inventoryTextFile){
		INVENTORY_FILE = inventoryTextFile;
	}
	
	@Override
	public List<Item> getAllItems() throws VendingMachinePersistenceException {
		loadInventory();
	    return new ArrayList<Item>(items.values());
	}

	@Override
	public Item getItem(String itemId) throws VendingMachinePersistenceException {
		loadInventory();
	    return items.get(itemId);
	}

	@Override
	public Item purchaseItem(String itemId) throws VendingMachinePersistenceException {
		loadInventory();
		Item itemToPurchase = getItem(itemId);
		itemToPurchase.setQuantity(itemToPurchase.getQuantity() - 1);
	    Item purchasedItem = items.put(itemId, itemToPurchase);
	    writeInventory();
	    return purchasedItem;
	}

	private Item unmarshallItem(String itemAsText){
	    // itemAsText is expecting a line read in from our file.
	    // For example, it might look like this:
	    // 1::Coca-Cola::1.20::12
	    //
	    // We then split that line on our DELIMITER - which we are using as ::
	    // Leaving us with an array of Strings, stored in itemTokens.
	    // Which should look like this:
	    // _____________________
	    // | |   	   |    |  |
	    // |1|Coca-Cola|1.20|12|
	    // | |   	   |    |  |
	    // ---------------------
	    // [0]   [1]    [2]  [3]
	    String[] itemTokens = itemAsText.split(DELIMITER);

	    // Given the pattern above, the item Id is in index 0 of the array.
	    String itemId = itemTokens[0];

	    // Which we can then use to create a new Item object to satisfy
	    // the requirements of the Item constructor.
	    Item itemFromFile = new Item(itemId);

	    // However, there are 3 remaining tokens that need to be set into the
	    // new Item object. Do this manually by using the appropriate setters.

	    // Index 1 - itemName
	    itemFromFile.setItemName(itemTokens[1]);

	    // Index 2 - Cost
	    itemFromFile.setCost(itemTokens[2]);

	    // Index 3 - Quantity
	    itemFromFile.setQuantity(Integer.parseInt(itemTokens[3]));

	    // We have now created a item and return
	    return itemFromFile;
	}
	
	/**
	 * Loads all items in the INVENTORY_FILE out to items.
	 * 
	 * @throws VendingMachinePersistenceException if an error occurs loading from the INVENTORY_FILE
	 */
	private void loadInventory() throws VendingMachinePersistenceException {
	    Scanner scanner;

	    try {
	        // Create Scanner for reading the file
	        scanner = new Scanner(
	                new BufferedReader(
	                        new FileReader(INVENTORY_FILE)));
	    } catch (FileNotFoundException e) {
	        throw new VendingMachinePersistenceException(
	                "-_- Could not load roster data into memory.", e);
	    }
	    // currentLine holds the most recent line read from the file
	    String currentLine;
	    // currentItem holds the most recent item unmarshalled
	    Item currentItem;
	    // Go through INVENTORY_FILE line by line, decoding each line into a 
	    // Student object by calling the unmarshallItem method.
	    // Process while we have more lines in the file
	    while (scanner.hasNextLine()) {
	        // get the next line in the file
	        currentLine = scanner.nextLine();
	        // unmarshall the line into a Item
	        currentItem = unmarshallItem(currentLine);
	        
	        // We are going to use the item id as the map key for our Item object.
	        // Put currentItem into the map using item id as the key
	        items.put(currentItem.getItemId(), currentItem);
	    }
	    // close scanner
	    scanner.close();
	}
	
	private String marshallItem(Item aItem){
	    // We need to turn a Item object into a line of text for our file.
	    // For example, the memory object will end up like this:
	    // 1::Coca-Cola::1.20::12

	    // Start with the item id, since that's supposed to be first.
	    String itemAsText = aItem.getItemId() + DELIMITER;

	    // add the rest of the properties in the correct order:

	    // ItemName
	    itemAsText += aItem.getItemName() + DELIMITER;

	    // Cost
	    itemAsText += aItem.getCost() + DELIMITER;

	    // Quantity
	    itemAsText += aItem.getQuantity();

	    // Hence converted item to text and return
	    return itemAsText;
	}
	
	/**
	 * Writes all items in the inventory out to a INVENTORY_FILE. 
	 * See loaInventory for file format.
	 * 
	 * @throws VendingMachinePersistenceException if an error occurs writing to the file
	 */
	private void writeInventory() throws VendingMachinePersistenceException {
	    
	    PrintWriter out;

	    try {
	        out = new PrintWriter(new FileWriter(INVENTORY_FILE));
	    } catch (IOException e) {
	        throw new VendingMachinePersistenceException(
	                "Could not save item data.", e);
	    }

	    // Write out the Item objects to the inventory file.
	    String itemAsText;
	    List<Item> itemList = this.getAllItems();
	    for (Item currentItem : itemList) {
	        // turn a Item into a String
	        itemAsText = marshallItem(currentItem);
	        // write the Item object to the file
	        out.println(itemAsText);
	        // force PrintWriter to write line to the file
	        out.flush();
	    }
	    // Clean up
	    out.close();
	}
	
} //class
