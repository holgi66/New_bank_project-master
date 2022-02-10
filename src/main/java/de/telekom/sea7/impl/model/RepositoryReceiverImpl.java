package de.telekom.sea7.impl.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.telekom.sea7.impl.ApplicationImpl;
import de.telekom.sea7.impl.BaseObjectImpl;
import de.telekom.sea7.inter.model.Bic;
import de.telekom.sea7.inter.model.Iban;
import de.telekom.sea7.inter.model.Receiver;
import de.telekom.sea7.inter.model.Repository;

public class RepositoryReceiverImpl extends BaseObjectImpl implements Repository<Receiver>  {

	private String sqlForGetAll = "SELECT * FROM receiver";
	private String sqlForGet = "SELECT * FROM receiver WHERE ID = ?";
	private String sqlForAdd = "INSERT INTO receiver (name) VALUES (?)";
	private String sqlForRem = "DELETE FROM receiver WHERE ID = ?";
	private String sqlForUpd = "UPDATE receiver set name = ? WHERE ID = ?";
	
	private Connection connection = ApplicationImpl.getApplication().connection;
	
	private PreparedStatement psForGetAll = connection.prepareStatement(sqlForGetAll);
	private PreparedStatement psForGet = connection.prepareStatement(sqlForGet);
	private PreparedStatement psForAdd = connection.prepareStatement(sqlForAdd, Statement.RETURN_GENERATED_KEYS);
	private PreparedStatement psForRem = connection.prepareStatement(sqlForRem);
	private PreparedStatement psForUpd = connection.prepareStatement(sqlForUpd);

	public RepositoryReceiverImpl(Object parent) throws SQLException {
		super(parent);
	}
	
	public List<Receiver> getAll() throws SQLException {
		List<Receiver> receiverList = new ArrayList<Receiver>();
		ResultSet rs = psForGetAll.executeQuery();
		while (rs.next()) {
			int id = rs.getInt("ID");
//			String country = rs.getString("country");
//			int zipcode = rs.getInt("zipcode");
//			String city = rs.getString("city");	public void update(Iban iban) {
//			String street = rs.getString("street");
			String name = rs.getString("name");
			Receiver receiverObject = new ReceiverImpl(this, name);
			receiverObject.setId(id);
			receiverList.add(receiverObject);
		}
		return receiverList;
	}
	
	public Receiver get(int index) throws SQLException {
		psForGet.setInt(1, index);
		ResultSet rs = psForGet.executeQuery();
		if (rs.next()) {
			int id = rs.getInt("ID");
//			String country = rs.getString("country");
//			int zipcode = rs.getInt("zipcode");
//			String city = rs.getString("city");
//			String street = rs.getString("street");
			String name = rs.getString("name");
			Receiver receiverObject = new ReceiverImpl(this, name);
			receiverObject.setId(id);
			return receiverObject;
		}
		else {
			throw new SQLException();
		}
	}
	
	public void add(Receiver receiver) {
		try {
//			psForAdd.setString(1, receiver.getCountry());
//			psForAdd.setInt(2, receiver.getZipcode());
//			psForAdd.setString(3, receiver.getCity());
//			psForAdd.setString(4, receiver.getStreet());
			psForAdd.setString(1, receiver.getName());
			psForAdd.execute();
			
			ResultSet rs = psForAdd.getGeneratedKeys();
			if (rs.next()) {
				receiver.setId(rs.getInt(1));
			}
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void update(Receiver receiver) {
		try {
			
			psForUpd.setString(1, receiver.getName());
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
