package com.vendingmachine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vendingmachine.business.ProductBusiness;
import com.vendingmachine.controller.api.ProductApi;
import com.vendingmachine.controller.response.BuyResponse;
import com.vendingmachine.controller.response.ProductResponse;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	private ProductBusiness productBusiness;
	
	@PostMapping(
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('SELLER')")
	public ResponseEntity<Object> addProduct(@RequestBody ProductApi api) {
		productBusiness.saveProduct(api);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PutMapping(
			value = "/{userId}/{productId}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('SELLER')")
	public ResponseEntity<Object> editProduct(@PathVariable Integer userId,@PathVariable Integer productId, @RequestBody ProductApi api) {
		productBusiness.editProduct(userId,productId,api);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping(
			value = "/{userId}/{productId}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('SELLER')")
	public ResponseEntity<Object> deleteProduct(@PathVariable Integer userId,@PathVariable Integer productId) {
		productBusiness.deleteProduct(userId,productId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping(
			value="/{userId}/buy/{productId}/{quantity}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('BUYER')")
	public ResponseEntity<BuyResponse> buyProduct(@PathVariable Integer userId,@PathVariable Integer productId,@PathVariable Integer quantity) {
		BuyResponse response = productBusiness.buyProduct(userId,productId,quantity);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductResponse>> list() {
		return ResponseEntity.ok(productBusiness.getProducts());
	}
	

}
