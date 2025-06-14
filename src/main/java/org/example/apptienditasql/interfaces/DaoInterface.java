package org.example.apptienditasql.interfaces;

import org.example.apptienditasql.model.Product;

import java.util.List;

public interface DaoInterface<T, K> {
	void create(T entity);

	T read(K key);

	List<T> readAll();

	void update(T updatedEntity);

	void delete(K key);
}
