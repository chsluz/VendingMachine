package com.vendingmachine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.vendingmachine.util.Utils;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CoinsInvalidException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Coin need to be in: "+Utils.getValidCoins();
	}

}
