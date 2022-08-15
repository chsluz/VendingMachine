package com.vendingmachine.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyResponse {
	private Integer total;
	private ProductBuyResponse product;
	private int change;

}
