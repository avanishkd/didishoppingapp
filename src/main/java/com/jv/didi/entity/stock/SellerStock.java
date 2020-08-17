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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jv.didi.entity.seller.Seller;

@Entity
@JsonIgnoreProperties({ "seller" })
public class SellerStock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STOCK_ID")
	private Long id;

	@ElementCollection // @OneToMany for basic and embeddables
	@CollectionTable(name = "SellerStockItem") // defaults to "Stock_items" if not overridden
	Collection<SellerStockItem> items;

	@JsonBackReference
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "SELLER_ID", nullable = false, updatable = false)
	@JsonIgnore
	private Seller seller;

	public SellerStock() {
		super();
	}

	public SellerStock(Long id, Collection<SellerStockItem> items, Seller seller) {
		super();
		this.id = id;
		this.items = items;
		this.seller = seller;
	}

	@Transient
	public BigDecimal getTotalStockAmount() {
		BigDecimal amount = new BigDecimal("0.0");

		Collection<SellerStockItem> stockItemList = this.getItems();
		for (SellerStockItem itrStockItem : stockItemList) {
			amount = amount.add(itrStockItem.getSubTotal());
		}
		// TODO Auto-generated method stub
		return amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<SellerStockItem> getItems() {
		return items;
	}

	public void setItems(Collection<SellerStockItem> items) {
		this.items = items;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

}