package com.vendingmachine.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.vendingmachine.controller.api.ProductApi;
import com.vendingmachine.controller.response.BuyResponse;
import com.vendingmachine.controller.response.ProductBuyResponse;
import com.vendingmachine.controller.response.ProductResponse;
import com.vendingmachine.entity.Product;
import com.vendingmachine.entity.User;
import com.vendingmachine.exception.CostInvalidException;
import com.vendingmachine.exception.FundsException;
import com.vendingmachine.exception.ProductNotFoundException;
import com.vendingmachine.exception.QuantityInvalidException;
import com.vendingmachine.exception.UserNotFoundException;
import com.vendingmachine.service.ProductService;
import com.vendingmachine.service.UserService;
import com.vendingmachine.util.Utils;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductBusiness {
	
	private final ModelMapper modelMapper;
	
	private final ProductService productService;
	
	private final UserService userService;

	public void saveProduct(ProductApi api) {
		Product product = modelMapper.map(api, Product.class);
		if(product.getCost() % 5 != 0) {
			throw new CostInvalidException();
		}
		productService.save(product);
	}
	
	public Product editProduct(Integer userId, Integer productId, ProductApi api) {
		Optional<Product> find = productService.findById(productId);
		if(find.isPresent()) {
			Product edit = modelMapper.map(api, Product.class);
			return productService.edit(find.get(),edit);
		}else {
			throw new ProductNotFoundException();
		}
	}
	
	public void deleteProduct(Integer userId, Integer productId) {
		Optional<Product> find = productService.findById(productId);
		if(find.isPresent()) {
			productService.delete(productId);
		}else {
			throw new ProductNotFoundException();
		}
	}
	
	public List<ProductResponse> getProducts() {
		List<Product> products = productService.listAll();
		List<ProductResponse> response = new ArrayList<>();
		products.forEach(p -> {
			response.add(modelMapper.map(p, ProductResponse.class));
		});
		return response;
	}

	public BuyResponse buyProduct(Integer userId, Integer productId, Integer quantity) {
		Optional<User> findUser = userService.getUserById(userId);
		Optional<Product> findProduct = productService.findById(productId);
		if(!findUser.isPresent()) {
			throw new UserNotFoundException();
		}
		if(!findProduct.isPresent()) {
			throw new ProductNotFoundException();
		}
		Product product = findProduct.get();
		User user = findUser.get();
		if(quantity > product.getAmountAvailable()) {
			throw new QuantityInvalidException();
		}
		if(product.getCost() * quantity > user.getDeposit()) {
			throw new FundsException();
		}
		BuyResponse response = new BuyResponse();
		response.setProduct(modelMapper.map(product, ProductBuyResponse.class));
		response.setTotal(quantity * product.getCost());
		int change = user.getDeposit() - response.getTotal();
		if(Utils.getValidCoins().contains(change)) {
			response.setChange(change);
			user.setDeposit(0);
		}
		product.setAmountAvailable(product.getAmountAvailable() - quantity);
		userService.save(user);
		productService.save(product);
		return response;
	}
}
