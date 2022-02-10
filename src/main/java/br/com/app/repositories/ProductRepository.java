package br.com.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.app.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>,JpaSpecificationExecutor<Product> {
	

	
	// Derived Queries - Apenas por o nome do atributo (existente na entidade)
		List<Product> findByName(String nome);
		
		
		// Usando JPQL , Uma linguagem P.O.O + SQL
		@Query("SELECT f FROM Product f WHERE f.name = :name "
				+ "AND f.price = :price AND f.description = :description")
		List<Product> findNameDescriptionPrice(String name, String description, Double price);
		
		//Native Query , SQL PURO
		@Query(value = "SELECT * FROM products WHERE price >= :price",
				nativeQuery = true)
		List<Product> expensiverThan(Double price);
		


}
