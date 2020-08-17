package com.jv.didi.controller.stock;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jv.didi.entity.product.Product;
import com.jv.didi.entity.stock.SellerStock;
import com.jv.didi.entity.stock.SellerStockItem;
import com.jv.didi.service.stock.StockService;

@RestController
public class StockController {

	/*
	 * @Autowired UserServiceProxy productServiceProxy;
	 */

	private Logger logger = LogManager.getLogger(StockController.class);

	@Autowired
	StockService stockService;

	@PostMapping("/addSellerStockItems/{userId}")
	public SellerStock addSellerStockItems(@PathVariable long userId,@RequestBody Collection<SellerStockItem> items) {

		try {
			return stockService.addSellerStockItems(userId, items);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@DeleteMapping("/clearItems/{userId}")
	public void clearItems(@PathVariable long userId) {

		stockService.clearItems(userId);
	}

	@PutMapping("/getOrCreateSellerStock/{userId}")
	public SellerStock getOrCreateSellerStock(@PathVariable long userId) {

		try {
			return stockService.getOrCreateSellerStock(userId);
		} catch (NotFoundException e) {
			return null;
		}
	}

	@GetMapping("/getItemCount/{userId}")
	public int getItemCount(@PathVariable long userId) {

		try {
			return stockService.getItemCount(userId);
		} catch (NotFoundException e) {

			e.printStackTrace();
			return 0;
		}
	}

	@DeleteMapping("/removeItem/{userId}")
	public SellerStock removeItem(@PathVariable long userId, @RequestBody Product product) {

		try {
			return stockService.removeItem(userId, product);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping("/getTotalSellerStockAmount/{userId}")
	public BigDecimal getTotalSellerStockAmount(@PathVariable long userId) {

		try {
			return stockService.getTotalSellerStockAmount(userId);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping("/addOneProduct/{userId}")
	public SellerStock addOneProduct(@PathVariable long userId, @RequestBody Product product) {

		try {
			return stockService.addOneProduct(userId, product);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@DeleteMapping("/removeOneProduct/{userId}")
	public SellerStock removeOneProduct(@PathVariable long userId, @RequestBody Product product) {

		try {
			return stockService.removeOneProduct(userId, product);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@PostMapping("/addSellerStockItem/{userId}")
	public SellerStock addSellerStockItem(@PathVariable long userId, @RequestBody SellerStock stock) {

		try {
			return stockService.addSellerStockItems(userId, stock.getItems());
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@PostMapping("/updateItemQuantity/{userId}")
	public SellerStock updateItemQuantity(@PathVariable long userId, @RequestBody SellerStockItem stockItem) {

		try {
			return stockService.updateItemQuantity(userId, stockItem.getProduct(), stockItem.getQuantity());
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
