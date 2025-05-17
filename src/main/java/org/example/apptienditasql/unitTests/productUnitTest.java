package org.example.apptienditasql.unitTests;

import org.example.apptienditasql.controller.ProductsController;
import org.example.apptienditasql.dao.ProductsDao;
import org.example.apptienditasql.model.Product;
import org.example.apptienditasql.utils.DatabaseConnection;

public class productUnitTest {
	public static void main(String[] args) throws Exception {

		ProductsController controller = new ProductsController(new ProductsDao(DatabaseConnection.getConnection()));

		///////////////////
		//////CREAR////////
		///////////////////
//		controller.createProduct(new Product());

		//////////////////////////
		//////CONSULTAR TODOS/////
		//////////////////////////
		controller.readAllProducts().forEach(System.out::println);

		/////////////////////////////
		//////Consultar Producto/////
		/////////////////////////////
//		System.out.println(controller.readProduct("667788990011200"));

		/////////////////////////////
		//////Eliminar Producto/////
		/////////////////////////////
//		controller.deleteProduct("667788990011200");
//		controller.readAllProducts().forEach(System.out::println);

		/////////////////////////////
		//////Modificar Producto/////
		/////////////////////////////
//		controller.updateProduct(new Product());
	}
}