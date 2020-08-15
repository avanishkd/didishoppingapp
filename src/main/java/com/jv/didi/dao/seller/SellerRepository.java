package com.jv.didi.dao.seller;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jv.didi.entity.seller.Seller;

@Repository
public interface SellerRepository extends CrudRepository<Seller, Long> {
	Seller findByIdAndPassword(Long id, String password);
	

}