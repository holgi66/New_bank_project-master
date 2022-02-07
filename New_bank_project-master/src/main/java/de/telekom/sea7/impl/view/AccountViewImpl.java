package de.telekom.sea7.impl.view;

import java.util.Scanner;

import de.telekom.sea7.impl.BaseObjectImpl;
import de.telekom.sea7.inter.model.Account;
import de.telekom.sea7.inter.model.GenericList;
import de.telekom.sea7.inter.model.Transaction;
import de.telekom.sea7.inter.view.AccountView;
import de.telekom.sea7.inter.view.TransactionListView;

public class AccountViewImpl extends BaseObjectImpl implements AccountView {
	
	private Account account;
	private Scanner scanner;
	
	public AccountViewImpl(Object parent, Scanner scanner, Account account) {
		super(parent);
		this.account = account;
		this.scanner = scanner;
	}

	//menu
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
			case "edit":
				editMenu();
				break;
			case "transactions":
				editMenu();
				break;
			case "back":
				break;
			default:
				System.out.println("Command unknown");
			}
		}
		//menuScanner.close();
	}
	
	//editMenu
	@Override
	public void editMenu() {
		String input = "";
		while (!input.equals("back")) {
			System.out.println("Enter name, type, iban or bic to change the property or back to exit editing menu.");
			System.out.println("Enter something:");
			input = this.scanner.next();
			this.scanner.nextLine();
			switch (input) {
			case "name":
				setName();
				break;
			case "iban":
				setIban();
				break;
			case "bic":
				setBic();
				break;
			case "type":
				setType();
				break;
			case "back":
				break;
			default:
				System.out.println("Command unknown");
			}
		}
		//menuScanner.close();
	}
	
	//setName
	@Override
	public void setName() {
		System.out.println("Enter new account name: ");
		String newEntry = this.scanner.nextLine();
		account.setIban(newEntry);
		//editScanner.close();
	}
	
	//setType
	@Override
	public void setType() {
		System.out.println("Enter new account type: ");
		String newEntry = this.scanner.nextLine();
		account.setIban(newEntry);
		//editScanner.close();
	}
	
	//setIban
	@Override
	public void setIban() {
		System.out.println("Enter new IBAN: ");
		String newEntry = this.scanner.nextLine();
		account.setIban(newEntry);
		//editScanner.close();
	}
	
	//setBic
	@Override
	public void setBic() {
		System.out.println("Enter new BIC: ");
		String newEntry = this.scanner.nextLine();
		account.setIban(newEntry);
		//editScanner.close();
	}
	
	//show
	@Override
	public void show() {
		System.out.println("Account name: " + account.getName());
		System.out.println("Account type: " + account.getType());
		System.out.println("IBAN: " + account.getIban());
		System.out.println("BIC: " + account.getBic());
	}
	
	//getTransactionList
	@Override
	public void getTransactionList() {
		GenericList<Transaction> transactionList = account.getTransactionList();
		TransactionListView transactionListView = new TransactionListViewImpl(this, this.scanner, transactionList);
		transactionListView.menu();
	}
}
