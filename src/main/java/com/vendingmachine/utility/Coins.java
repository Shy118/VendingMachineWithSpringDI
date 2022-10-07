package com.vendingmachine.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum Coins {
	Penny("0.01"), Nickel("0.05"), Dime("0.1"), Quarter("0.25");
	BigDecimal value;
	Coins(String value){
		this.value = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
	}
	
	public BigDecimal getValue() {
		return value;
	}
	
} //enum
	