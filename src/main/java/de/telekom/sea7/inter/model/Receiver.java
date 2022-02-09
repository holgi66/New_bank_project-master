package de.telekom.sea7.inter.model;

public interface Receiver {

	int getId();

	String getCountry();

	int getZipcode();

	String getCity();

	String getStreet();

	String getName();

	void setCountry(String country);

	void setZipcode(int zipcode);

	void setCity(String city);

	void setStreet(String street);

	void setName(String name);

}