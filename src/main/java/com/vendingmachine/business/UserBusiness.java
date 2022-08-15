package com.vendingmachine.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vendingmachine.controller.api.UserApi;
import com.vendingmachine.controller.response.UserDepositResponse;
import com.vendingmachine.controller.response.UserResetResponse;
import com.vendingmachine.controller.response.UserResponse;
import com.vendingmachine.entity.User;
import com.vendingmachine.exception.CoinsInvalidException;
import com.vendingmachine.exception.UserAlreadyExistException;
import com.vendingmachine.exception.UserNotFoundException;
import com.vendingmachine.service.UserService;
import com.vendingmachine.util.Utils;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserBusiness {
	
	@Autowired
	private ModelMapper modelMapper;
	private final UserService userService;

	public UserDepositResponse addDeposit(Integer userId, Integer amount){
		Optional<User> find = findUser(userId);
		if(!find.isPresent()) {
			throw new UserNotFoundException();
		}
		if(!Utils.getValidCoins().contains(amount) ) {
			throw new CoinsInvalidException();
		}
		User user = find.get();
		user.setDeposit(user.getDeposit()+ amount);
		user = userService.save(user);
		return modelMapper.map(user, UserDepositResponse.class); 
	}
	
	public Optional<User> findUser(Integer id) {
		return userService.getUserById(id);
	}

	public UserResetResponse reset(Integer userId) {
		Optional<User> find = findUser(userId);
		if(!find.isPresent()) {
			throw new UserNotFoundException();
		}
		UserResetResponse response = new UserResetResponse();
		User user = find.get();
		response.setWithdrawal(user.getDeposit());
		user.setDeposit(0);
		userService.save(user);
		response.setCurrentDeposity(user.getDeposit());
		return response;
	}

	public UserResponse createUser(UserApi api) {
		ModelMapper modelMapper = new ModelMapper();
		User user = modelMapper.map(api, User.class);
		User find = userService.getUser(api.getUsername());
		if(find != null) {
			throw new UserAlreadyExistException();
		}
		user = userService.save(user);
		return modelMapper.map(user, UserResponse.class);
	}

	public List<UserResponse> getUsers() {
		List<UserResponse> response = new ArrayList<>();
		List<User> users = userService.getUsers();
		users.forEach(u -> response.add(modelMapper.map(u, UserResponse.class)));
		return response;
	}

}
