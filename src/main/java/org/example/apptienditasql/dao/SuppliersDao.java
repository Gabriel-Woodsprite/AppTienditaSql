package org.example.apptienditasql.dao;

import org.example.apptienditasql.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SuppliersDao {
	Logger logger = Logger.getLogger(SuppliersDao.class.getName());
	private final Connection connection;

	public SuppliersDao(Connection connection) {
		this.connection = connection;
	}

	public boolean create(Supplier supplier) {
		String sql = "INSERT INTO suppliers(supplierName, contactPerson, tel, email, type, address, notes) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			attributesSet(supplier, ps);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		}
		return false;
	}

	private void attributesSet(Supplier supplier, PreparedStatement ps) throws SQLException {
		ps.setString(1, supplier.getName());
		ps.setString(2, supplier.getContactPerson());
		ps.setString(3, supplier.getTel());
		ps.setString(4, supplier.getEmail());
		ps.setString(5, supplier.getType());
		ps.setString(6, supplier.getAddress());
		ps.setString(7, supplier.getNotes());
	}
}
