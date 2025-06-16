package org.example.apptienditasql.dao;

import org.example.apptienditasql.interfaces.DaoInterface;
import org.example.apptienditasql.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SuppliersDao implements DaoInterface<Supplier, String> {
	Logger logger = Logger.getLogger(SuppliersDao.class.getName());
	private final Connection connection;

	public SuppliersDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Supplier supplier) {
		String sql = "INSERT INTO suppliers(supplierName, contactPerson, tel, email, type, address, notes) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			attributesSet(supplier, ps);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		}
	}

	@Override
	public Supplier read(String key) {
		Supplier supplier = null;
		String sql = "SELECT * FROM suppliers WHERE idProveedores = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, Integer.parseInt(key));
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				supplier = new Supplier();
				supplier.setSupplierId(rs.getInt("idProveedores"));
				supplier.setName(rs.getString("supplierName"));
				supplier.setContactPerson(rs.getString("contactPerson"));
				supplier.setTel(rs.getString("tel"));
				supplier.setEmail(rs.getString("email"));
				supplier.setType(rs.getString("type"));
				supplier.setAddress(rs.getString("address"));
				supplier.setNotes(rs.getString("notes"));
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		}
		return supplier;
	}

	public int getSupplierByName(String supplierName) {
		String sql = "SELECT idProveedores FROM suppliers WHERE supplierName = ? ";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, supplierName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt("idProveedores");
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		}

		return -1;
	}

	@Override
	public List<Supplier> readAll() {
		String sql = "SELECT * FROM suppliers";
		List<Supplier> suppliers = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				suppliers.add(read(String.valueOf(rs.getInt("idProveedores"))));
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		}
		return suppliers;
	}

	@Override
	public void update(Supplier updatedEntity) {

	}

	@Override
	public void delete(String key) {
		String sql = "DELETE FROM suppliers WHERE idProveedores = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, Integer.parseInt(key));
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		}
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
