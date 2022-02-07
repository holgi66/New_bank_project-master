package de.telekom.sea7.impl;

import java.util.ArrayList;
import java.util.List;

public class BaseObjectImpl implements BaseObject {
	private int id;
	private Object parent;
	private static int count;
	
	public BaseObjectImpl(Object parent) {
		this.id = count++;
		this.parent = parent;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public Object getParent() {
		return parent;
	}
	
	@Override
	public List<String> getPropertyNames() {
		List<String> values = new ArrayList<String>();
		return values;
	}
	
	@Override
	public List<String> getValues() {
		List<String> values = new ArrayList<String>();
		return values;
	}
}
