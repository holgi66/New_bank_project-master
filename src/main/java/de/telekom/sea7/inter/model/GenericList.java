package de.telekom.sea7.inter.model;

import java.util.Iterator;
import java.util.List;

public interface GenericList<T> extends Iterable<T> {

	T getOneObject(int index) throws IndexOutOfBoundsException;

	void add(T tObject);

	void remove(int index) throws IndexOutOfBoundsException;

	void remove(T tObject);

	int size();

	int getIndex(T tObject);

	Iterator<T> iterator();

	boolean checkIndex(int index);
	
	List<T> getGenericList();
	
	void compare(String input, T tObject, GenericList<T> foundTransactionList);
	
	GenericList<T> search(String input);
	
	void exportCsv(String fileName);
	
}