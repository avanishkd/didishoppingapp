package com.jv.didi.controller.product;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jv.didi.dto.product.ProductAddDTO;
import com.jv.didi.dto.product.response.ProductResponseDTO;
import com.jv.didi.dto.seller.SellerRegisterDTO;
import com.jv.didi.dto.seller.response.SellerRegisterResponse;
import com.jv.didi.entity.product.Product;
import com.jv.didi.entity.seller.Seller;
import com.jv.didi.exception.InvalidProductDetailsException;
import com.jv.didi.exception.UserSaveException;
import com.jv.didi.service.product.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	private ProductService productService;

	private Logger logger = LogManager.getLogger(ProductController.class);

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping(value = { "", "/" })
	public @NotNull Iterable<Product> getProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/{id}")
	public @NotNull Product getProductById(@PathVariable Long id) {
		return productService.getProduct(id);
	}

	@GetMapping("/list/{idArray}")
	public Collection<Product> getProductsByIds(@PathVariable long[] idArray) {
		Collection<Product> products = productService.getAllProductsByIds(idArray);
		if (idArray.length != products.size())
			logger.warn(
					"unable to fetch all products, some products with given id might not be avialable, products fetched->{}",
					products);

		logger.info(" all products with given id fetched successfully->{}", products);
		return products;
	}

	@PostMapping("/add")
	public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductAddDTO productDto) {

		ProductResponseDTO product = productService.addProduct(productDto);
		if (product == null) {
			logger.error("unable to add the product");
			throw new InvalidProductDetailsException("Exception occurred in adding product!");
		}
		logger.info("Product added with Id:" + product.getId());

		HttpHeaders headers = new HttpHeaders();
		headers.add("product-added", "yes");

		return new ResponseEntity<ProductResponseDTO>(product, headers, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<ProductResponseDTO> updateProduct(@RequestBody ProductResponseDTO productDto) {

		ProductResponseDTO product = productService.updateProduct(productDto);
		if (product == null) {
			logger.error("unable to update the product");
			throw new InvalidProductDetailsException("Exception occurred in updating product!");
		}
		logger.info("Product updated with Id:" + product.getId());

		HttpHeaders headers = new HttpHeaders();
		headers.add("product-updated", "yes");

		return new ResponseEntity<ProductResponseDTO>(product, headers, HttpStatus.OK);
	}

}
