package com.jv.didi.service.stock;

import java.math.BigDecimal;

import com.jv.didi.entity.stock.SellerStockItem;

public interface StockItemService {
	public BigDecimal getSubTotal(SellerStockItem sellerStockItem);

}
