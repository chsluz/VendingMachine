package com.vendingmachine.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDepositResponse {
	private Integer id;
	private String name;
	private Integer deposit;

}
