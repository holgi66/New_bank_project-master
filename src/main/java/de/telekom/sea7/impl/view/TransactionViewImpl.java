package de.telekom.sea7.impl.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import de.telekom.sea7.impl.BaseObjectImpl;
import de.telekom.sea7.inter.model.Transaction;
import de.telekom.sea7.inter.view.TransactionView;

public class TransactionViewImpl extends BaseObjectImpl implements TransactionView {
	
	private Transaction transaction;
	private Scanner scanner;
	
	public TransactionViewImpl(Object parent, Scanner scanner, Transaction transaction) {
		super(parent);
		this.transaction = transaction;
		this.scanner = scanner;
	}
	
	@Override
	public void menu() {
		String input = "";
		while (!input.equals("back")) {
			System.out.println("Enter show, edit or back to navigate.");
			System.out.println("Enter something:");
			input = this.scanner.next();
			this.scanner.nextLine();
			switch (input) {
			case "show":
				show();
				break;
			//case "edit":
			//	editMenu();
			//	break;
			case "back":
				break;
			default:
				System.out.println("Command unknown");
			}
		}
		//menuScanner.close();
	}

	
	@Override
	public void show() {
		System.out.println("ID: " + transaction.getId());
		System.out.println("Receiver: " + transaction.getReceiver().getName());
		System.out.println("IBAN: " + transaction.getIban().getIban());
		System.out.println("Amount: " + transaction.getAmount());
		System.out.println("Purpose: " + transaction.getPurpose());
		System.out.println("Date: " + transaction.getDate());
		System.out.println();
	}
	
	/*
	@Override
	public void setReceiver() {
		System.out.println("Enter new receiver: ");
		String newEntry = this.scanner.nextLine();
		transaction.setReceiver(newEntry);
		//editScanner.close();
	}
	
	@Override
	public void setIban() {
		System.out.println("Enter new IBAN: ");
		String newEntry = this.scanner.nextLine();
		transaction.setIban(newEntry);
		//editScanner.close();
	}
	
	@Override
	public void setBic() {
		System.out.println("Enter new BIC: ");
		String newEntry = this.scanner.nextLine();
		transaction.setBic(newEntry);
		//editScanner.close();
	}
	
	@Override
	public void setPurpose() {
		System.out.println("Enter new purpose: ");
		String newEntry = this.scanner.nextLine();
		transaction.setPurpose(newEntry);
		//editScanner.close();
	}
	
	@Override
	public void setAmount() {
		System.out.println("Enter new amount: ");
		Float newEntry = this.scanner.nextFloat();
		transaction.setAmount(newEntry);
		//editScanner.close();
	}
	
		
	@Override
	public void editMenu() {
		String input = "";
		while (!input.equals("back")) {
			System.out.println("Enter receiver, iban, bic, purpose, amount to change the property or back to exit editing menu.");
			System.out.println("Enter something:");
			input = this.scanner.next();
			this.scanner.nextLine();
			switch (input) {
			case "receiver":
				setReceiver();
				break;
			case "iban":
				setIban();
				break;
			case "bic":
				setBic();
				break;
			case "purpose":
				setPurpose();
				break;
			case "amount":
				setAmount();
				break;
			case "back":
				break;
			default:
				System.out.println("Command unknown");
			}
		}
		//menuScanner.close();
	}
	
	*/
}
