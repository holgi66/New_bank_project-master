package de.telekom.sea7.impl;

import java.math.RoundingMode;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Scanner;

import de.telekom.sea7.impl.model.GenericListImpl;
import de.telekom.sea7.impl.model.TransactionImpl;
import de.telekom.sea7.impl.view.TransactionListViewImpl;
import de.telekom.sea7.inter.model.GenericList;
import de.telekom.sea7.inter.model.Transaction;
import de.telekom.sea7.inter.view.TransactionListView;

public class ApplicationImpl extends BaseObjectImpl implements Application {

	public ApplicationImpl(Object parent) {
		super(parent);
	}

	@Override
	public void run() {
		try {
			try (Connection connection = getConnection()) {
				try (Statement stmt = connection.createStatement()) {
					GenericList<Transaction> transactionList = new GenericListImpl<Transaction>(this);
					ResultSet rs = stmt.executeQuery("select * from showTransactions;");
					while (rs.next()) {
						String name = rs.getString("name");
						String iban = rs.getString("iban");
						String bic = rs.getString("bic");
						float amount = rs.getBigDecimal("amount").setScale(2, RoundingMode.DOWN).floatValue();
						String purpose = rs.getString("purpose");
						LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
						Transaction transaction = new TransactionImpl(this, amount ,name  , iban , bic , purpose , date);
						transactionList.add(transaction);
					}
					try (Scanner scanner = new Scanner(System.in)) {
						TransactionListView transactionListView = new TransactionListViewImpl(this, scanner,
								transactionList);
						transactionListView.menu();
					}
				}

			
			
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public Connection getConnection() throws SQLException {

		Properties connectionProps = new Properties();
		connectionProps.put("user", "admin");
		connectionProps.put("password", "toll");
		Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/bank", connectionProps);
		System.out.println("Connected to database");
		return conn;

	}
}
