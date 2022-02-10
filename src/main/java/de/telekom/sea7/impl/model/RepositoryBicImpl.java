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
import de.telekom.sea7.inter.model.Repository;

public class RepositoryBicImpl extends BaseObjectImpl implements Repository<Bic> {

	private String sqlForGetAll = "SELECT * FROM bic";
	private String sqlForGet = "SELECT * FROM bic WHERE ID = ?";
	private String sqlForAdd = "INSERT INTO bic (bic, institute) VALUES (?,?)";
	private String sqlForRem = "DELETE FROM bic WHERE ID = ?";
	private String sqlForUpd = "UPDATE bic set bic = ?, institute = ? WHERE ID = ?";

	private Connection connection = ApplicationImpl.getApplication().connection;

	private PreparedStatement psForGetAll = connection.prepareStatement(sqlForGetAll);
	private PreparedStatement psForGet = connection.prepareStatement(sqlForGet);
	private PreparedStatement psForAdd = connection.prepareStatement(sqlForAdd, Statement.RETURN_GENERATED_KEYS);
	private PreparedStatement psForRem = connection.prepareStatement(sqlForRem);
	private PreparedStatement psForUpd = connection.prepareStatement(sqlForUpd);

	public RepositoryBicImpl(Object parent) throws SQLException {
		super(parent);
	}

	public List<Bic> getAll() throws SQLException {
		List<Bic> bicList = new ArrayList<Bic>();
		ResultSet rs = psForGetAll.executeQuery();
		while (rs.next()) {
			int id = rs.getInt("ID");
			String bic = rs.getString("bic");
			String institute = rs.getString("institute");
			Bic bicObject = new BicImpl(this, bic, institute);
			bicObject.setId(id);
			bicList.add(bicObject);
		}
		return bicList;
	}

	public Bic get(int index) throws SQLException {
		psForGet.setInt(1, index);
		ResultSet rs = psForGet.executeQuery();
		if (rs.next()) {
			int id = rs.getInt("ID");
			String bic = rs.getString("bic");
			String institute = rs.getString("institute");
			Bic bicObject = new BicImpl(this, bic, institute);
			bicObject.setId(id);
			return bicObject;
		} else {
			throw new SQLException();
		}
	}

	public void add(Bic bic) {
		try {
			psForAdd.setString(1, bic.getBic());
			psForAdd.setString(2, bic.getInstitute());
			psForAdd.execute();

			ResultSet rs = psForAdd.getGeneratedKeys();
			if (rs.next()) {
				bic.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void update(Bic bic) {
		try {
			psForUpd.setString(1, bic.getBic());
			psForUpd.setString(2, bic.getInstitute());
			psForUpd.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void remove(int index) {
		try {
			psForRem.setInt(1, index);
			psForRem.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
