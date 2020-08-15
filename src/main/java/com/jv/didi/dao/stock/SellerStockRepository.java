package com.jv.didi.dao.stock;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jv.didi.entity.stock.SellerStock;

@Repository
public interface SellerStockRepository extends CrudRepository<SellerStock, Long> {

	public SellerStock findBySellerId(long id);

	@Modifying
	@Transactional
	public void deleteBySellerId(long id);

}
