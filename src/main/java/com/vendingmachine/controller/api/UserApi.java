package com.vendingmachine.controller.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserApi {
	private String name;
	private String username;
	private String password;
}
