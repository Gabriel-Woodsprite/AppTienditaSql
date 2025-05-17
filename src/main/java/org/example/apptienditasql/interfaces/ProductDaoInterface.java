package org.example.apptienditasql.interfaces;

import org.example.apptienditasql.model.Product;

import java.util.List;

public interface ProductDaoInterface {
	void create(Product product);

	Product read(String barcode);

	List<Product> readAll();

	void update(Product updatedProduct);

	void delete(String barcode);
}
