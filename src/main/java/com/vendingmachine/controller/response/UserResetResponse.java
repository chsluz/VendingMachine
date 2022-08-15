package com.vendingmachine.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResetResponse {
	private Integer withdrawal;
	private Integer currentDeposity;
}
