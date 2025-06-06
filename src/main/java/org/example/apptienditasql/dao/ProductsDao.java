package org.example.apptienditasql.dao;

import org.example.apptienditasql.interfaces.*;
import org.example.apptienditasql.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductsDao implements ProductDaoInterface {
	private final Connection connection;

	public ProductsDao(Connection connection) {
		this.connection = connection;
	}

	//////////////////
	//////CREATE//////
	//////////////////
	@Override
	public void create(Product product) {
		String sql = "INSERT INTO Catalogue(barcode,name,brand,content,minStock,maxStock,description,available,img,registerDate,productLocation,expiryDate,Category_idCategory,Presentation_idPresentation,MeasurementUnit_idMeasurementUnit)" +
				"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			attributesSet(product, ps);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	///////////////////
	///////READ////////
	///////////////////
	@Override
	public Product read(String barcode) {
		Product product = null;
		String sql = "SELECT * FROM catalogue\n" +
				"    INNER JOIN apptienditadb.category c\n" +
				"        ON catalogue.Category_idCategory = c.idCategory\n" +
				"    INNER JOIN apptienditadb.measurementunit mu\n" +
				"        ON catalogue.MeasurementUnit_idMeasurementUnit = mu.idMeasurementUnit\n" +
				"    INNER JOIN apptienditadb.presentation p\n" +
				"        ON catalogue.Presentation_idPresentation = p.idPresentation WHERE barcode = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, barcode);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				product = new Product();
				product.setBarcode(rs.getString("barcode"));
				product.setName(rs.getString("name"));
				product.setBrand(rs.getString("brand"));
				product.setCategory(rs.getString("category"));
				product.setContent(rs.getString("content"));
				product.setMeasurementUnit(rs.getString("unit"));
				product.setMinStock(rs.getString("minStock"));
				product.setMaxStock(rs.getString("maxStock"));
				product.setPresentation(rs.getString("presentation"));
				product.setDescription(rs.getString("description"));
				product.setAvilable(rs.getBoolean("available"));
				product.setImage(rs.getString("img"));
				product.setRegisterDate(rs.getDate("registerDate").toLocalDate());
				product.setProductLocation(rs.getString("productLocation"));
				product.setRegisterDate(rs.getDate("registerDate").toLocalDate());
				product.setProductLocation(rs.getString("productLocation"));
				product.setExpiryDate(rs.getDate("expiryDate").toLocalDate());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	public List<String> readCategories() {
		List<String> categories = new ArrayList<>();
		String sql = "SELECT * FROM category";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				categories.add(rs.getString("category"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}

	public List<String> readUnits() {
		List<String> units = new ArrayList<>();
		String sql = "SELECT * FROM measurementunit";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				units.add(rs.getString("unit"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return units;
	}

	public List<String> redPresentations() {
		List<String> presentations = new ArrayList<>();
		String sql = "SELECT * FROM presentation";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				presentations.add(rs.getString("presentation"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return presentations;
	}

	///////////////////
	//////READ ALL/////
	///////////////////
	@Override
	public List<Product> readAll() {
		String sql = "SELECT * FROM Catalogue";
		List<Product> products = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				products.add(read(rs.getString("barCode")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
	}

	///////////////////
	//////UPDATE///////
	///////////////////
	@Override
	public void update(Product updatedProduct) {
		String sql = "UPDATE Catalogue SET barCode = ?, name = ?, brand = ?, content = ?, minStock = ?, maxStock = ?, description = ?, available = ?, img = ?, registerDate = ?, productLocation = ?, expiryDate = ?, Category_idCategory = ?, Presentation_idPresentation = ?, MeasurementUnit_idMeasurementUnit = ? WHERE barcode = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			attributesSet(updatedProduct, ps);
			ps.setString(16, updatedProduct.getBarcode());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	///////////////////
	//////DELETE///////
	///////////////////
	@Override
	public void delete(String barcode) {
		String sql = "DELETE FROM Catalogue WHERE barcode = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, barcode);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	private void attributesSet(Product product, PreparedStatement ps) throws SQLException {
		ps.setString(1, product.getBarcode());
		ps.setString(2, product.getName());
		ps.setString(3, product.getBrand());
		ps.setString(4, product.getContent());
		ps.setString(5, product.getMinStock());
		ps.setString(6, product.getMaxStock());
		ps.setString(7, product.getDescription());
		ps.setBoolean(8, product.isAvilable());
		ps.setString(9, product.getImage());
		ps.setDate(10, Date.valueOf(product.getRegisterDate()));
		ps.setString(11, product.getProductLocation());
		ps.setDate(12, Date.valueOf(product.getExpiryDate()));

		System.out.println("CATEGORÍA: " + getIdByName("category", "idCategory", "category", product.getCategory()));
		System.out.println("PRESENTACION" + getIdByName("presentation", "idPresentation", "presentation", product.getPresentation()));
		System.out.println("MEASUREMENTUNIT" + getIdByName("measurementUnit", "idMeasurementUnit", "unit", product.getMeasurementUnit()));
		ps.setInt(13, getIdByName("category", "idCategory", "category", product.getCategory()));
		ps.setInt(14, getIdByName("presentation", "idPresentation", "presentation", product.getPresentation()));
		ps.setInt(15, getIdByName("measurementUnit", "idMeasurementUnit", "unit", product.getMeasurementUnit()));
	}

	private int getIdByName(String table, String idColumn, String nameColumn, String nameValue) {
		String sql = "SELECT " + idColumn + " FROM " + table + " WHERE " + nameColumn + " = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, nameValue);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(idColumn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
