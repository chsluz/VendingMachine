package com.vendingmachine.controller.response;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	private Integer id;
	private String name;
	private Integer deposit;
    private Set<RoleResponse> roles;
}
