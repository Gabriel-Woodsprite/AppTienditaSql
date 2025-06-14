package org.example.apptienditasql.dao;


import org.example.apptienditasql.interfaces.DaoInterface;
import org.example.apptienditasql.model.Sale;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

public class SalesDao implements DaoInterface<Sale, String> {
	Logger logger = Logger.getLogger(SalesDao.class.getName());

	private final Connection connection;

	public SalesDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(Sale entity) {

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
}
