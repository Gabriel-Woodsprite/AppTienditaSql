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
		String sql = "INSERT INTO Catalogue(name,brand,category,content,measurementUnit,minStock,maxStock,presentation,description,avilable,img,registerDate,productLocation,expiryDate)" +
				"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, product.getName());
			ps.setString(2, product.getBrand());
			ps.setString(3, product.getCategory());
			ps.setString(4, product.getContent());
			ps.setString(5, product.getMeasurementUnit());
			ps.setString(6, product.getMinStock());
			ps.setString(7, product.getMaxStock());
			ps.setString(8, product.getPresentation());
			ps.setString(9, product.getDescription());
			ps.setBoolean(10, product.isAvilable());
			ps.setString(11, product.getImage());
			ps.setDate(12, (Date) product.getRegisterDate());
			ps.setString(13, product.getProductLocation());
			ps.setDate(14, (Date) product.getExpiryDate());
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
		String sql = "SELECT * FROM Catalogue WHERE barcode = ?";
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
				product.setMeasurementUnit(rs.getString("measurementUnit"));
				product.setMinStock(rs.getString("minStock"));
				product.setMaxStock(rs.getString("maxStock"));
				product.setPresentation(rs.getString("presentation"));
				product.setDescription(rs.getString("description"));
				product.setAvilable(rs.getBoolean("avilable"));
				product.setImage(rs.getString("img"));
				product.setRegisterDate(rs.getDate("registerDate"));
				product.setProductLocation(rs.getString("productLocation"));
				product.setRegisterDate(rs.getDate("registerDate"));
				product.setProductLocation(rs.getString("productLocation"));
				product.setExpiryDate(rs.getDate("expiryDate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
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
		String sql = "UPDATE Catalogue SET name = ?, brand = ?, category = ?, content = ?, measurementUnit = ?, minStock = ?, maxStock = ?, presentation = ?, description = ?, avilable = ?, img = ?, registerDate = ?, productLocation = ?, expiryDate = ? WHERE barcode = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, updatedProduct.getName());
			ps.setString(2, updatedProduct.getBrand());
			ps.setString(3, updatedProduct.getCategory());
			ps.setString(4, updatedProduct.getContent());
			ps.setString(5, updatedProduct.getMeasurementUnit());
			ps.setString(6, updatedProduct.getMinStock());
			ps.setString(7, updatedProduct.getMaxStock());
			ps.setString(8, updatedProduct.getPresentation());
			ps.setString(9, updatedProduct.getDescription());
			ps.setBoolean(10, updatedProduct.isAvilable());
			ps.setString(11, updatedProduct.getImage());
			ps.setDate(12, (Date) updatedProduct.getRegisterDate());
			ps.setString(13, updatedProduct.getProductLocation());
			ps.setDate(14, (Date) updatedProduct.getExpiryDate());
			ps.setString(15, updatedProduct.getBarcode());

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
}
