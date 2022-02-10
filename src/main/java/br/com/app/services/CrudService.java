package br.com.app.services;

import java.util.List;

public interface CrudService<T> {

	void insert(T object);

	List<T> findAll();

	T findById(Integer id);

	void update(Integer id,T obj);

	void delete(Integer id);

}
