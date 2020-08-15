package com.jv.didi.entity.stock;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jv.didi.entity.seller.Seller;

@Entity
public class SellerStock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "STOCK_ID")
	private long id;

	@ElementCollection // @OneToMany for basic and embeddables
	@CollectionTable(name = "SellerStockItem") // defaults to "Cart_items" if not overridden
	Collection<SellerStockItem> items;

	@JsonBackReference
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "SELLER_ID", nullable = false, updatable = false)
	@JsonIgnore
	private Seller seller;

	public SellerStock() {

	}

	public SellerStock(long id) {

		this.id = id;
	}

	public long getId() {
		return id;
	}

	public Collection<SellerStockItem> getItems() {
		return items;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setItems(Collection<SellerStockItem> items) {
		this.items = items;
	}

	@Transient
	public BigDecimal getTotalCartAmount() {
		BigDecimal amount = new BigDecimal("0.0");

		Collection<SellerStockItem> cartItemList = this.getItems();
		for (SellerStockItem itrCartItem : cartItemList) {
			amount = amount.add(itrCartItem.getSubTotal());
		}
		// TODO Auto-generated method stub
		return amount;
	}

	public SellerStock(long id, Collection<SellerStockItem> items, Seller seller) {
		super();
		this.id = id;
		this.items = items;
		this.seller = seller;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public void setId(long id) {
		this.id = id;
	}

}