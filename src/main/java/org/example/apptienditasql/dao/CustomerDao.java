package org.example.apptienditasql.dao;

import org.example.apptienditasql.interfaces.CostumerDaoInterface;
import org.example.apptienditasql.model.Costumer;

import java.util.List;

public class CustomerDao implements CostumerDaoInterface {

	@Override
	public void create(Costumer product) throws Exception {
		
	}

	@Override
	public Costumer read(String costumerID) throws Exception {
		return null;
	}

	@Override
	public List<Costumer> readAll() throws Exception {
		return List.of();
	}

	@Override
	public void update(String costumerID, Costumer updatedProduct) throws Exception {

	}

	@Override
	public void delete(String costumerID) throws Exception {

	}
}
