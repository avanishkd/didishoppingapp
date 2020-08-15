package com.jv.didi.entity.stock;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.jv.didi.entity.product.Product;

@Embeddable
public class SellerStockItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;
	private int quantity;

	public SellerStockItem(Product product, int quantity) {

		this.product = product;
		this.quantity = quantity;
	}

	public SellerStockItem() {

	}

	public int getQuantity() {
		return quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Transient
	public BigDecimal getSubTotal() {
		return this.getProduct().getPrice().multiply(new BigDecimal(this.getQuantity()));

	}

}
