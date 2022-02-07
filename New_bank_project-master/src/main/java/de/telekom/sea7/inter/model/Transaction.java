package de.telekom.sea7.inter.model;

import java.time.LocalDateTime;
import java.util.List;

public interface Transaction {

	float getAmount();

	void setAmount(float amount);

	String getReceiver();

	void setReceiver(String receiver);

	String getIban();

	void setIban(String iban);

	String getBic();

	void setBic(String bic);

	String getPurpose();

	void setPurpose(String purpose);

	LocalDateTime getDate();

	void setDate(LocalDateTime date);
	
	List<String> getValues();
}