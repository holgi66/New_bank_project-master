package de.telekom.sea7.inter.model;

public interface Iban {

	int getId();

	Bic getBic();

	void setBic(Bic bic);

	void setId(int id);

	String getIban();

	void setIban(String iban);

}