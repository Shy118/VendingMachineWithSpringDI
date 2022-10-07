package com.vendingmachine.dto;

import java.util.Objects;

public class Item {
	private String itemId;
	private String itemName;
	private String cost;
	private int quantity;
	
	public Item(String itemId) {
		this.itemId = itemId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", itemName=" + itemName + ", cost=" + cost + ", quantity=" + quantity + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cost, itemId, itemName, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(cost, other.cost) && Objects.equals(itemId, other.itemId)
				&& Objects.equals(itemName, other.itemName) && quantity == other.quantity;
	}
	
}
