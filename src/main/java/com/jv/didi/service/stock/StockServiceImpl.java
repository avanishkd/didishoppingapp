package com.jv.didi.service.stock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.jv.didi.dao.stock.SellerStockRepository;
import com.jv.didi.dto.seller.response.SellerLoginResponseDTO;
import com.jv.didi.entity.product.Product;
import com.jv.didi.entity.seller.Seller;
import com.jv.didi.entity.stock.SellerStock;
import com.jv.didi.entity.stock.SellerStockItem;
import com.jv.didi.service.product.ProductService;
import com.jv.didi.service.seller.SellerProfileService;

@Service
@Transactional
public class StockServiceImpl implements StockService {

	@Autowired
	StockItemService stockItemService;

	@Autowired
	ProductService productService;

	@Autowired
	SellerStockRepository stockRepository;

	@Autowired
	SellerProfileService sellerProfileService;

	private ModelMapper modelMapper;

	@Override
	public boolean clearItems(long sellerId) {
		// TODO Auto-generated method stub

		stockRepository.deleteBySellerId(sellerId);
		SellerStock deletedSellerStock = stockRepository.findBySellerId(sellerId);
		if (deletedSellerStock == null)
			return true;

		return false;
	}

	@Override
	public SellerStock getOrCreateSellerStock(long sellerId) throws NotFoundException {

		SellerStock stock = stockRepository.findBySellerId(sellerId);
		if (stock != null)
			return stock;
		Collection<SellerStockItem> items = new ArrayList<SellerStockItem>();
		SellerLoginResponseDTO seller = sellerProfileService.getSellerById(sellerId);
		if (seller == null) {
			return null;
		}
		stock = new SellerStock();
		stock.setItems(items);
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setAmbiguityIgnored(true);
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		Seller sellerToSave = mapper.map(seller, Seller.class);
		stock.setSeller(sellerToSave);

		return stockRepository.save(stock);

	}

	@Override
	public int getItemCount(long sellerId) throws NotFoundException {
		int quantity = 0;
		SellerStock stock = getOrCreateSellerStock(sellerId);
		Collection<SellerStockItem> stockItemList = stock.getItems();
		for (SellerStockItem itrSellerStockItem : stockItemList) {
			quantity = quantity + itrSellerStockItem.getQuantity();
		}

		return quantity;

	}

	@Override
	public SellerStock removeItem(long sellerId, Product product) throws NotFoundException {
		SellerStock stock = getOrCreateSellerStock(sellerId);
		SellerStockItem stockItem = null;
		for (SellerStockItem itrstockItem : stock.getItems()) {
			if (itrstockItem.getProduct().getId() == product.getId()) {
				stockItem = itrstockItem;
				break;
			}

		}
		Collection<SellerStockItem> itemList = stock.getItems();
		itemList.remove(stockItem);
//		stock.setItems(itemList);

		return stockRepository.save(stock);

	}

	@Override
	public BigDecimal getTotalSellerStockAmount(long sellerId) throws NotFoundException {
		BigDecimal amount = new BigDecimal("0.0");
		SellerStock stock = getOrCreateSellerStock(sellerId);
		Collection<SellerStockItem> stockItemList = stock.getItems();
		for (SellerStockItem itrSellerStockItem : stockItemList) {
			amount = amount.add(stockItemService.getSubTotal(itrSellerStockItem));
		}
		// TODO Auto-generated method stub
		return amount;
	}

	@Override
	public SellerStock addOneProduct(long sellerId, Product product) throws NotFoundException {

		long productId = product.getId();
		List<Product> productList = new ArrayList<Product>();
		productList.add(product);
		List<Product> productListinDb = fetchProducts(productList);

		if (!productListinDb.stream().map(item -> item.getId()).collect(Collectors.toList())
				.contains(product.getId())) {
			return null;
		}
		SellerStock stock = getOrCreateSellerStock(sellerId);
		Collection<SellerStockItem> dbSellerStockItemList = stock.getItems();
		boolean productAvailable = false;

		for (SellerStockItem itrSellerStockItem : dbSellerStockItemList) {
			if (itrSellerStockItem.getProduct().getId() == product.getId()) {
				itrSellerStockItem.setQuantity(itrSellerStockItem.getQuantity() + 1);
				productAvailable = true;
				break;
			}
		}

		if (!productAvailable) {
			dbSellerStockItemList.add(new SellerStockItem(product, 1));
		}
		stock.setItems(dbSellerStockItemList);

		return stockRepository.save(stock);
	}

