package com.vendingmachine.business;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.vendingmachine.controller.response.UserDepositResponse;
import com.vendingmachine.entity.User;
import com.vendingmachine.exception.CoinsInvalidException;
import com.vendingmachine.repository.RoleRepository;
import com.vendingmachine.repository.UserRepository;
import com.vendingmachine.service.UserService;
import com.vendingmachine.service.impl.UserServiceImpl;
import com.vendingmachine.util.Utils;

@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class UserBusinessTest {
	
	@Mock
	private UserBusiness userBusiness;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private RoleRepository roleRepository;
	
	private User user;
	
	@Mock
	private UserService userService;
	
	@BeforeAll
	public void init() {
		MockitoAnnotations.openMocks(this);
		userService = new UserServiceImpl(userRepository, roleRepository, new BCryptPasswordEncoder());
		user = new User(1, "test", "test", "123456", 0, new HashSet<>());
		userBusiness = new UserBusiness(new ModelMapper(), userService);
	}
	
	@Test
	public void shouldGiveErrorWhenSendCoin25() {
		when(userService.getUserById(anyInt())).thenReturn(Optional.of(user));
		Throwable exception = assertThrows(CoinsInvalidException.class, () -> userBusiness.addDeposit(user.getId(), 25));
	    assertEquals("Coin need to be in: "+Utils.getValidCoins(), exception.getMessage());
	}
	
	@Test
	public void shouldAddDepositOf100() {
		when(userService.getUserById(anyInt())).thenReturn(Optional.of(user));
		when(userService.save(user)).thenReturn(user);
		UserDepositResponse deposit = userBusiness.addDeposit(user.getId(), 100);
		assertThat(deposit.getDeposit()).isEqualTo(100);
	}

}
