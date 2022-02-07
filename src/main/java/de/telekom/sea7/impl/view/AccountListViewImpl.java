package de.telekom.sea7.impl.view;


import java.util.Scanner;

import de.telekom.sea7.impl.BaseObjectImpl;
import de.telekom.sea7.impl.model.AccountImpl;
import de.telekom.sea7.inter.model.Account;
import de.telekom.sea7.inter.model.GenericList;
import de.telekom.sea7.inter.view.AccountListView;
import de.telekom.sea7.inter.view.AccountView;

public class AccountListViewImpl extends BaseObjectImpl implements AccountListView {

	private GenericList<Account> accountList;
	private Scanner scanner;
	
	public AccountListViewImpl(Object parent, Scanner scanner, GenericList<Account> accountList) {
		super(parent);
		this.accountList = accountList;
		this.scanner = scanner;
	}
	
	@Override
	public void add() {
		System.out.println("Enter account name: ");
		String name = this.scanner.nextLine();
		
		System.out.println("Enter account type: ");
		String type = this.scanner.nextLine();
		
		System.out.println("Enter IBAN: ");
		String iban = this.scanner.nextLine();
		
		System.out.println("Enter BIC: ");
		String bic = this.scanner.nextLine();

		Account account = new AccountImpl(this, name, type, iban, bic);
		accountList.add(account);
		//scanner.close();
	}

	@Override
	public void remove() {
		System.out.println("Enter position number of account: ");
		int position = this.scanner.nextInt();
		accountList.remove(position);
		//scanner.close();
	}
	
	//show
	@Override
	public void show() {
		if (accountList.size() > 0) { 
			for (Object o : accountList) {
				Account account = (Account)o;
				AccountView accountView = new AccountViewImpl(this, this.scanner, account);
				System.out.println("Position: " + accountList.getIndex(account));
				accountView.show();
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
		System.out.println("Enter position number of account: ");
		int index = this.scanner.nextInt();
		
		if (index < 0 || index > accountList.size() - 1) {
			System.out.println("Specified entry does not belong to an account.");
		}
		else {
			AccountView accountView = new AccountViewImpl(this, this.scanner, accountList.getOneObject(index));
			accountView.menu();
		}
		//scanner.close();
	}
	
	//menu
	@Override
	public void menu() {
		String input = "";
		while (!input.equals("exit")) {
			System.out.println("Enter show, showOne, add, remove or exit to navigate.");
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
				case "add":
					add();
					break;
				case "remove":
					remove();
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
