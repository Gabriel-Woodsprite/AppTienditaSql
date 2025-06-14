package org.example.apptienditasql.dao;

import org.example.apptienditasql.interfaces.*;
import org.example.apptienditasql.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductsDao implements ProductDaoInterface {
	private final static Logger logger = Logger.getLogger(ProductsDao.class.getName());

	private final Connection connection;

	public ProductsDao(Connection connection) {
		this.connection = connection;
	}

	//////////////////
	//////CREATE//////
	//////////////////
	@Override
	public void create(Product product) {
		String sql = "INSERT INTO Catalogue(barcode,name,brand,content,minStock,maxStock,description,available,img,registerDate,Category_idCategory,Presentation_idPresentation,Units_idUnits,Location_idLocation)" +
				"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			attributesSet(product, ps);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error al insertar el producto: " + product.getName(), e);
		}
	}

	public void createOptions(String table, String optionValue) {
		String sql;
		sql = switch (table) {
			case "category" -> "INSERT INTO category(category) VALUES(?)";
			case "location" -> "INSERT INTO location(location) VALUES(?)";
			case "presentation" -> "INSERT INTO presentation(presentation) VALUES(?)";
			case "units" -> "INSERT INTO units(unit) VALUES(?)";
			default -> null;
		};
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, optionValue);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error al crear opción: " + table, e);
		}
	}

//	public void createUnit(String unit) {
//		String sql = "INSERT INTO units(unit) VALUES(?)";
//		try (PreparedStatement ps = connection.prepareStatement(sql)) {
//			ps.setString(1, unit);
//			ps.executeUpdate();
//		} catch (SQLException e) {
//			logger.log(Level.SEVERE, "Error al crear Unidades", e);
//		}
//	}

//	public void createLocation(String location) {
//		String sql = "INSERT INTO location(location) VALUES(?)";
//		try (PreparedStatement ps = connection.prepareStatement(sql)) {
//			ps.setString(1, location);
//			ps.executeUpdate();
//		} catch (SQLException e) {
//			logger.log(Level.SEVERE, "Error al crear Ubicación", e);
//		}
//	}

//	public void createCategory(String category) {
//		String sql = "INSERT INTO category(category) VALUES(?)";
//		try (PreparedStatement ps = connection.prepareStatement(sql)) {
//			ps.setString(1, category);
//			ps.executeUpdate();
//		} catch (SQLException e) {
//			logger.log(Level.SEVERE, "Error al crear Categorias", e);
//		}
//	}

