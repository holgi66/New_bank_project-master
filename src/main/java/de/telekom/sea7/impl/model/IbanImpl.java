package de.telekom.sea7.impl.model;

import de.telekom.sea7.impl.BaseObjectImpl;
import de.telekom.sea7.inter.model.Bic;
import de.telekom.sea7.inter.model.Iban;
import javax.persistence.*;


public class IbanImpl extends BaseObjectImpl implements Iban {
	
	private int id;
	private String iban;
	
	@ManyToOne
	private Bic bic;
	
	public IbanImpl(Object parent, String iban, Bic bic) {
		super(parent);
		this.iban = iban;
		this.bic = bic;
	}

	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public Bic getBic() {
		return bic;
	}

	@Override
	public void setBic(Bic bic) {
		this.bic = bic;
	}

	@Override 
	public void setId(int id) {
		this.id = id;
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
