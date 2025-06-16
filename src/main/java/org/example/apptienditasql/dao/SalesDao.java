package org.example.apptienditasql.dao;


import org.example.apptienditasql.interfaces.DaoInterface;
import org.example.apptienditasql.model.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SalesDao implements DaoInterface<Sale, String> {
	Logger logger = Logger.getLogger(SalesDao.class.getName());

	private final Connection connection;

	public SalesDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Sale entity) {
		String insertSaleSQL = "INSERT INTO Sales (folio, dateSold) VALUES (?, ?)";
		String insertDetailSQL = "INSERT INTO SalesDetails (quantity, price, subTotal, total, Sales_folio, Catalogue_barCode) " +
				"VALUES (?, ?, ?, ?, ?, ?)";

		try {
			connection.setAutoCommit(false); // Iniciar transacción

			// 1. Insertar la venta
			try (PreparedStatement psSale = connection.prepareStatement(insertSaleSQL)) {
				psSale.setInt(1, entity.getFolio());
				psSale.setDate(2, java.sql.Date.valueOf(entity.getDateSold()));
				psSale.executeUpdate();
			}

			// 2. Insertar detalles de la venta
			try (PreparedStatement psDetail = connection.prepareStatement(insertDetailSQL)) {
				for (var detail : entity.getSaleDetails()) {
					psDetail.setString(1, detail.getQuantity());
					psDetail.setBigDecimal(2, detail.getPrice());
					psDetail.setBigDecimal(3, detail.getSubTotal());
					psDetail.setBigDecimal(4, detail.getTotal());
					psDetail.setInt(5, detail.getSaleFolio());
					psDetail.setString(6, detail.getCatalogueBarcode());
					psDetail.addBatch();
				}
				psDetail.executeBatch();
			}

			connection.commit(); // Confirmar cambios

		} catch (SQLException e) {
			try {
				connection.rollback(); // Revertir si hay error
				logger.log(Level.SEVERE, "Error al crear la venta. Transacción revertida.", e);
			} catch (SQLException rollbackEx) {
				logger.log(Level.SEVERE, "Error al revertir la transacción", rollbackEx);
			}
		} finally {
			try {
				connection.setAutoCommit(true); // Restaurar autocommit
			} catch (SQLException ex) {
				logger.log(Level.SEVERE, "No se pudo restaurar autocommit", ex);
			}
		}
	}

	@Override
	public Sale read(String key) {
		return null;
	}

	@Override
	public List<Sale> readAll() {
		return List.of();
	}

	@Override
	public void update(Sale updatedEntity) {

	}

	@Override
	public void delete(String key) {

	}

	public int getCount() {
		int count = 0;
		String sql = "SELECT COUNT(*) FROM sales";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		return count;
	}
}
