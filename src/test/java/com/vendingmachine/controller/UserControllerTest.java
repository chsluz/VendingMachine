package com.vendingmachine.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vendingmachine.business.UserBusiness;
import com.vendingmachine.controller.api.UserApi;
import com.vendingmachine.entity.User;
import com.vendingmachine.security.SecurityConfig;
import com.vendingmachine.service.RoleService;
import com.vendingmachine.service.UserService;

@WebMvcTest(controllers = UserController.class, excludeAutoConfiguration = { SecurityConfig.class })
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	
	@MockBean
	private UserBusiness userBusiness;

	@MockBean
	private UserDetailsService userDetailsService;

	@MockBean
	private RoleService roleService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void shouldCreateAnUser() throws Exception {
		UserApi api = new UserApi("test user", "test", "123456");
		 when(userService.save(any())).thenReturn(new User(1, "test user", "test",
		 "123456",0, new HashSet<>()));
		mockMvc.perform(MockMvcRequestBuilders.post("/api/users").content(objectMapper.writeValueAsString(api))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

}
