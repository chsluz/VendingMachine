package com.vendingmachine.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vendingmachine.business.UserBusiness;
import com.vendingmachine.controller.api.RoleUserApi;
import com.vendingmachine.controller.api.UserApi;
import com.vendingmachine.controller.response.UserDepositResponse;
import com.vendingmachine.controller.response.UserResetResponse;
import com.vendingmachine.controller.response.UserResponse;
import com.vendingmachine.exception.UserNotFoundException;
import com.vendingmachine.exception.UserRoleNotFoundException;
import com.vendingmachine.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	private final UserBusiness userBusiness;
	
	@GetMapping
	public ResponseEntity<List<UserResponse>> listAll() {
		List<UserResponse> response = userBusiness.getUsers();
		return ResponseEntity.ok(response);
	}
	
	@PostMapping
	public ResponseEntity<UserResponse> createUser(@RequestBody UserApi api) {
	  UserResponse response = userBusiness.createUser(api);
	  return ResponseEntity.ok(response);
	}
	
	@PostMapping("add-role-user")
	public ResponseEntity<Object> addRoleToUser(@RequestBody RoleUserApi api) {
		userService.addRoleToUser(api.getUserName(), api.getRoleName());
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/{userId}/deposit/{amount}")
	@PreAuthorize("hasAuthority('BUYER')")
	public ResponseEntity<UserDepositResponse> addDeposity(@PathVariable Integer userId,@PathVariable Integer amount) throws UserNotFoundException {
		UserDepositResponse response = userBusiness.addDeposit(userId,amount);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping(
			value="/{userId}/reset",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('BUYER')")
	public ResponseEntity<UserResetResponse> resetDeposit(@PathVariable Integer userId) {
		UserResetResponse response = userBusiness.reset(userId);
		return ResponseEntity.ok(response);
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private void carNotFoundHandler(UserRoleNotFoundException ex) {}
}
