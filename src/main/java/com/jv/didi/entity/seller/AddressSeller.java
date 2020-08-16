package com.jv.didi.entity.seller;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AddressSeller {
	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int addrId;

	private String street;

	private String city;

	private String state;

	private String country;

	private String pin;

	public AddressSeller() {

	}

	public AddressSeller(int addrId, String street, String city, String state, String country, String pin) {
		super();
		this.addrId = addrId;
		this.street = street;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pin = pin;

	}

	public int getAddrId() {
		return addrId;
	}

	public void setAddrId(int addrId) {
		this.addrId = addrId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

}
