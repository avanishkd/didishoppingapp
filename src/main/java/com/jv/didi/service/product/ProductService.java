package com.jv.didi.service.product;

import java.util.Collection;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.jv.didi.dto.product.ProductAddDTO;
import com.jv.didi.dto.product.response.ProductResponseDTO;
import com.jv.didi.entity.product.Product;

@Validated
public interface ProductService {

	@NotNull
	Iterable<Product> getAllProducts();

	Product getProduct(@Min(value = 1L, message = "Invalid product ID.") long id);

	Product save(Product product);
	
	ProductResponseDTO addProduct(ProductAddDTO productDto);

	Collection<Product> getAllProductsByIds(long[] listId);

	ProductResponseDTO updateProduct(ProductResponseDTO productDto);
}