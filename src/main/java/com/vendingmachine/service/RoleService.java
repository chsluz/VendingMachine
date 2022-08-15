package com.vendingmachine.service;

import java.util.List;

import com.vendingmachine.entity.Role;

public interface RoleService {
	Role save(Role role);
	List<Role> getRoles();
}
