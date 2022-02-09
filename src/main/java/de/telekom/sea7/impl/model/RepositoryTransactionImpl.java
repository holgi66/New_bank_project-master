package de.telekom.sea7.impl.model;

import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import de.telekom.sea7.impl.Application;
import de.telekom.sea7.impl.ApplicationImpl;
import de.telekom.sea7.impl.BaseObjectImpl;
import de.telekom.sea7.inter.model.Iban;
import de.telekom.sea7.inter.model.Receiver;
import de.telekom.sea7.inter.model.Repository;
import de.telekom.sea7.inter.model.Transaction;

public class RepositoryTransactionImpl extends BaseObjectImpl implements Repository<Transaction>  {
	
	private String sqlForGetAll = "SELECT * FROM transactions";
	private String sqlForGet = "SELECT * FROM transactions WHERE ID = ?";
	private String sqlForAdd = "INSERT INTO transactions (ID, receiver_ID, iban_ID, amount, purpose, date) VALUES (?, ?, ?, ? ,? ,?)";
	private String sqlForRem = "DELETE FROM transactions WHERE ID = ?";
	
	private Connection connection = ApplicationImpl.getApplication().connection;
	
	private PreparedStatement psForGetAll = connection.prepareStatement(sqlForGetAll);
	private PreparedStatement psForGet = connection.prepareStatement(sqlForGet);
	private PreparedStatement psForAdd = connection.prepareStatement(sqlForAdd);
	private PreparedStatement psForRem = connection.prepareStatement(sqlForRem);

	public RepositoryTransactionImpl(Object parent) throws SQLException {
		super(parent);
	}
	
	public List<Transaction> getAll() throws SQLException {
		List<Transaction> transactionList = new ArrayList<Transaction>();
		ResultSet rs = psForGetAll.executeQuery();
		while (rs.next()) {
			int id = rs.getInt("ID");
			int receiver_ID = rs.getInt("receiver_ID");
			int iban_ID = rs.getInt("iban_ID");
			Float amount = rs.getBigDecimal("amount").setScale(2, RoundingMode.DOWN).floatValue();
			String purpose = rs.getString("purpose");
			LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
			
			Repository<Receiver> receiverRepo = new RepositoryReceiverImpl(this);
			Receiver receiver = receiverRepo.get(receiver_ID);
			
			Repository<Iban> ibanRepo = new RepositoryIbanImpl(this);
			Iban iban = ibanRepo.get(iban_ID);
			
			Transaction transactionObject = new TransactionImpl(this, id, amount, receiver, iban, purpose, date);
			transactionList.add(transactionObject);
		}
		return transactionList;
	}
	
	public Transaction get(int index) throws SQLException {
		psForGet.setInt(1, index);
		ResultSet rs = psForGet.executeQuery();
		if (rs.next()) {
			int id = rs.getInt("ID");
			int receiver_ID = rs.getInt("receiver_ID");
			int iban_ID = rs.getInt("iban_ID");
			Float amount = rs.getBigDecimal("amount").setScale(2, RoundingMode.DOWN).floatValue();
			String purpose = rs.getString("purpose");
			LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
			
			Repository<Receiver> receiverRepo = new RepositoryReceiverImpl(this);
			Receiver receiver = receiverRepo.get(receiver_ID);
			
			Repository<Iban> ibanRepo = new RepositoryIbanImpl(this);
			Iban iban = ibanRepo.get(iban_ID);
			
			Transaction transactionObject = new TransactionImpl(this, id, amount, receiver, iban, purpose, date);
			return transactionObject;
		}
		else {
			throw new SQLException();
		}
	}
	
	public void add(Transaction transaction) {
		
	}
	
	public void remove(int index) {
		try {
			psForRem.setInt(1, index);
			psForRem.execute();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
