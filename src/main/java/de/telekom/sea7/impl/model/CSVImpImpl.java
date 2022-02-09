package de.telekom.sea7.impl.model;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import de.telekom.sea7.inter.model.CSVImp;

public class CSVImpImpl implements Iterable<Map<String, String>>, CSVImp{

	private String fileName;
	private List<Map<String, String>> values;
	private List<String> header;
	
	public CSVImpImpl(String fileName) {
		this.fileName = fileName;
		this.values = new ArrayList<Map<String, String>>();
		this.header = new ArrayList<String>();
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public List<Map<String, String>> getValues() {
		return values;
	}

	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void setValues(List<Map<String, String>> values) {
		this.values = values;
	}
	
	@Override
	public List<Map<String, String>> importCsv(String fileName) throws Exception {
		try (Reader in = new FileReader(fileName)) {
			CSVFormat format = CSVFormat.Builder.create().setSkipHeaderRecord(true).build();
			try (CSVParser parser = new CSVParser(in, format)) {
				boolean headerUsed = false;
				for (CSVRecord record : parser) {
//					if (!headerUsed) {
//						for (String str : record) {
//							header.add(str);
//						}
//						headerUsed = true;
//					}
//					else {
//						HashMap<String, String> csvEntry = new HashMap<String, String>();
//						for (String str : header) {
//							csvEntry.put(str, record.get(str).toString())
//						}
//						values.add(csvEntry);
					
					values.add(record.toMap());
				}
			
			}
		}
		return values;
	}

	@Override
	public Iterator<Map<String, String>> iterator() {
		return values.iterator();
	}
}
