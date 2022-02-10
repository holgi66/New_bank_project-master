package de.telekom.sea7.impl.model;

import de.telekom.sea7.impl.BaseObjectImpl;
import de.telekom.sea7.inter.model.Receiver;

public class ReceiverImpl extends BaseObjectImpl implements Receiver {
	
	private int id;
	private String country;
	private int zipcode;
	private String city;
	private String street;
	private String name;
	
	public ReceiverImpl(Object parent, String name) {
		super(parent);
//		this.id = id;
//		this.country = country;
//		this.zipcode = zipcode;
//		this.city = city;
//		this.street = street;
		this.name = name;
	}

	@Override
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getCountry() {
		return country;
	}

	@Override
	public int getZipcode() {
		return zipcode;
	}

	@Override
	public String getCity() {
		return city;
	}

	@Override
	public String getStreet() {
		return street;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	@Override
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public void setStreet(String street) {
		this.street = street;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

}
