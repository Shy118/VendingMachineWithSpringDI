package com.vendingmachine.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import com.vendingmachine.dao.VendingMachineAuditDao;
import com.vendingmachine.dao.VendingMachineDao;
import com.vendingmachine.dao.VendingMachinePersistenceException;
import com.vendingmachine.dto.Item;
import com.vendingmachine.utility.Change;
import com.vendingmachine.utility.Coins;

public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {
	private VendingMachineDao dao;
	private VendingMachineAuditDao auditDao;
	
	public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
		this.dao = dao;
	    this.auditDao = auditDao;
	}

	@Override
	public List<Item> getAllItems() throws VendingMachinePersistenceException {
		return dao.getAllItems();
	}

	@Override
	public Item getItem(String itemId) throws VendingMachinePersistenceException {
		return dao.getItem(itemId);
	}

	@Override
	public Map<Coins, Integer> purchaseItem(String itemId, String insertAmount) throws VendingMachinePersistenceException, NoItemIdFoundInInventoryException, OutOfStockException, InsufficientFundsException {
		BigDecimal insertAmountBd = new BigDecimal(insertAmount).setScale(2, RoundingMode.HALF_UP);
		
		validateInventoryItemId(itemId, insertAmountBd);
		validateInventoryItem(itemId, insertAmountBd);
		
		BigDecimal costBd = new BigDecimal(dao.getItem(itemId).getCost()).setScale(2, RoundingMode.HALF_UP);
		
		validateFunds(itemId, insertAmountBd, costBd);
		
		Map<Coins, Integer> change = Change.doChange(insertAmountBd, costBd);
		
		String changeStr = "";
		
		for (Coins c : change.keySet()) {
			changeStr += c.toString() + ": " + change.get(c).toString() + " ";
		}
		changeStr.trim();
		
		Item purchasedItem = dao.purchaseItem(itemId);
		auditDao.writeAuditEntry("Item " + purchasedItem.getItemId() + " " + purchasedItem.getItemName() + " VENDED! | Quantity left in inventory - " + purchasedItem.getQuantity() + " | User inserted - $" + insertAmount + " | Change returned - [" + changeStr + "]");
		return change;
	}
	
	private void validateInventoryItem(String itemId, BigDecimal insertAmount) throws OutOfStockException, VendingMachinePersistenceException {
		if (dao.getItem(itemId).getQuantity() == 0) {
			auditDao.writeAuditEntry("User attempted to purchase " + dao.getItem(itemId) + " | Out-of-Stock | Change returned - [" + refundCoins(insertAmount) + "]");
			throw new OutOfStockException ("ERROR: The item you have selected is out of stock, please select other item\nPlease take your change:\n" + refundCoins(insertAmount));
		} //if
	}
	
	private void validateInventoryItemId(String itemId, BigDecimal insertAmount) throws  NoItemIdFoundInInventoryException, VendingMachinePersistenceException {
		if (dao.getItem(itemId) == null) {
			auditDao.writeAuditEntry("User attempted to purchase an item ID not found in inventory | Change returned - [" + refundCoins(insertAmount) + "]");
			throw new NoItemIdFoundInInventoryException ("ERROR: The item you have selected is not found in inventory, please select other item\nPlease take your change:\n" + refundCoins(insertAmount));
		} //if
	}
	
	private void validateFunds(String itemId, BigDecimal insertAmount, BigDecimal cost) throws InsufficientFundsException, VendingMachinePersistenceException {
		if (insertAmount.compareTo(cost) < 0 ) {
			auditDao.writeAuditEntry("User attempted to purchase " + dao.getItem(itemId).getItemName() + " with insufficient funds | Quantity left in inventory - " + dao.getItem(itemId).getQuantity() + " User Inserted: " + insertAmount + " | Requred: " + cost + " | change returned - [" + refundCoins(insertAmount) + "]");
			throw new InsufficientFundsException ("Error: Insufficient fund to make purchase | Inserted: $" + insertAmount + " | Required: $" + insertAmount + "\nPlease take your change:\n" + refundCoins(insertAmount));
		} //if
	}
	
	private String refundCoins(BigDecimal insertAmount) {
		Map<Coins, Integer> change = Change.doChange(insertAmount, new BigDecimal("0").setScale(2, RoundingMode.HALF_UP));
		String changeStr = "";
		for (Coins c : change.keySet()) {
			changeStr += c.toString() + ": " + change.get(c).toString() + " ";
		}
		changeStr.trim();
		return changeStr;
	}
} //class
