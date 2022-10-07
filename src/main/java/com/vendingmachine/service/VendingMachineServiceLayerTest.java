package com.vendingmachine.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.vendingmachine.dao.VendingMachineAuditDao;
import com.vendingmachine.dao.VendingMachineAuditDaoStubImpl;
import com.vendingmachine.dao.VendingMachineDao;
import com.vendingmachine.dao.VendingMachineDaoStubImpl;
import com.vendingmachine.dto.Item;
import com.vendingmachine.utility.Coins;

class VendingMachineServiceLayerTest {
	VendingMachineDao dao = new VendingMachineDaoStubImpl();
	VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();
	VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao, auditDao);
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	public void testGetItem() throws Exception {
	    // ARRANGE
	    Item testClone1 = new Item("12");
        testClone1.setItemName("Red Bull");
        testClone1.setCost("1.60");
        testClone1.setQuantity(15);
        
        Item testClone2 = new Item("18");
        testClone2.setItemName("Pepsi");
        testClone2.setCost("1.30");
        testClone2.setQuantity(0);

	    // ACT & ASSERT
	    Item shouldBeRedBull = service.getItem("12");
	    assertNotNull(shouldBeRedBull, "Getting 12 should be not null.");
	    assertEquals(testClone1, shouldBeRedBull, "item stored under 12 should be red Bull.");

	    Item shouldBePepsi = service.getItem("18");
	    assertNotNull(shouldBeRedBull, "Getting 18 should be not null.");
	    assertEquals(testClone2, shouldBePepsi, "item stored under 18 should be red Bull.");
	    
	    Item shouldBeNull = service.getItem("15");    
	    assertNull(shouldBeNull, "Getting 15 should be null.");
	} //Test
	
	@Test
	public void testGetAllItems() throws Exception {
	    // ARRANGE
		Item testClone1 = new Item("12");
        testClone1.setItemName("Red Bull");
        testClone1.setCost("1.60");
        testClone1.setQuantity(15);

        Item testClone2 = new Item("18");
        testClone2.setItemName("Pepsi");
        testClone2.setCost("1.30");
        testClone2.setQuantity(0);
        
	    // ACT & ASSERT
	    assertEquals(2, service.getAllItems().size(), "Should have two items.");
	    assertTrue(service.getAllItems().contains(testClone1), "The item 12 should be Red Bull.");
	    assertTrue(service.getAllItems().contains(testClone2), "The item 18 should be Pepsi.");
	} //Test
	
	@Test
	public void testPurchase() throws Exception {
	    // ARRANGE
		Item testPurchasedClone1 = new Item("12");
		testPurchasedClone1.setItemName("Red Bull");
		testPurchasedClone1.setCost("1.60");
		testPurchasedClone1.setQuantity(14);

        Map<Coins, Integer> testPurchasedCloneChange1 = new HashMap<Coins, Integer>();
        testPurchasedCloneChange1.put(Coins.Quarter, 0);
        testPurchasedCloneChange1.put(Coins.Dime, 2);
        testPurchasedCloneChange1.put(Coins.Nickel, 0);
        testPurchasedCloneChange1.put(Coins.Penny, 0);
        
        Item testClone2 = new Item("18");
        testClone2.setItemName("Pepsi");
        testClone2.setCost("1.30");
        testClone2.setQuantity(0);
        
        Map<Coins, Integer> ChangeReceivedAfterPurchasingRedBull = service.purchaseItem("12", "1.80");
        Item shouldBePurchasedRedBull = service.getItem("12");
        Item shouldBePepsi = service.getItem("18");
        
        // ACT & ASSERT
        assertEquals(2, service.getAllItems().size(), "Should still have two items.");
        assertEquals(testPurchasedCloneChange1, ChangeReceivedAfterPurchasingRedBull, "Change received should be cost minus insert amount.");
        assertEquals(testPurchasedClone1, shouldBePurchasedRedBull, "Purchasing 18 result in reducing its quantity by 1.");
	    assertEquals(testClone2, shouldBePepsi, "The item 18 should be Pepsi.");
	} //Test
	
	@Test
	public void testPurchaseItemIdNotInInventory() throws Exception {
		// ARRANGE
		Item testClone1 = new Item("12");
        testClone1.setItemName("Red Bull");
        testClone1.setCost("1.60");
        testClone1.setQuantity(15);

        Item testClone2 = new Item("18");
        testClone2.setItemName("Pepsi");
        testClone2.setCost("1.30");
        testClone2.setQuantity(0);
        
        NoItemIdFoundInInventoryException thrown = Assertions.assertThrows(NoItemIdFoundInInventoryException.class, () -> {
        	service.purchaseItem("15", "1.80");
        } , "NoItemIdFoundInInventoryException error was expected");
         
        
        Item shouldBeRedBull = service.getItem("12");
        Item shouldBePepsi = service.getItem("18");
        
        // ACT & ASSERT
        assertTrue(thrown.getMessage().contains("ERROR: The item you have selected is not found in inventory"));
        assertEquals(2, service.getAllItems().size(), "Should still have two items.");
        assertEquals(testClone1, shouldBeRedBull, "Purchasing 12 sould not result in reducing its quantity by 1.");
	    assertEquals(testClone2, shouldBePepsi, "The item 18 should be Pepsi.");
	} //Test
	
	@Test
	public void testPurchaseInsufficientFunds() throws Exception {
	    // ARRANGE
		Item testClone1 = new Item("12");
        testClone1.setItemName("Red Bull");
        testClone1.setCost("1.60");
        testClone1.setQuantity(15);
        
        Item testClone2 = new Item("18");
        testClone2.setItemName("Pepsi");
        testClone2.setCost("1.30");
        testClone2.setQuantity(0);

        InsufficientFundsException thrown = Assertions.assertThrows(InsufficientFundsException.class, () -> {
        	service.purchaseItem("12", "1.20");
        } , "InsufficientFundsException error was expected");
            
        Item shouldBeRedBull = service.getItem("12");
        Item shouldBePepsi = service.getItem("18");
        
	    // ACT & ASSERT
        assertTrue(thrown.getMessage().contains("Error: Insufficient fund to make purchase"));
        assertEquals(2, service.getAllItems().size(), "Should still have two items.");
        assertEquals(testClone1, shouldBeRedBull, "Purchasing 12 sould not result in reducing its quantity by 1.");
	    assertEquals(testClone2, shouldBePepsi, "The item 18 should be Pepsi.");
	} //Test
	
	@Test
	public void testPurchaseItemOutOfStock() throws Exception {
		// ARRANGE
		Item testClone1 = new Item("12");
        testClone1.setItemName("Red Bull");
        testClone1.setCost("1.60");
        testClone1.setQuantity(15);

        Item testClone2 = new Item("18");
        testClone2.setItemName("Pepsi");
        testClone2.setCost("1.30");
        testClone2.setQuantity(0);
        
        OutOfStockException thrown = Assertions.assertThrows(OutOfStockException.class, () -> {
        	service.purchaseItem("18", "1.88");
        } ,"OutOfStockException error was expected");
            
        Item shouldBeRedBull = service.getItem("12");
        Item shouldBePepsi = service.getItem("18");
        
	    // ACT & ASSERT
        assertTrue(thrown.getMessage().contains("ERROR: The item you have selected is out of stock"));
        assertEquals(2, service.getAllItems().size(), "Should still have two items.");
        assertEquals(testClone1, shouldBeRedBull, "The item 12 should be Red Bull.");
        assertEquals(testClone2, shouldBePepsi, "Purchasing 18 should not result in reducing its quantity by 1.");
	} //Test
	
} //class