	@Override
	public SellerStock addSellerStockItems(long sellerId, Collection<SellerStockItem> stockItems)
			throws NotFoundException {

		List<Long> requestProductIds = stockItems.stream().map(item -> item.getProduct().getId())
				.collect(Collectors.toList());
		List<Product> productListinDb = fetchSellerStockItemProducts(stockItems.stream().collect(Collectors.toList()));

		if (!productListinDb.stream().map(item -> item.getId()).collect(Collectors.toList())
				.containsAll(requestProductIds)) {
			return null;
		}
		boolean productAvailable = false;
		SellerStock stock = getOrCreateSellerStock(sellerId);
		Collection<SellerStockItem> dbSellerStockItemList = stock.getItems();

		for (SellerStockItem itrSellerStockItem : stockItems) {
			productAvailable = false;
			for (SellerStockItem itrDbSellerStockItem : dbSellerStockItemList) {
				if (itrSellerStockItem.getProduct().getId() == itrDbSellerStockItem.getProduct().getId()) {
					itrDbSellerStockItem
							.setQuantity(itrSellerStockItem.getQuantity() + itrDbSellerStockItem.getQuantity());
					productAvailable = true;
					break;
				}
			}
			if (!productAvailable) {
				dbSellerStockItemList.add(itrSellerStockItem);
			}

		}

		stock.setItems(dbSellerStockItemList);
		try {
			return stockRepository.save(stock);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public SellerStock updateItemQuantity(long sellerId, Product product, int quantity) throws NotFoundException {
		// TODO Auto-generated method stub
		SellerStock stock = getOrCreateSellerStock(sellerId);
		Collection<SellerStockItem> stockItemList = stock.getItems();

		for (SellerStockItem itrSellerStockItem : stockItemList) {
			if (itrSellerStockItem.getProduct().getId() == product.getId()) {
				itrSellerStockItem.setQuantity(itrSellerStockItem.getQuantity() + quantity);
				break;
			}
		}
		stock.setItems(stockItemList);

		return stockRepository.save(stock);
	}

	@Override
	public SellerStock removeOneProduct(long sellerId, Product product) throws NotFoundException {

		// TODO Auto-generated method stub
		SellerStock stock = getOrCreateSellerStock(sellerId);
		Collection<SellerStockItem> stockItemList = stock.getItems();

		for (SellerStockItem itrSellerStockItem : stockItemList) {
			if (itrSellerStockItem.getProduct().getId() == product.getId()) {
				if (itrSellerStockItem.getQuantity() == 1) {
					stock.getItems().remove(itrSellerStockItem);
				} else
					itrSellerStockItem.setQuantity(itrSellerStockItem.getQuantity() - 1);
				break;
			}
		}
		stock.setItems(stockItemList);

		return stockRepository.save(stock);
	}

	public List<Product> fetchSellerStockProducts(SellerStock stock) {

		List<Long> requestProductIds = stock.getItems().stream().map(item -> item.getProduct().getId())
				.collect(Collectors.toList());

		List<Product> productList = productService.getAllProductsByIds(requestProductIds);
		return productList;
	}

	public List<Product> fetchSellerStockItemProducts(List<SellerStockItem> stockItemList) {

		List<Long> requestProductIds = stockItemList.stream().map(item -> item.getProduct().getId())
				.collect(Collectors.toList());
		List<Product> productList = productService.getAllProductsByIds(requestProductIds);
		return productList;
	}

	public List<Product> fetchProducts(List<Product> productList) {

		List<Long> requestProductIds = productList.stream().map(item -> item.getId()).collect(Collectors.toList());
		List<Product> responseProductList = productService.getAllProductsByIds(requestProductIds);
		return responseProductList;
	}

}
