package de.telekom.sea7.impl.view;

import java.io.FileReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import de.telekom.sea7.impl.BaseObjectImpl;
import de.telekom.sea7.impl.model.CSVImp;
import de.telekom.sea7.impl.model.CSVImpImpl;
import de.telekom.sea7.impl.model.TransactionImpl;
import de.telekom.sea7.inter.model.GenericList;
import de.telekom.sea7.inter.model.Transaction;
import de.telekom.sea7.inter.view.TransactionListView;
import de.telekom.sea7.inter.view.TransactionView;

public class TransactionListViewImpl extends BaseObjectImpl implements TransactionListView {
	
	private GenericList<Transaction> transactionList;
	private Scanner scanner;
	
	public TransactionListViewImpl(Object parent, Scanner scanner, GenericList<Transaction> transactionList) {
		super(parent);
		this.transactionList = transactionList;
		this.scanner = scanner;
	}
	
	@Override
	public void add() {
		System.out.println("Enter receiver: ");
		String receiver = this.scanner.nextLine();
		
		System.out.println("Enter IBAN: ");
		String iban = this.scanner.nextLine();
		
		System.out.println("Enter BIC: ");
		String bic = this.scanner.nextLine();
		
		System.out.println("Enter purpose: ");
		String purpose = this.scanner.nextLine();
		
		System.out.println("Enter amount: ");
		while(!this.scanner.hasNextFloat()) {
			System.out.println("Your entered value");
			this.scanner.next();
		}
		Float amount = this.scanner.nextFloat();
		this.scanner.nextLine();
		
		LocalDateTime date = LocalDateTime.now();
		Transaction transaction = new TransactionImpl(this, amount, receiver, iban, bic, purpose, date);
		transactionList.add(transaction);
		//scanner.close();
	}

	@Override
	public void remove() {
		System.out.println("Enter position number of transaction: ");
		int position = this.scanner.nextInt();
		transactionList.remove(position);
		//scanner.close();
	}
	
	//show
	@Override
	public void show() {
		if (transactionList.size() > 0) { 
			for (Object o : transactionList) {
				Transaction transaction = (Transaction)o;
				TransactionView transactionView = new TransactionViewImpl(this, this.scanner, transaction);
				System.out.println("Position: " + transactionList.getIndex(transaction));
				transactionView.show();
				System.out.println();
			}
		}
		else {
			System.out.println("No entries");
		}
		//scanner.close();
	}
	
	//showOne
	@Override
	public void showOne() {
		System.out.println("Enter position number of transaction: ");
		int index = this.scanner.nextInt();
		
		if (index < 0 || index > transactionList.size() - 1) {
			System.out.println("Specified entry does not belong to a transaction.");
		}
		else {
			TransactionView transactionView = new TransactionViewImpl(this, this.scanner, transactionList.getOneObject(index));
			transactionView.menu();
		}
		//scanner.close();
	}
	
	public void getBalance() {
		float amount = 0.00f;
		for (Object o : transactionList) {
			Transaction transaction = (Transaction)o;
			amount = amount + transaction.getAmount();
		}
		
		System.out.println("Your Balance is: " + String.format("%.2f", amount) + " €");
	}
	
	public void exportCsv() {
	try {
		System.out.println("Enter Filename: ");
		String fileName = this.scanner.nextLine();
		if (!fileName.endsWith(".csv")) {
			fileName = fileName + ".csv";
		}
		transactionList.exportCsv(fileName);
	}
	catch(Exception e) {
		System.out.println(e.getMessage());
	}
}
	
//	public void importCsv() {
//		try {
//			System.out.println("Enter Filename: ");
//			String fileName = this.scanner.nextLine();
//			if (!fileName.endsWith(".csv")) {
//				fileName = fileName + ".csv";
//			}
//			try (Reader in = new FileReader(fileName)) {
//				CSVFormat format = CSVFormat.Builder.create().setSkipHeaderRecord(true).setHeader("amount","receiver","iban","bic","purpose","date").build();
//				try (CSVParser parser = new CSVParser(in, format)) {
//					for (CSVRecord record : parser) {
//						float amount = Float.parseFloat(record.get("amount"));
//						String receiver = record.get("receiver");
//						String iban = record.get("iban");
//						String bic = record.get("bic");
//						String purpose = record.get("purpose");
//						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
//						LocalDateTime date = LocalDateTime.parse(record.get("date"), formatter);
//						Transaction transaction = new TransactionImpl(this,amount,receiver,iban,bic,purpose,date);
//						transactionList.add(transaction);
//					}
//				}
//			}
//		}
//		catch(Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}

	public void importCsv() {
		try {
			System.out.println("Enter Filename: ");
			String fileName = this.scanner.nextLine();
			if (!fileName.endsWith(".csv")) {
				fileName = fileName + ".csv";
			}
			CSVImp csvImport = new CSVImpImpl(fileName);
			
			
			for (Map<String, String> map : csvImport.importCsv(fileName)) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
				LocalDateTime date = LocalDateTime.parse(map.get("date"), formatter);
				Transaction transaction = new TransactionImpl(this,Float.parseFloat(map.get("amount")),
																	map.get("receiver"),
																	map.get("iban"),
																	map.get("bic"),
																	map.get("purpose"),date);
			}
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void search() {
		System.out.println("Search: ");
		String search = this.scanner.nextLine();
		GenericList<Transaction> foundObjects = transactionList.search(search);
		TransactionListView foundObjectsView = new TransactionListViewImpl(this, this.scanner, foundObjects);
		foundObjectsView.menu();
	}
	
	//menu
	@Override
	public void menu() {
		String input = "";
		while (!input.equals("exit")) {
			System.out.println("Enter show, showOne, balance, add, remove, import, export or exit to navigate.");
			System.out.println("Enter something:");
			input = this.scanner.next();
			this.scanner.nextLine();
			switch (input) {
				case "show":
					show();
					break;
				case "showOne":
					showOne();
					break;
				case "balance":
					getBalance();
					break;
				case "add":
					add();
					break;
				case "remove":
					remove();
					break;
				case "export":
					exportCsv();
					break;
				case "import":
					importCsv();
					break;
				case "search":
					search();
					break;
				case "exit":
					break;
				default:
					System.out.println("Command unknown");
			}
		}
		//menuScanner.close();
	}
}
