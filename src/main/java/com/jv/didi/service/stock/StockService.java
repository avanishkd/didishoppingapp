package com.jv.didi.service.stock;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.jv.didi.entity.product.Product;
import com.jv.didi.entity.stock.SellerStock;
import com.jv.didi.entity.stock.SellerStockItem;

public interface StockService {

	public SellerStock removeItem(long userId, Product product) throws NotFoundException;

	public int getItemCount(long userId) throws NotFoundException;

	public BigDecimal getTotalSellerStockAmount(long userId) throws NotFoundException;

	public SellerStock getOrCreateSellerStock(long userId) throws NotFoundException;

	public boolean clearItems(long userId);

	public SellerStock addOneProduct(long userId, Product product) throws NotFoundException;

	public SellerStock removeOneProduct(long userId, Product product) throws NotFoundException;

	public SellerStock addSellerStockItems(long userId, Collection<SellerStockItem> stockItems)
			throws NotFoundException;

	public SellerStock updateItemQuantity(long userId, Product product, int quantity) throws NotFoundException;

}