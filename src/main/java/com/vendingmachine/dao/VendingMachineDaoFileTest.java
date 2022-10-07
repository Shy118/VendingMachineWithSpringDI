package com.vendingmachine.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.vendingmachine.dto.Item;

class VendingMachineDaoFileTest {
	VendingMachineDao testDao;
	
	public VendingMachineDaoFileTest() {
		
	}
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		String testFile = "testinventory.txt";
        String DELIMITER = "::";
        
        PrintWriter out;
        
        out = new PrintWriter(new FileWriter(testFile));
	    
        List<Item> itemList = new ArrayList<>();
        
		String aItemId1 = "12";
	    Item aItem1 = new Item(aItemId1);
	    aItem1.setItemName("Red Bull");
	    aItem1.setCost("1.60");
	    aItem1.setQuantity(15);
	    
	    String aItemId2 = "18";
	    Item aItem2 = new Item(aItemId2);
	    aItem2.setItemName("Pepsi");
	    aItem2.setCost("1.30");
	    aItem2.setQuantity(1);
	    
	    itemList.add(aItem1); itemList.add(aItem2);
	    
	    for (Item item : itemList) {
		    String itemAsText = item.getItemId() + DELIMITER;
		    // ItemName
		    itemAsText += item.getItemName() + DELIMITER;
		    // Cost
		    itemAsText += item.getCost() + DELIMITER;
		    // Quantity
		    itemAsText += item.getQuantity();
		    
		    out.println(itemAsText);
		    
		    out.flush();
	    }
	    
	    out.close();
        testDao = new VendingMachineDaoFileImpl(testFile);
	}
	
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void testGetItem() throws Exception {

		String itemId = "12";
		String itemName = "Red Bull";
		String itemCost = "1.60";
		int itemQuantity = 15;
		
	    // Get the item from the DAO
	    Item retrievedItem = testDao.getItem(itemId);

	    // Check the data is equal
	    assertEquals(itemId, retrievedItem.getItemId(), "Checking item id.");
	    
	    assertEquals(itemName,retrievedItem.getItemName(), "Checking item name.");
	    
	    assertEquals(itemCost,retrievedItem.getCost(), "Checking item cost.");
	    
	    assertEquals(itemQuantity, retrievedItem.getQuantity(),"Checking item quantity.");
	} //Test

	@Test
	public void testGetAllItems() throws Exception {

	    // Retrieve the list of all items within the DAO
	    List<Item> allItems = testDao.getAllItems();

	    String firstItemId = "12";
	    Item firstItem = new Item(firstItemId);
	    firstItem.setItemName("Red Bull");
	    firstItem.setCost("1.60");
	    firstItem.setQuantity(15);
	    
	    String secondItemId = "18";
	    Item secondItem = new Item(secondItemId);
	    secondItem.setItemName("Pepsi");
	    secondItem.setCost("1.30");
	    secondItem.setQuantity(1);
	    
	    // First check the general contents of the list
	    assertNotNull(allItems, "The list of items must not null");
	    assertEquals(2, allItems.size(),"List of items should have 2 items.");

	    // Then the specifics
	    assertTrue(testDao.getAllItems().contains(firstItem),
	                "The list of items should include Red Bull.");
	    assertTrue(testDao.getAllItems().contains(secondItem),
	            "The list of items should include Pepsi.");
	} //Test
	
	@Test
	public void testPurchaseItem() throws Exception {
	    // Create two purchased items
		String firstAfterPurchasedItemId = "12";
	    Item firstAfterPurchasedItem = new Item(firstAfterPurchasedItemId);
	    firstAfterPurchasedItem.setItemName("Red Bull");
	    firstAfterPurchasedItem.setCost("1.60");
	    firstAfterPurchasedItem.setQuantity(14);
	    
	    String secondAfterPurchasedItemId = "18";
	    Item secondAfterPurchasedItem = new Item(secondAfterPurchasedItemId);
	    secondAfterPurchasedItem.setItemName("Pepsi");
	    secondAfterPurchasedItem.setCost("1.30");
	    secondAfterPurchasedItem.setQuantity(0);

	    Item firstPurchasedItem = testDao.purchaseItem(firstAfterPurchasedItemId);
	    Item secondPurchasedItem = testDao.purchaseItem(secondAfterPurchasedItemId);
	    
	    // Check that the correct item was purchased.
	    assertEquals(firstPurchasedItem, firstAfterPurchasedItem, "The purchased item should be Red Bull.");
	    assertEquals(secondPurchasedItem, secondAfterPurchasedItem, "The purchased item should be Pepsi.");
	    
	    // Get all the items
	    List<Item> allItems = testDao.getAllItems();

	    // First check the general contents of the list
	    assertNotNull(allItems, "All items list should be not null.");
	    assertEquals(2, allItems.size(), "All Items should still have 2 items.");

	    // Then the specifics
	    assertTrue(testDao.getAllItems().contains(firstAfterPurchasedItem),"The list of items should include Red Bull.");
	    assertTrue(testDao.getAllItems().contains(secondAfterPurchasedItem),"The list of items should include Pepsi."); 
	} //Test

} //class
