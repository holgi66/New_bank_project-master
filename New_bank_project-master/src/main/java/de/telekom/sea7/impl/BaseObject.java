package de.telekom.sea7.impl;

import java.util.List;

public interface BaseObject {

	int getId();

	Object getParent();
	
	List<String> getValues();

	List<String> getPropertyNames();
}