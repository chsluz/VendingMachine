package com.vendingmachine.service;

import java.util.List;
import java.util.Optional;

import com.vendingmachine.entity.User;

public interface UserService {
	User save(User user);
	void addRoleToUser(String username,String roleName);
	User getUser(String username);
	List<User> getUsers();
	Optional<User> getUserById(Integer id);
	void deleteUser(Integer id);
}
