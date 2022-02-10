package de.telekom.sea7.impl.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import de.telekom.sea7.impl.ApplicationImpl;
import de.telekom.sea7.impl.BaseObjectImpl;
import de.telekom.sea7.inter.model.Iban;
import de.telekom.sea7.inter.model.Receiver;
import de.telekom.sea7.inter.model.Repository;
import de.telekom.sea7.inter.model.Transaction;

public class RepositoryTransactionImpl extends BaseObjectImpl implements Repository<Transaction>  {
	
	private String sqlForGetAll = "SELECT * FROM transactions";
	private String sqlForGet = "SELECT * FROM transactions WHERE ID = ?";
	private String sqlForAdd = "INSERT INTO transactions (receiver_ID, iban_ID, amount, purpose, date) VALUES (?, ?, ? ,? ,?)";
	private String sqlForRem = "DELETE FROM transactions WHERE ID = ?";
	private String sqlForUpd = "UPDATE transaction set amount = ?, purpose = ?, date = ? WHERE ID = ?";
	
	private Connection connection = ApplicationImpl.getApplication().connection;
	
	private PreparedStatement psForGetAll = connection.prepareStatement(sqlForGetAll);
	private PreparedStatement psForGet = connection.prepareStatement(sqlForGet);
	private PreparedStatement psForAdd = connection.prepareStatement(sqlForAdd, Statement.RETURN_GENERATED_KEYS);
	private PreparedStatement psForRem = connection.prepareStatement(sqlForRem);
	private PreparedStatement psForUpd = connection.prepareStatement(sqlForUpd);

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
			
			Transaction transactionObject = new TransactionImpl(this, amount, receiver, iban, purpose, date);
			transactionObject.setId(id);
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
			
			Transaction transactionObject = new TransactionImpl(this, amount, receiver, iban, purpose, date);
			transactionObject.setId(id);
			return transactionObject;
		}
		else {
			throw new SQLException();
		}
	}
	
	@Override
	public void add(Transaction transaction) {
		try {
			Repository<Receiver> receiverRepo = new RepositoryReceiverImpl(this);
			receiverRepo.add(transaction.getReceiver());
			
			Repository<Iban> ibanRepo = new RepositoryIbanImpl(this);
			ibanRepo.add(transaction.getIban());
			
			BigDecimal value = new BigDecimal(Float.toString(transaction.getAmount()));
			
			psForAdd.setInt(1, transaction.getReceiver().getId());
			psForAdd.setInt(2, transaction.getIban().getId());
			psForAdd.setBigDecimal(3, value);
			psForAdd.setString(4, transaction.getPurpose());
			psForAdd.setTimestamp(5, Timestamp.valueOf(transaction.getDate()));
			psForAdd.execute();
			ResultSet rs = psForAdd.getGeneratedKeys();
			
			if (rs.next()) {
				transaction.setId(rs.getInt(1));
			}
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void update(Transaction transaction) {
		try {
			Repository<Iban> ibanRepo = new RepositoryIbanImpl(this);
			ibanRepo.update(transaction.getIban());
			
			Repository<Receiver> receiverRepo = new RepositoryReceiverImpl(this);
			receiverRepo.update(transaction.getReceiver());
			
			BigDecimal value = new BigDecimal(Float.toString(transaction.getAmount()));
			
			
			psForUpd.setBigDecimal(1, value);
			psForUpd.setString(2, transaction.getPurpose());
			psForUpd.setTimestamp(3, Timestamp.valueOf(transaction.getDate()));
			psForUpd.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
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
