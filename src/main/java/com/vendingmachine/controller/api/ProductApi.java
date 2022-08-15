package com.vendingmachine.controller.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductApi {
	private Integer id;
	private String name;
	private Integer amountAvailable;
	private Integer cost;
	private Integer userId;
}
