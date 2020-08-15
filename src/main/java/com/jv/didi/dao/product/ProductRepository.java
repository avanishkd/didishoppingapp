package com.jv.didi.dao.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jv.didi.entity.product.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
