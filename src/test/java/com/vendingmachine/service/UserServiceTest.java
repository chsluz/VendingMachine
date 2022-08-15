package com.vendingmachine.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.vendingmachine.entity.User;
import com.vendingmachine.exception.UserRoleNotFoundException;
import com.vendingmachine.repository.RoleRepository;
import com.vendingmachine.repository.UserRepository;
import com.vendingmachine.service.impl.UserServiceImpl;

@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private RoleRepository roleRepository;
	
	@Mock
	private UserService userService;
	
	private User userForTest;
	
	
	@BeforeAll
	public void init() {
		userForTest = new User(1, "test name", "test", "123456",0, new HashSet<>());
		MockitoAnnotations.openMocks(this);
		userService = new UserServiceImpl(userRepository, roleRepository, new BCryptPasswordEncoder());
	}
	
	@Test
	public void shouldCreateUser() {
		when(userRepository.save(any())).thenReturn(userForTest);
		User save = userService.save(userForTest);
		assertThat(save.getName()).isEqualTo("test name");
		assertThat(save.getUsername()).isEqualTo("test");
	}
	
	@Test
	public void shouldEditUser() {
		ModelMapper mapper = new ModelMapper();
		User edit = mapper.map(userForTest, User.class);
		edit.setDeposit(100);
		when(userRepository.save(any())).thenReturn(edit);
		assertThat(edit.getDeposit()).isEqualTo(100);
		assertThat(edit.getUsername()).isEqualTo("test");
	}
	
	@Test
	public void shouldDeleteUser() {
		doNothing().when(userRepository).deleteById(any());
		userService.deleteUser(userForTest.getId());
	}

	
	@Test
	public void shouldReturnUser() {
		when(userRepository.findByUsername(anyString())).thenReturn(userForTest);
		User find = userService.getUser("test");
		assertThat(find.getName()).isEqualTo("test name");
		assertThat(find.getUsername()).isEqualTo("test");
	}
	
	@Test
	public void shouldGivenErrorWhenPassInvalidUser() {
		Throwable exception = assertThrows(UserRoleNotFoundException.class, () -> userService.addRoleToUser("any","any"));
	    assertEquals("User or Role not found", exception.getMessage());
	}

}
