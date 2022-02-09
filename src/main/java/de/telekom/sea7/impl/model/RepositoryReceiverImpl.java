package de.telekom.sea7.impl.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.telekom.sea7.impl.ApplicationImpl;
import de.telekom.sea7.impl.BaseObjectImpl;
import de.telekom.sea7.inter.model.Iban;
import de.telekom.sea7.inter.model.Receiver;
import de.telekom.sea7.inter.model.Repository;

public class RepositoryReceiverImpl extends BaseObjectImpl implements Repository<Receiver>  {

	private String sqlForGetAll = "SELECT * FROM receiver";
	private String sqlForGet = "SELECT * FROM receiver WHERE ID = ?";
	private String sqlForAdd = "INSERT INTO receiver (country, zipcode, city, street, name, ID) VALUES (?, ?, ?, ? ,? ,?)";
	private String sqlForRem = "DELETE FROM receiver WHERE ID = ?";
	
	private Connection connection = ApplicationImpl.getApplication().connection;
	
	private PreparedStatement psForGetAll = connection.prepareStatement(sqlForGetAll);
	private PreparedStatement psForGet = connection.prepareStatement(sqlForGet);
	private PreparedStatement psForAdd = connection.prepareStatement(sqlForAdd);
	private PreparedStatement psForRem = connection.prepareStatement(sqlForRem);

	public RepositoryReceiverImpl(Object parent) throws SQLException {
		super(parent);
	}
	
	public List<Receiver> getAll() throws SQLException {
		List<Receiver> receiverList = new ArrayList<Receiver>();
		ResultSet rs = psForGetAll.executeQuery();
		while (rs.next()) {
			int id = rs.getInt("ID");
			String country = rs.getString("country");
			int zipcode = rs.getInt("zipcode");
			String city = rs.getString("city");
			String street = rs.getString("street");
			String name = rs.getString("name");
			Receiver receiverObject = new ReceiverImpl(this, id, country, zipcode, city, street, name);
			receiverList.add(receiverObject);
		}
		return receiverList;
	}
	
	public Receiver get(int index) throws SQLException {
		psForGet.setInt(1, index);
		ResultSet rs = psForGet.executeQuery();
		if (rs.next()) {
			int id = rs.getInt("ID");
			String country = rs.getString("country");
			int zipcode = rs.getInt("zipcode");
			String city = rs.getString("city");
			String street = rs.getString("street");
			String name = rs.getString("name");
			Receiver receiverObject = new ReceiverImpl(this, id, country, zipcode, city, street, name);
			return receiverObject;
		}
		else {
			throw new SQLException();
		}
	}
	
	public void add(Receiver receiver) {
		
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
