package de.telekom.sea7.impl.model;


import de.telekom.sea7.impl.BaseObjectImpl;
import de.telekom.sea7.inter.model.Bic;

public class BicImpl extends BaseObjectImpl implements Bic {

	private int id;
	private String bic;
	private String institute;
	
	public BicImpl(Object parent, String bic, String institute) {
		super(parent);
		this.bic = bic;
		this.institute = institute;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getInstitute() {
		return institute;
	}

	@Override
	public void setInstitute(String institute) {
		this.institute = institute;
	}

	@Override
	public String getBic() {
		return bic;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void setBic(String bic) {
		this.bic = bic;
	}
	
}
