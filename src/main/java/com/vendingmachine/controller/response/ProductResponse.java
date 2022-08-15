package com.vendingmachine.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
	private Integer id;
	private String name;
	private Integer amountAvailable;
	private Integer cost;
	private UserResponse user;
}