//	public void createPresentation(String presentation) {
//		String sql = "INSERT INTO presentation(presentation) VALUES(?)";
//		try (PreparedStatement ps = connection.prepareStatement(sql)) {
//			ps.setString(1, presentation);
//			ps.executeUpdate();
//		} catch (SQLException e) {
//			logger.log(Level.SEVERE, "Error al crear Presentaciones", e);
//		}
//	}

	///////////////////
	///////READ////////
	///////////////////
	@Override
	public Product read(String barcode) {
		Product product = null;
		String sql = """
                SELECT * FROM catalogue
                    INNER JOIN apptienditadb.category c
                        ON catalogue.Category_idCategory = c.idCategory
                    INNER JOIN apptienditadb.units mu
                        ON catalogue.Units_idUnits = mu.idUnits
                    INNER JOIN apptienditadb.presentation p
                        ON catalogue.Presentation_idPresentation = p.idPresentation
                     INNER JOIN apptienditadb.location l
                        ON catalogue.Location_idLocation = l.idLocation
                WHERE barcode = ?""";

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
				product.setUnits(rs.getString("unit"));
				product.setMinStock(rs.getString("minStock"));
				product.setMaxStock(rs.getString("maxStock"));
				product.setPresentation(rs.getString("presentation"));
				product.setDescription(rs.getString("description"));
				product.setAvilable(rs.getBoolean("available"));
				product.setImage(rs.getString("img"));
				product.setRegisterDate(rs.getDate("registerDate").toLocalDate());
				product.setProductLocation(rs.getString("location"));
				product.setRegisterDate(rs.getDate("registerDate").toLocalDate());
				product.setPrice(rs.getString("unitPrice"));
				product.setCantidad(rs.getString("quantity"));
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error al obtener el producto.", e);
		}
		return product;
	}

	public List<String> readCategories() {
		List<String> categories = new ArrayList<>();
		String sql = "SELECT * FROM category ORDER BY category";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				categories.add(rs.getString("category"));
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error al obtener el Categorias.", e);
		}
		return categories;
	}

	public List<String> readUnits() {
		List<String> units = new ArrayList<>();
		String sql = "SELECT * FROM Units ORDER BY unit";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				units.add(rs.getString("unit"));
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error al obtener el Unidades.", e);
		}
		return units;
	}

	public List<String> readPresentations() {
		List<String> presentations = new ArrayList<>();
		String sql = "SELECT * FROM presentation ORDER BY presentation";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				presentations.add(rs.getString("presentation"));
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error al obtener el Presentaciones.", e);
		}
		return presentations;
	}

	public List<String> readLocation() {
		List<String> locations = new ArrayList<>();
		String sql = "SELECT * FROM location ORDER BY location";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				locations.add(rs.getString("location"));
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error al obtener el ubicaciones.", e);
		}
		return locations;
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
			logger.log(Level.SEVERE, "Error al obtener el productos.", e);
		}

		return products;
	}

	///////////////////
	//////UPDATE///////
	///////////////////
	@Override
	public void update(Product updatedProduct) {
		String sql = "UPDATE Catalogue SET barcode = ?,name =?,brand =?,content =?,minStock =?,maxStock =?,description =?,available = ?,img = ?,registerDate = ?,Category_idCategory = ?,Presentation_idPresentation = ?,Units_idUnits = ?,Location_idLocation = ? WHERE barcode = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			attributesSet(updatedProduct, ps);
			ps.setString(15, updatedProduct.getBarcode());

			ps.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error al actualizar el producto.", e);
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
			logger.log(Level.SEVERE, "Error al eliminar el producto.", e);
		}
	}

	public boolean deleteOptions(String table, String idColumn, String nameColumn, String optionName) {
		int id = getIdByName(table, idColumn, nameColumn, optionName);
		System.out.println("PRODUCTSDAO 250 id: " + id);
		String sql = switch (table) {
			case "category" -> "DELETE FROM category WHERE idCategory = ?";
			case "location" -> "DELETE FROM location WHERE  idLocation = ?";
			case "presentation" -> "DELETE FROM presentation WHERE idPresentation = ?";
			case "units" -> "DELETE FROM units WHERE  idUnits = ?";
			default -> null;
		};
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, String.valueOf(id));
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error al eliminar Opciones", e);
		}
		return false;
	}

	public void resetConfig() {
		try (Statement ps = connection.createStatement()) {
			ps.executeUpdate("DELETE FROM category WHERE idCategory > 7;");
			ps.executeUpdate("DELETE FROM location WHERE idLocation > 5;");
			ps.executeUpdate("DELETE FROM presentation WHERE idPresentation > 7;");
			ps.executeUpdate("DELETE FROM units WHERE idUnits > 5;");
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "No se pudo completar resetConfig()", e);
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
		ps.setInt(11, getIdByName("category", "idCategory", "category", product.getCategory()));
		ps.setInt(12, getIdByName("presentation", "idPresentation", "presentation", product.getPresentation()));
		ps.setInt(13, getIdByName("Units", "idUnits", "unit", product.getUnits()));
		ps.setInt(14, getIdByName("location", "idLocation", "location", product.getProductLocation()));
	}

	private int getIdByName(String table, String idColumn, String nameColumn, String nameValue) {
		String sql = "SELECT " + idColumn + " FROM " + table + " WHERE " + nameColumn + " = ?";
		System.out.println("QUERY: 299 " + nameValue + ", " + sql);
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, nameValue);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(idColumn);
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error al obtener el id de opciones", e);
		}
		return -1;
	}
}
