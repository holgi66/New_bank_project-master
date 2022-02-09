package de.telekom.sea7.impl.model;

import de.telekom.sea7.impl.BaseObjectImpl;
import de.telekom.sea7.inter.model.Iban;

public class IbanImpl extends BaseObjectImpl implements Iban {
	
	private int id;
	private String iban;
	
	public IbanImpl(Object parent, int id, String iban) {
		super(parent);
		this.id = id;
		this.iban = iban;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getIban() {
		return iban;
	}

	@Override
	public void setIban(String iban) {
		this.iban = iban;
	}

}
