package com.jv.didi.dto.seller;

public class SellerLoginDTO {
	private Long id;

	private String password;

	public SellerLoginDTO() {
		super();
	}

	public SellerLoginDTO(Long id, String password) {
		super();
		this.id = id;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}