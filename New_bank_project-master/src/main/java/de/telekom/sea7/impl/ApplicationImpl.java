package de.telekom.sea7.impl;

import java.util.Scanner;

import de.telekom.sea7.impl.model.GenericListImpl;
import de.telekom.sea7.impl.view.TransactionListViewImpl;
import de.telekom.sea7.inter.model.GenericList;
import de.telekom.sea7.inter.model.Transaction;
import de.telekom.sea7.inter.view.TransactionListView;

public class ApplicationImpl extends BaseObjectImpl implements Application{
	
	public ApplicationImpl(Object parent) {
		super(parent);
	}
	
	@Override
	public void run() {
		GenericList<Transaction> transactionList = new GenericListImpl<Transaction>(this);
		try (Scanner scanner = new Scanner(System.in)) {
			TransactionListView transactionListView = new TransactionListViewImpl(this, scanner, transactionList);
			transactionListView.menu();
		}
	}
}
