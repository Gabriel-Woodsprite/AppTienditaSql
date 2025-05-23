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
		String sql = "INSERT INTO Catalogue(barcode,name,brand,category,content,measurementUnit,minStock,maxStock,presentation,description,avilable,img,registerDate,productLocation,expiryDate)" +
				"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
				System.out.println("resultSet: " + rs.getString("category"));
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
		String sql = "UPDATE Catalogue SET barCode = ?, name = ?, brand = ?, category = ?, content = ?, measurementUnit = ?, minStock = ?, maxStock = ?, presentation = ?, description = ?, avilable = ?, img = ?, registerDate = ?, productLocation = ?, expiryDate = ? WHERE barcode = ?";
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
		ps.setString(4, product.getCategory());
		ps.setString(5, product.getContent());
		ps.setString(6, product.getMeasurementUnit());
		ps.setString(7, product.getMinStock());
		ps.setString(8, product.getMaxStock());
		ps.setString(9, product.getPresentation());
		ps.setString(10, product.getDescription());
		ps.setBoolean(11, product.isAvilable());
		ps.setString(12, product.getImage());
		System.out.println(product.getRegisterDate());
		System.out.println(product.getExpiryDate());
		ps.setDate(13, Date.valueOf(product.getRegisterDate()));
		ps.setString(14, product.getProductLocation());
		ps.setDate(15, Date.valueOf(product.getExpiryDate()));
	}
}
