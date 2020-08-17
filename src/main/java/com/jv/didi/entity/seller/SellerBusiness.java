package com.jv.didi.entity.seller;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SellerBusiness {

	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long businessId;

	private String businessName;

	private String businessWebsiteURL;

	public SellerBusiness() {
		super();
	}

	public SellerBusiness(Long businessId, String businessName, String businessWebsiteURL) {
		super();
		this.businessId = businessId;
		this.businessName = businessName;
		this.businessWebsiteURL = businessWebsiteURL;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessWebsiteURL() {
		return businessWebsiteURL;
	}

	public void setBusinessWebsiteURL(String businessWebsiteURL) {
		this.businessWebsiteURL = businessWebsiteURL;
	}
}
