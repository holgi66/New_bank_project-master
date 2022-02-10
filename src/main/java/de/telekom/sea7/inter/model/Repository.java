package de.telekom.sea7.inter.model;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {

	List<T> getAll() throws SQLException;

	T get(int index) throws SQLException;

	void add(T t);
	
	void update(T t);

	void remove(int index);

}