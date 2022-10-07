package com.vendingmachine.utility;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public final class Change {

	public static Coins getPenny() {
		return Coins.Penny;
	}

	public static Coins getNickel() {
		return Coins.Nickel;
	}

	public static Coins getDime() {
		return Coins.Dime;
	}

	public static Coins getQuarter() {
		return Coins.Quarter;
	}
	
	public static Map<Coins, Integer> doChange(BigDecimal greater, BigDecimal lesser) {
		int penny = 0; int nickel = 0; int dime = 0; int quarter = 0;
		Map<Coins, Integer> change = new HashMap<>();

		BigDecimal changeAmt = greater.subtract(lesser);
		
		while (changeAmt.compareTo(getQuarter().getValue()) >= 0 ) {
			changeAmt = changeAmt.subtract(getQuarter().getValue());
			quarter++;
		} //while
		
		while (changeAmt.compareTo(getDime().getValue()) >= 0) {
			changeAmt = changeAmt.subtract(getDime().getValue());
			dime++;
		} //while
		
		while (changeAmt.compareTo(getNickel().getValue()) >= 0) {
			changeAmt = changeAmt.subtract(getNickel().getValue());
			nickel++;
		} //while
		
		while (changeAmt.compareTo(getPenny().getValue()) >= 0) {
			changeAmt = changeAmt.subtract(getPenny().getValue());
			penny++;
		} //while
		
		change.put(getQuarter(), quarter);
		change.put(getDime(), dime);
		change.put(getNickel(), nickel);
		change.put(getPenny(), penny);
		
		return change;
	} //method

} //class
