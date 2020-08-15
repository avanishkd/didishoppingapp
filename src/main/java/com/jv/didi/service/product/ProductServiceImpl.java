package com.jv.didi.service.product;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import com.jv.didi.dao.product.ProductRepository;
import com.jv.didi.dto.product.ProductAddDTO;
import com.jv.didi.dto.product.response.ProductResponseDTO;
import com.jv.didi.entity.product.Product;
import com.jv.didi.entity.product.ProductStatus;
import com.jv.didi.exception.ResourceNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	@Transactional
	public Iterable<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	@Transactional
	public Product getProduct(long id) {
		return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
	}

	@Override
	@Transactional
	public Product save(Product product) {
		return productRepository.save(product);
	}

	@Override
	@Transactional
	public List<Product> getAllProductsByIds(long[] idArray) {

		List<Long> listId = Arrays.stream(idArray).boxed().collect(Collectors.toList());

		productRepository.findAllById(listId);
		List<Product> products = (List<Product>) productRepository.findAllById(listId);
		return products;
	}

	@Override
	@Transactional
	public ProductResponseDTO addProduct(ProductAddDTO productDto) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setAmbiguityIgnored(true);
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

		Product product = mapper.map(productDto, Product.class);
		product.setStatus(ProductStatus.OFFLINE.name());

		// TODO Auto-generated method stub
		Product saveProduct = productRepository.save(product);
		ProductResponseDTO responseProduct = mapper.map(saveProduct, ProductResponseDTO.class);
		return responseProduct;
	}

	@Override
	@Transactional
	public ProductResponseDTO updateProduct(ProductResponseDTO productDto) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setAmbiguityIgnored(true);
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

		Product product = mapper.map(productDto, Product.class);
		ProductStatus status = ProductStatus.valueOf(product.getStatus());

		// TODO Auto-generated method stub
		Product saveProduct = productRepository.save(product);
		ProductResponseDTO responseProduct = mapper.map(saveProduct, ProductResponseDTO.class);
		return responseProduct;
	}

}
