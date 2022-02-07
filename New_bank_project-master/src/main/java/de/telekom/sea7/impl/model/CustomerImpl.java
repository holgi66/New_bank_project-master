package de.telekom.sea7.impl.model;

import java.time.LocalDate;

import de.telekom.sea7.impl.BaseObjectImpl;
import de.telekom.sea7.inter.model.Account;
import de.telekom.sea7.inter.model.GenericList;

public class CustomerImpl extends BaseObjectImpl {
	
	private String name;
	private String surname;
	private LocalDate birthday;
	private String street;
	private int streetNumber;
	private int postalCode;
	private String city;
	private String username;
	private GenericList<Account> accountList;
	
	public CustomerImpl(Object parent, String name, String surname, LocalDate birthday, String street, int streetNumber, int postalCode, String city, String username) {
		super(parent);
		this.name = name;
		this.surname = surname;
		this.birthday = birthday;
		this.street = street;
		this.streetNumber = streetNumber;
		this.postalCode = postalCode;
		this.city = city;
		this.username = username;
		this.accountList = new GenericListImpl<Account>(this);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(int streetNumber) {
		this.streetNumber = streetNumber;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public GenericList<Account> getAccountList() {
		return accountList;
	}
}
