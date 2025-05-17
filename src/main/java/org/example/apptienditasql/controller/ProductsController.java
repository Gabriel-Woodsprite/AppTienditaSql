package org.example.apptienditasql.controller;

import org.example.apptienditasql.dao.ProductsDao;
import org.example.apptienditasql.model.Product;

import java.util.List;

public class ProductsController {
	private ProductsDao dao;

	public ProductsController(ProductsDao dao) {
		this.dao = dao;
	}

	public void createProduct(Product product) throws Exception {
		dao.create(product);
	}

	public Product readProduct(String barcode) throws Exception {
		return dao.read(barcode);
	}

	public List<Product> readAllProducts() throws Exception {
		return dao.readAll();
	}

	public void updateProduct(String barcode, Product product) throws Exception {
		dao.update(barcode, product);
	}

	public void deleteProduct(String barcode) throws Exception {
		dao.delete(barcode);
	}
}
