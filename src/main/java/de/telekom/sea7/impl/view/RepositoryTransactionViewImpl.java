package de.telekom.sea7.impl.view;

import java.io.FileReader;
import java.io.Reader;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import de.telekom.sea7.impl.BaseObjectImpl;
import de.telekom.sea7.impl.model.BicImpl;
import de.telekom.sea7.impl.model.CSVImpImpl;
import de.telekom.sea7.impl.model.IbanImpl;
import de.telekom.sea7.impl.model.ReceiverImpl;
import de.telekom.sea7.impl.model.RepositoryBicImpl;
import de.telekom.sea7.impl.model.RepositoryIbanImpl;
import de.telekom.sea7.impl.model.RepositoryReceiverImpl;
import de.telekom.sea7.impl.model.TransactionImpl;
import de.telekom.sea7.inter.model.Bic;
import de.telekom.sea7.inter.model.CSVImp;
import de.telekom.sea7.inter.model.Iban;
import de.telekom.sea7.inter.model.Receiver;
import de.telekom.sea7.inter.model.Repository;
import de.telekom.sea7.inter.model.Transaction;
import de.telekom.sea7.inter.view.RepositoryTransactionView;
import de.telekom.sea7.inter.view.TransactionView;

public class RepositoryTransactionViewImpl extends BaseObjectImpl implements RepositoryTransactionView {

	private Repository<Transaction> transactionRepo;
	private Scanner scanner;

	public RepositoryTransactionViewImpl(Object parent, Scanner scanner, Repository<Transaction> transactionRepo) {
		super(parent);
		this.transactionRepo = transactionRepo;
		this.scanner = scanner;
	}

	// menu
	@Override
	public void menu() {
		String input = "";
		while (!input.equals("exit")) {
			System.out.println("Enter show, showAll, remove, add or exit to navigate.");
			System.out.println("Enter something:");
			input = this.scanner.next();
			this.scanner.nextLine();
			switch (input) {
			case "show":
				show();
				break;
			case "showAll":
				showAll();
				break;
//				case "balance":
//					getBalance();
//					break;
			case "add":
				add();
				break;
			case "remove":
				remove();
				break;
			case "update":
				update();
				break;
//				case "export":
//					exportCsv();
//					break;
//				case "import":
//					importCsv();
//					break;
//				case "search":
//					search();
//					break;
			case "exit":
				break;
			default:
				System.out.println("Command unknown");
			}
		}
	}

