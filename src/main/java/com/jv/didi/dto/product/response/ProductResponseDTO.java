package com.jv.didi.dto.product.response;

import java.math.BigDecimal;

public class ProductResponseDTO {

	private Long id;

	private String name;

	private BigDecimal price;

	private String description;

	private String status;

	public ProductResponseDTO() {
		super();
	}

	public ProductResponseDTO(Long id, String name, BigDecimal price, String description, String status) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.status = status;
	}

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
