package com.vendingmachine.util;

import java.util.Arrays;
import java.util.List;

public class Utils {

	private static final List<Integer> validCoins = Arrays.asList(5,10,20,50,100);
	
	public static List<Integer> getValidCoins() {
		return validCoins;
	}
}
