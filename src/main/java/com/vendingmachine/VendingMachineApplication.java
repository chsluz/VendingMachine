package com.vendingmachine;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class VendingMachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendingMachineApplication.class, args);
	}
	
//	@Bean
//	CommandLineRunner run(RoleService roleService, UserService userService) {
//		return args -> {
//			roleService.save(new Role(null, "SELLER"));
//			roleService.save(new Role(null, "BUYER"));
//			
//			userService.save(new User(null, "test seller", "seller", "123456",0, new ArrayList<>()));
//			userService.save(new User(null, "test buyer", "buyer", "123456",0, new ArrayList<>()));
//			
//			userService.addRoleToUser("seller", "SELLER");
//			userService.addRoleToUser("buyer", "BUYER");
//		};
//	}
	
	@Bean	
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
