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

import com.vendingmachine.controller.response.BuyResponse;
import com.vendingmachine.entity.Product;
import com.vendingmachine.entity.User;
import com.vendingmachine.exception.FundsException;
import com.vendingmachine.exception.ProductNotFoundException;
import com.vendingmachine.exception.QuantityInvalidException;
import com.vendingmachine.exception.UserNotFoundException;
import com.vendingmachine.repository.ProductRepository;
import com.vendingmachine.repository.RoleRepository;
import com.vendingmachine.repository.UserRepository;
import com.vendingmachine.service.ProductService;
import com.vendingmachine.service.UserService;
import com.vendingmachine.service.impl.ProductServiceImpl;
import com.vendingmachine.service.impl.UserServiceImpl;

@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class ProductBusinessTest {
	
	@Mock
	private ProductBusiness productBusiness;
	
	@Mock
	private ProductService productService;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private RoleRepository roleRepository;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private UserService userService;
	
	private User user;
	
	private Product product;
	
	@BeforeAll
	public void init() {
		MockitoAnnotations.openMocks(this);
		productService = new ProductServiceImpl(productRepository);
		userService = new UserServiceImpl(userRepository, roleRepository, new BCryptPasswordEncoder());
		productBusiness = new ProductBusiness(new ModelMapper(), productService, userService);
		user = new User(1, "test", "test", "123456", 0, new HashSet<>());
		product = new Product(11111, "Product 1", 10, 10,user);
	}
	
	@Test
	public void shouldFailedWhenPassInvalidUserIdOnBuyProduct() {
		when(userService.getUserById(anyInt())).thenReturn(Optional.empty());
		Throwable exception = assertThrows(UserNotFoundException.class, () -> productBusiness.buyProduct(user.getId(), product.getId(), 1));
	    assertEquals("User not found", exception.getMessage());
	}
	
	@Test
	public void shouldFailedWhenPassInvalidProductIdOnBuyProduct() {
		when(userService.getUserById(anyInt())).thenReturn(Optional.of(user));
		when(productService.findById(anyInt())).thenReturn(Optional.empty());
		Throwable exception = assertThrows(ProductNotFoundException.class, () -> productBusiness.buyProduct(user.getId(), product.getId(), 1));
	    assertEquals("Product not found", exception.getMessage());
	}
	
	@Test
	public void shouldFailedWhenPassAmountGraterThan() {
		when(userService.getUserById(anyInt())).thenReturn(Optional.of(user));
		when(productService.findById(anyInt())).thenReturn(Optional.of(product));
		Throwable exception = assertThrows(QuantityInvalidException.class, () -> productBusiness.buyProduct(user.getId(), product.getId(), 20));
	    assertEquals("Quantity inválid", exception.getMessage());
	}
	
	@Test
	public void shouldFailedWhenProductCostsMoreThanDeposity() {
		when(userService.getUserById(anyInt())).thenReturn(Optional.of(user));
		when(productService.findById(anyInt())).thenReturn(Optional.of(product));
		Throwable exception = assertThrows(FundsException.class, () -> productBusiness.buyProduct(user.getId(), product.getId(), 1));
	    assertEquals("You dont have enought funds", exception.getMessage());
	}
	
	@Test
	public void shouldBuyProduct() {
		when(userService.getUserById(anyInt())).thenReturn(Optional.of(user));
		when(productService.findById(anyInt())).thenReturn(Optional.of(product));
		
		User copy = new ModelMapper().map(user, User.class);
		user.setDeposit(100);
		when(userService.save(copy)).thenReturn(copy);
		when(productService.save(product)).thenReturn(product);
		BuyResponse buyProduct = productBusiness.buyProduct(copy.getId(), product.getId(), 1);
		assertThat(buyProduct.getTotal()).isEqualTo(10);
		assertThat(buyProduct.getProduct().getId()).isEqualTo(11111);
		assertThat(buyProduct.getProduct().getId()).isEqualTo(11111);
		assertThat(buyProduct.getChange()).isEqualTo(0);
	}
}
