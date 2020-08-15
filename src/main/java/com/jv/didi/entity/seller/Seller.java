package com.jv.didi.entity.seller;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jv.didi.entity.stock.SellerStock;

@Entity
public class Seller {

	public Seller() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;

	private String name;

	private String password;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "addrId")
	private SellerAddress address;

	private String status;

	@JsonManagedReference
	@OneToOne(mappedBy = "seller", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)

	private SellerStock sellerStock;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SellerAddress getAddress() {
		return address;
	}

	public void setAddress(SellerAddress address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Seller(@NotNull(message = "seller id is required.") Long id, String name, String password,
			SellerAddress address, String status, SellerStock sellerStock) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.address = address;
		this.status = status;
		this.sellerStock = sellerStock;
	}

	public SellerStock getSellerStock() {
		return sellerStock;
	}

	public void setSellerStock(SellerStock sellerStock) {
		this.sellerStock = sellerStock;
	}

}
