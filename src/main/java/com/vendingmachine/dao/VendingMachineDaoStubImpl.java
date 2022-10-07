package com.vendingmachine.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vendingmachine.dto.Item;

public class VendingMachineDaoStubImpl implements VendingMachineDao {
 
	public Map<String, Item> testItemsMap;
	
    public VendingMachineDaoStubImpl() {
        
        Item item1 = new Item("12");
	    item1.setItemName("Red Bull");
	    item1.setCost("1.60");
	    item1.setQuantity(15);
	    
	    Item item2 = new Item("18");
	    item2.setItemName("Pepsi");
	    item2.setCost("1.30");
	    item2.setQuantity(0);
	    
	    this.testItemsMap = new HashMap<String, Item>();
	    testItemsMap.put(item1.getItemId(), item1);
	    testItemsMap.put(item2.getItemId(), item2);
    }

    public VendingMachineDaoStubImpl(Map<String, Item> testItemsMap){
         this.testItemsMap = testItemsMap;
     }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        return new ArrayList<Item>(testItemsMap.values());
    }

    @Override
    public Item getItem(String itemId) throws VendingMachinePersistenceException {
    	return testItemsMap.get(itemId);  
    }

	@Override
	public Item purchaseItem(String itemId) throws VendingMachinePersistenceException {
		testItemsMap.get(itemId).setQuantity(testItemsMap.get(itemId).getQuantity() - 1);
		return testItemsMap.get(itemId); 
    }

}
