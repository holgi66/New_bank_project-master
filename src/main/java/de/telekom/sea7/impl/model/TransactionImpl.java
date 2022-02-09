package de.telekom.sea7.impl.model;

import java.time.LocalDateTime;

import de.telekom.sea7.impl.BaseObjectImpl;
import de.telekom.sea7.inter.model.Iban;
import de.telekom.sea7.inter.model.Receiver;
import de.telekom.sea7.inter.model.Transaction;


public class TransactionImpl extends BaseObjectImpl implements Transaction  {
	
	private float amount;
	private int id;
	private Receiver receiver; 
	private Iban iban;
	private String bic;
	private String purpose;
	private LocalDateTime date;
	
	public TransactionImpl(Object parent, int id, float amount, Receiver receiver, Iban iban, String purpose, LocalDateTime date) {
		super(parent);
		this.id = id;
		this.amount = amount;
		this.receiver = receiver;
		this.iban = iban;
		this.purpose = purpose;
		this.date = date;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public float getAmount() {
		return amount;
	}

	@Override
	public void setAmount(float amount) {
		this.amount = amount;
	}

	@Override
	public Receiver getReceiver() {
		return receiver;
	}

	@Override
	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	@Override
	public Iban getIban() {
		return iban;
	}

	@Override
	public void setIban(Iban iban) {
		this.iban = iban;
	}

	@Override
	public String getPurpose() {
		return purpose;
	}

	@Override
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	@Override
	public LocalDateTime getDate() {
		return date;
	}

	@Override
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	/*
	@Override
	public List<String> getValues() {
		List<String> values = new ArrayList<String>();
		values.add(Float.toString(amount));
		values.add(receiver);
		values.add(iban);
		values.add(bic);
		values.add(purpose);
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
		String dateString = date.format(dateTimeFormatter);
		values.add(dateString);
		
		return values;
	}
	
	@Override
	public List<String> getPropertyNames() {
		List<String> values = new ArrayList<String>();
		values.add("amount");
		values.add("receiver");
		values.add("iban");
		values.add("bic");
		values.add("purpose");
		values.add("date");
		
		return values;
	}
	*/
}
