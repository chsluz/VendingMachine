package com.vendingmachine.service;

import java.util.List;
import java.util.Optional;

import com.vendingmachine.entity.Product;

public interface ProductService {
	
	Product save(Product product);

	Optional<Product> findById(Integer productId);

	Product edit(Product product, Product edit);

	void delete(Integer id);

	List<Product> listAll();

}
