package de.telekom.sea7.impl.model;

import java.util.ArrayList;
import java.util.List;

import de.telekom.sea7.impl.BaseObjectImpl;
import de.telekom.sea7.inter.model.Account;
import de.telekom.sea7.inter.model.GenericList;
import de.telekom.sea7.inter.model.Transaction;

public class AccountImpl extends BaseObjectImpl implements Account {

	private String name;
	private String type;
	private String iban;
	private String bic;
	private GenericList<Transaction> transactionList;
	
	public AccountImpl(Object parent, String name, String type, String iban, String bic) {
		super(parent);
		this.name = name;
		this.type = type;
		this.iban = iban;
		this.bic = bic;
		transactionList = new GenericListImpl<Transaction>(this);
	}

	@Override
	public GenericList<Transaction> getTransactionList() {
		return transactionList;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getIban() {
		return iban;
	}

	@Override
	public void setIban(String iban) {
		this.iban = iban;
	}

	@Override
	public String getBic() {
		return bic;
	}

	@Override
	public void setBic(String bic) {
		this.bic = bic;
	}
	
	@Override
	public List<String> getValues() {
		List<String> values = new ArrayList<String>();
		values.add(name);
		values.add(type);
		values.add(iban);
		values.add(bic);

		return values;
	}
	
	@Override
	public List<String> getPropertyNames() {
		List<String> values = new ArrayList<String>();
		values.add("name");
		values.add("type");
		values.add("iban");
		values.add("bic");
		
		return values;
	}
}
