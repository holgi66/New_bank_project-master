package de.telekom.sea7.inter.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface CSVImp extends Iterable<Map<String, String>>{

	String getFileName();

	List<Map<String, String>> getValues();

	void setFileName(String fileName);

	void setValues(List<Map<String, String>> values);

	List<Map<String, String>> importCsv(String fileName) throws Exception;

	Iterator<Map<String, String>> iterator();

}