	public void update() {
		System.out.println("Enter position number of transaction you want to update: ");
		int id = this.scanner.nextInt();
		this.scanner.nextLine();

		System.out.println("Enter receiver name: ");
		String name = this.scanner.nextLine();

		System.out.println("Enter IBAN: ");
		String iban = this.scanner.nextLine();

		System.out.println("Enter BIC: ");
		String bic = this.scanner.nextLine();

		System.out.println("Enter institute: ");
		String institute = this.scanner.nextLine();

		System.out.println("Enter purpose: ");
		String purpose = this.scanner.nextLine();

		System.out.println("Enter amount: ");
		while (!this.scanner.hasNextFloat()) {
			System.out.println("Your entered value");
			this.scanner.next();
		}
		Float amount = this.scanner.nextFloat();
		this.scanner.nextLine();

		try {
			Transaction transaction = transactionRepo.get(id);
			transaction.setAmount(amount);
			transaction.setPurpose(purpose);

			Repository<Receiver> receiverRepo = new RepositoryReceiverImpl(this);
			Receiver receiverObject = receiverRepo.get(transaction.getReceiver().getId());
			receiverObject.setName(name);

			Repository<Iban> ibanRepo = new RepositoryIbanImpl(this);
			Iban ibanObject = ibanRepo.get(transaction.getIban().getId());
			ibanObject.setIban(iban);

			Repository<Bic> bicRepo = new RepositoryBicImpl(this);
			Bic bicObject = bicRepo.get(ibanObject.getBic().getId());
			bicObject.setBic(bic);
			bicObject.setInstitute(institute);
			transactionRepo.update(transaction);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void remove() {
		System.out.println("Enter position number of transaction: ");
		int index = this.scanner.nextInt();

		transactionRepo.remove(index);
	}

	// show
	@Override
	public void showAll() {
		try {
			List<Transaction> transactionList = transactionRepo.getAll();
			for (Transaction transaction : transactionList) {
				System.out.println("ID: " + transaction.getId());
				System.out.println("Receiver: " + transaction.getReceiver().getName());
				System.out.println("IBAN: " + transaction.getIban().getIban());
				System.out.println("Amount: " + transaction.getAmount());
				System.out.println("Purpose: " + transaction.getPurpose());
				System.out.println("Date: " + transaction.getDate());
				System.out.println();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// showOne
	@Override
	public void show() {
		System.out.println("Enter position number of transaction: ");
		int index = this.scanner.nextInt();

		try {
			Transaction transaction = transactionRepo.get(index);
			TransactionView transactionView = new TransactionViewImpl(this, this.scanner, transaction);
			transactionView.menu();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void add() {
		System.out.println("Enter receiver name: ");
		String name = this.scanner.nextLine();

//		System.out.println("Enter receiver country: ");
//		String country = this.scanner.nextLine();
//		
//		System.out.println("Enter receiver city: ");
//		String city = this.scanner.nextLine();
//		
//		System.out.println("Enter receiver zipcode: ");
//		int zipcode = this.scanner.nextInt();
//		this.scanner.nextLine();
//		
//		System.out.println("Enter receiver street: ");
//		String street = this.scanner.nextLine();

		System.out.println("Enter IBAN: ");
		String iban = this.scanner.nextLine();

		System.out.println("Enter BIC: ");
		String bic = this.scanner.nextLine();

		System.out.println("Enter institute: ");
		String institute = this.scanner.nextLine();

		System.out.println("Enter purpose: ");
		String purpose = this.scanner.nextLine();

		System.out.println("Enter amount: ");
		while (!this.scanner.hasNextFloat()) {
			System.out.println("Your entered value");
			this.scanner.next();
		}
		Float amount = this.scanner.nextFloat();
		this.scanner.nextLine();

		LocalDateTime date = LocalDateTime.now();

		Bic bicObject = new BicImpl(this, bic, institute);
		Iban ibanObject = new IbanImpl(this, iban, bicObject);
		Receiver receiverObject = new ReceiverImpl(this, name);
		Transaction transaction = new TransactionImpl(this, amount, receiverObject, ibanObject, purpose, date);

		transactionRepo.add(transaction);
	}

//	public void getBalance() {
//		float amount = 0.00f;
//		for (Object o : transactionList) {
//			Transaction transaction = (Transaction)o;
//			amount = amount + transaction.getAmount();
//		}
//		
//		System.out.println("Your Balance is: " + String.format("%.2f", amount) + " €");
//	}

//	public void exportCsv() {
//		try {
//			System.out.println("Enter Filename: ");
//			String fileName = this.scanner.nextLine();
//			if (!fileName.endsWith(".csv")) {
//				fileName = fileName + ".csv";
//			}
//			transactionList.exportCsv(fileName);
//		}
//		catch(Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}

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

//	public void importCsv() {
//		try {
//			System.out.println("Enter Filename: ");
//			String fileName = this.scanner.nextLine();
//			if (!fileName.endsWith(".csv")) {
//				fileName = fileName + ".csv";
//			}
//			CSVImp csvImport = new CSVImpImpl(fileName);
//			
//			
//			for (Map<String, String> map : csvImport.importCsv(fileName)) {
//				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
//				LocalDateTime date = LocalDateTime.parse(map.get("date"), formatter);
//				Transaction transaction = new TransactionImpl(this,Float.parseFloat(map.get("amount")),
//																	map.get("receiver"),
//																	map.get("iban"),
//																	map.get("bic"),
//																	map.get("purpose"),date);
//			}
//			
//		}
//		catch(Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}

//	public void search() {
//		System.out.println("Search: ");
//		String search = this.scanner.nextLine();
//		GenericList<Transaction> foundObjects = transactionList.search(search);
//		TransactionListView foundObjectsView = new RepositoryTransactionViewImpl(this, this.scanner, foundObjects);
//		foundObjectsView.menu();
//	}

}
