package de.telekom.sea7.impl.model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import de.telekom.sea7.impl.BaseObject;
import de.telekom.sea7.impl.BaseObjectImpl;
import de.telekom.sea7.inter.model.GenericList;

public class GenericListImpl<T> extends BaseObjectImpl implements Iterable<T>, GenericList<T> {
	
	private List<T> genericList;
	
	public GenericListImpl(Object parent) {
		super(parent);
		genericList = new ArrayList<T>();
	}
	
	
	public List<T> getGenericList() {
		return genericList;
	}


	@Override
	public T getOneObject(int index) throws IndexOutOfBoundsException {
		if (checkIndex(index)) {
			return genericList.get(index);
		}
		else {
			throw new IndexOutOfBoundsException();
		}
	}
	
	@Override
	public void add(T tObject) {
		this.genericList.add(tObject);
	}
	
	@Override
	public void remove(int index) throws IndexOutOfBoundsException {
		if (checkIndex(index)) {
			this.genericList.remove(index);
		}
		else {
			throw new IndexOutOfBoundsException();
		}
	}
	
	@Override
	public void remove(T tObject) {
		this.genericList.remove(tObject);
	}
	
	@Override
	public int size() {
		return this.genericList.size();
	}
	
	@Override
	public int getIndex(T tObject) {
		return this.genericList.indexOf(tObject);
	}

	@Override
	public Iterator<T> iterator() {
		return genericList.iterator();
	}
	
	@Override
	public boolean checkIndex(int index) {
		return index >= 0 && index < genericList.size();
	}
	
	@Override
	public GenericList<T> search(String input) {
		GenericList<T> foundTransactionList = new GenericListImpl<T>(this);
		for (T e : genericList) {
			compare(input, e, foundTransactionList);
		}
		return foundTransactionList;
	}
	
	@Override
	public void compare(String input, T tObject, GenericList<T> foundTransactionList) {
		if (tObject instanceof BaseObject) {
			BaseObject baseobject = (BaseObject)tObject;
		
			for (String str : baseobject.getValues()) {
				if (str.contains(input)) {
					foundTransactionList.add(tObject);
					return;
				}
			}
		}
	}
	
	public void exportCsv(String fileName) {
		try {
			try (Writer out = new FileWriter(fileName)) {
				CSVFormat format = CSVFormat.Builder.create().build();
				try (CSVPrinter printer = new CSVPrinter(out, format)) {
					boolean headerCreated = false;
					for (T tObject : genericList) {
						if (tObject instanceof BaseObject) {
							BaseObject baseobject = (BaseObject)tObject;
							if (!headerCreated) {
								printer.printRecord(baseobject.getPropertyNames());	
								headerCreated = true;
							}
							printer.printRecord(baseobject.getValues());
						}
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	// exportCsv with reflections
//	public void exportCsv(String fileName) {
//		try {
//			try (Writer out = new FileWriter(fileName)) {
//				CSVFormat format = CSVFormat.Builder.create().build();
//				try (CSVPrinter printer = new CSVPrinter(out, format)) {
//					boolean headerCreated = false;
//					for (T tObject : genericList) {
//						if (!headerCreated) {
//							List<String> header = new ArrayList<String>();
//							for (Field f : tObject.getClass().getDeclaredFields()) {
//								header.add(f.getName());
//							}
//							printer.printRecord(header);
//						}
//						
//						Method mGetValues = tObject.getClass().getMethod("getValues");
//						List<String> test = (List<String>) mGetValues.invoke(tObject);
//						
//						List<String> values = new ArrayList<String>();
//						for (String str : test) {
//							values.add(str);
//						}
//						printer.printRecord(values);
//					}
//				}
//			}
//		}
//		catch(Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
	

}
