package com.vendingmachine.service;

public class NoItemIdFoundInInventoryException extends Exception {

	public NoItemIdFoundInInventoryException(String message) {
		super(message);
    }
	
	public NoItemIdFoundInInventoryException(String message,Throwable cause) {
		super(message, cause);
    }

}
