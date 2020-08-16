package com.jv.didi.dto.seller.response;

import com.jv.didi.entity.seller.SellerAddress;

public class SellerRegisterResponse {

	private String name;

	private String password;

	private SellerAddress address;

	private String status;
	
	private Long id;

	public SellerRegisterResponse() {
		super();
	}

	public SellerRegisterResponse(String name, String password, SellerAddress address, String status, Long id) {
		super();
		this.name = name;
		this.password = password;
		this.address = address;
		this.status = status;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}