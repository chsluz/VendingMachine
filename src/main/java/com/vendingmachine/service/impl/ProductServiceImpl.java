package com.vendingmachine.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vendingmachine.entity.Product;
import com.vendingmachine.repository.ProductRepository;
import com.vendingmachine.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepository productRepository;

	@Override
	public Product save(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Optional<Product> findById(Integer productId) {
		return productRepository.findById(productId);
	}

	@Override
	public Product edit(Product product, Product edit) {
		edit.setId(product.getId());
		return productRepository.save(edit);
	}

	@Override
	public void delete(Integer id) {
		productRepository.deleteById(id);
	}

	@Override
	public List<Product> listAll() {
		return productRepository.findAll();
	}

}
