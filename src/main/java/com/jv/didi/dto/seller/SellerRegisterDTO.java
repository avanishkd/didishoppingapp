package com.jv.didi.dto.seller;

import com.jv.didi.entity.seller.SellerAddress;

public class SellerRegisterDTO {

	private String name;

	private String password;

	private SellerAddress address;

	public SellerRegisterDTO() {
		super();
	}

	public SellerRegisterDTO(String name, String password, SellerAddress address) {
		super();
		this.name = name;
		this.password = password;
		this.address = address;

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

}
