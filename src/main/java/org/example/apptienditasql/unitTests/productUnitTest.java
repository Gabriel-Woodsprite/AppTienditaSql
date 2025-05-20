//package org.example.apptienditasql.unitTests;
//
//import org.example.apptienditasql.controller.ProductsController;
//import org.example.apptienditasql.dao.ProductsDao;
//import org.example.apptienditasql.model.Product;
//import org.example.apptienditasql.utils.DatabaseConnection;
//
//import java.time.LocalDate;
//import java.util.Date;
//
//public class productUnitTest {
//	public static void main(String[] args) throws Exception {
//
//		ProductsController controller = new ProductsController(new ProductsDao(DatabaseConnection.getConnection()));
//
//		///////////////////
//		//////CREAR////////
//		///////////////////
//		LocalDate today = LocalDate.now();
//		Product product = new Product("1008", "Papas","Sabritas","Snacks","240g","Gramos","10","30","Bolsa","Papas Sabritas ou yea",true,"img.jpg",today,"Pasillo 4",today);
////		controller.createProduct(product);
//
//		//////////////////////////
//		//////CONSULTAR TODOS/////
//		//////////////////////////
////		controller.readAllProducts().forEach(System.out::println);
//
//		/////////////////////////////
//		//////Consultar Producto/////
//		/////////////////////////////
//		System.out.println(controller.readProduct("1008"));
//
//		/////////////////////////////
//		//////Eliminar Producto/////
//		/////////////////////////////
////		controller.deleteProduct("1008");
////		controller.readAllProducts().forEach(System.out::println);
//
//		/////////////////////////////
//		//////Modificar Producto/////
//		/////////////////////////////
//		product.setBrand("Barcel");
//		product.setDescription("Papas ou yea si ou yea si");
//		controller.updateProduct(product);
//		System.out.println(controller.readProduct("1008"));
//	}
//}