package org.example.apptienditasql.interfaces;

import java.util.List;

public interface ProductDaoInterface {
	void create(org.example.apptienditasql.model.Product product) throws Exception;

	org.example.apptienditasql.model.Product read(String barcode) throws Exception;

	List<org.example.apptienditasql.model.Product> readAll() throws Exception;

	void update(String barcode, org.example.apptienditasql.model.Product updatedProduct) throws Exception;

	void delete(String barcode) throws Exception;
}
