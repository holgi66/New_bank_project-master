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
import de.telekom.sea7.inter.model.Repository;

public class RepositoryIbanImpl extends BaseObjectImpl implements Repository<Iban> {
	
	private String sqlForGetAll = "SELECT * FROM iban";
	private String sqlForGet = "SELECT * FROM iban WHERE ID = ?";
	private String sqlForAdd = "INSERT INTO iban (iban) VALUES (?)";
	private String sqlForRem = "DELETE FROM iban WHERE ID = ?";
	
	private Connection connection = ApplicationImpl.getApplication().connection;
	
	private PreparedStatement psForGetAll = connection.prepareStatement(sqlForGetAll);
	private PreparedStatement psForGet = connection.prepareStatement(sqlForGet);
	private PreparedStatement psForAdd = connection.prepareStatement(sqlForAdd);
	private PreparedStatement psForRem = connection.prepareStatement(sqlForRem);

	public RepositoryIbanImpl(Object parent) throws SQLException {
		super(parent);
	}
	
	@Override
	public List<Iban> getAll() throws SQLException {
		List<Iban> ibanList = new ArrayList<Iban>();
		ResultSet rs = psForGetAll.executeQuery();
		while (rs.next()) {
			int id = rs.getInt("ID");
			String iban = rs.getString("iban");
			Iban ibanObject = new IbanImpl(this, id, iban);
			ibanList.add(ibanObject);
		}
		return ibanList;
	}
	
	@Override
	public Iban get(int index) throws SQLException {
		psForGet.setInt(1, index);
		ResultSet rs = psForGet.executeQuery();
		if (rs.next()) { 
			int id = rs.getInt("ID");
			String iban = rs.getString("iban");
			Iban ibanObject = new IbanImpl(this, id, iban);
			return ibanObject;
		}
		else {
			throw new SQLException();
		}
	}
	
	@Override
	public void add(Iban iban) {
		
	}
	
	@Override
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
