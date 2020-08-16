package com.jv.didi.service.stock;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.jv.didi.entity.stock.SellerStockItem;

@Service
public class StockItemServiceImpl implements StockItemService {

	@Override
	public BigDecimal getSubTotal(SellerStockItem sellerStockItem) {
		// TODO Auto-generated method stub
		return sellerStockItem.getProduct().getPrice().multiply(new BigDecimal(sellerStockItem.getQuantity()));
	}

}
