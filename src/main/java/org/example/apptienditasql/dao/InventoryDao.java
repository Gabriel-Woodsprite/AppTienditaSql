package org.example.apptienditasql.dao;

import org.example.apptienditasql.interfaces.DaoInterface;
import org.example.apptienditasql.model.Purchase;
import org.example.apptienditasql.model.PurchaseDetail;
import org.example.apptienditasql.model.Supplier;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InventoryDao implements DaoInterface<Purchase, String> {
	Logger logger = Logger.getLogger(InventoryDao.class.getName());
	private final Connection connection;

	public InventoryDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Purchase entity) {
		String sql = """
				INSERT INTO purchase(purchaseDate, folio, cost, Suppliers_idProveedores)
				VALUES(?,?,?,?)
				""";
		try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setDate(1, Date.valueOf(entity.getPurchaseDate()));
			ps.setString(2, entity.getFolio());
			ps.setBigDecimal(3, entity.getCost());
			ps.setInt(4, entity.getSupplierId());
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			int purchaseId = -1;
			if (rs.next()) {
				purchaseId = rs.getInt(1);
			}

			for (PurchaseDetail purchaseDetail : entity.getSaleDetails()) {
				createPurchaseDetail(purchaseDetail, purchaseId);
			}

		} catch (SQLException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
	}

	private void createPurchaseDetail(PurchaseDetail entity, int purchaseId) {
		String sql = """
				INSERT INTO purchasedetails(quantity, unitPrice, total, expireDate, Purchase_idpurchase, Catalogue_barCode)
				VALUES(?,?,?,?,?,?)
				""";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, entity.getQuantity());
			ps.setString(2, entity.getUnitPrice());
			ps.setString(3, entity.getTotal());
			ps.setDate(4, Date.valueOf(entity.getExpiryDate()));
			ps.setInt(5, purchaseId);
			ps.setInt(6, entity.getBarcode());
			ps.executeUpdate();

			createInventory(Integer.parseInt(entity.getQuantity()), entity.getBarcode());
		} catch (SQLException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
	}

	private void createInventory(int quantity, int productBarcode) {
		int currentQuantity = selectInventoryQuantity(productBarcode);
		if (currentQuantity == -1) {
			String insert = """
					INSERT INTO inventory(quantity, Catalogue_barCode)
					VALUES(?,?)
					""";
			try (PreparedStatement ps = connection.prepareStatement(insert)) {
				ps.setInt(1, quantity);
				ps.setInt(2, productBarcode);
				ps.executeUpdate();
			} catch (SQLException e) {
				logger.log(Level.WARNING, e.getMessage());
			}
		} else {
			String updateInventory = """
					UPDATE inventory SET quantity = ? WHERE Catalogue_barCode = ?;
					""";
			try (PreparedStatement ps = connection.prepareStatement(updateInventory)) {
				ps.setInt(1, currentQuantity + quantity);
				ps.setInt(2, productBarcode);
				ps.executeUpdate();
			} catch (SQLException e) {
				logger.log(Level.WARNING, e.getMessage());
			}
		}
	}

	public int selectInventoryQuantity(int barcode) {
		int quantity = -1;
		String sql = """
				SELECT quantity FROM inventory WHERE Catalogue_barCode = ?;
				""";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, barcode);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				quantity = rs.getInt("quantity");
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
		return quantity;
	}

	@Override
	public Purchase read(String key) {
		return null;
	}

	@Override
	public List<Purchase> readAll() {
		return List.of();
	}

	@Override
	public void update(Purchase updatedEntity) {

	}

	@Override
	public void delete(String key) {

	}
}
