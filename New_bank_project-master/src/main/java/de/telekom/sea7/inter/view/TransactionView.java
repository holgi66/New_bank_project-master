package de.telekom.sea7.inter.view;

public interface TransactionView {

	void menu();

	void editMenu();

	void show();

	void setReceiver();

	void setIban();

	void setBic();

	void setPurpose();

	void setAmount();

}