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
import de.telekom.sea7.inter.model.Repository;

public class RepositoryIbanImpl extends BaseObjectImpl implements Repository<Iban> {
	
	private String sqlForGetAll = "SELECT * FROM iban";
	private String sqlForGet = "SELECT * FROM iban WHERE ID = ?";
	private String sqlForAdd = "INSERT INTO iban (iban, bic_ID) VALUES (?,?)";
	private String sqlForRem = "DELETE FROM iban WHERE ID = ?";
	private String sqlForUpd = "UPDATE iban set iban = ? WHERE ID = ?";
	
	private Connection connection = ApplicationImpl.getApplication().connection;
	
	private PreparedStatement psForGetAll = connection.prepareStatement(sqlForGetAll);
	private PreparedStatement psForGet = connection.prepareStatement(sqlForGet);
	private PreparedStatement psForAdd = connection.prepareStatement(sqlForAdd, Statement.RETURN_GENERATED_KEYS);
	private PreparedStatement psForRem = connection.prepareStatement(sqlForRem);
	private PreparedStatement psForUpd = connection.prepareStatement(sqlForUpd);

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
			int bic_ID = rs.getInt("bic_ID");
			
			Repository<Bic> bicRepo = new RepositoryBicImpl(this);
			Bic bic = bicRepo.get(bic_ID);
					
			Iban ibanObject = new IbanImpl(this, iban, bic);
			ibanObject.setId(id);
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
			int bic_ID = rs.getInt("bic_ID");
			
			Repository<Bic> bicRepo = new RepositoryBicImpl(this);
			Bic bic = bicRepo.get(bic_ID);
			
			Iban ibanObject = new IbanImpl(this, iban, bic);
			ibanObject.setId(id);
			return ibanObject;
		}
		else {
			throw new SQLException();
		}
	}
	
	@Override
	public void add(Iban iban) {
		try {
			Repository<Bic> bicRepo = new RepositoryBicImpl(this);
			bicRepo.add(iban.getBic());
			
			psForAdd.setString(1, iban.getIban());
			psForAdd.setInt(2, iban.getBic().getId());
			psForAdd.execute();
			
			ResultSet rs = psForAdd.getGeneratedKeys();
			if (rs.next()) {
				iban.setId(rs.getInt(1));
			}
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void update(Iban iban) {
		try {
			Repository<Bic> bicRepo = new RepositoryBicImpl(this);
			bicRepo.update(iban.getBic());
			
			psForUpd.setString(1, iban.getIban());
			psForUpd.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
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
