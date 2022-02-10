package de.telekom.sea7.inter.model;

import java.time.LocalDateTime;

public interface Transaction {

	int getId();
	
	void setId(int id);
	
	float getAmount();

	void setAmount(float amount);

	Receiver getReceiver();

	void setReceiver(Receiver receiver);

	Iban getIban();

	void setIban(Iban iban);

	String getPurpose();

	void setPurpose(String purpose);

	LocalDateTime getDate();

	void setDate(LocalDateTime date);

}