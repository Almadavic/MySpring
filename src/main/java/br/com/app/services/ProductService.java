package br.com.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.app.models.Product;
import br.com.app.repositories.ProductRepository;

@Service
public class ProductService implements CrudService<Product> {
	
	@Autowired
	private ProductRepository repository;

	@Override
	public void insert(Product product) {
		repository.save(product);
		System.out.println("Produto "+product.getId()+" : "+product.getName()+" salvo");
	}

	@Override
	public List<Product> findAll() {
		return repository.findAll();
	}

	@Override
	public Product findById(Integer id) {
		Optional<Product> obj = repository.findById(id);
		try {
			return obj.orElseThrow(()-> new Exception());
		} catch (Exception e) {
			System.out.println("Erro : Id inválido");
		}
		return null;
	}

	@Override
	public void update(Integer id, Product product) {
		Product newProduct = new Product(id,product.getName(),
		        product.getDescription(),product.getPrice(),null);
		 
		repository.save(newProduct);
		System.out.println("Atualizado para : "+newProduct.getName());
	}

	@Override
	public void delete(Integer id) {
	Product newProduct =null;
		try {
		Optional<Product> product = repository.findById(id);
		newProduct = product.orElseThrow(()->new Exception());
		} catch (Exception e) {
			System.out.println("Erro : Id inválido");
		}
		
		repository.deleteById(id);
		
		if(newProduct!=null) {
			System.out.println("Produto : "+newProduct.getName()+" deletado");
		}
	}
	
	public List<Product> findByName(String name) {
		return repository.findByName(name);
	}
	
	public List<Product> findNomeDescricaoPrice(String name, String description, Double price) {
		return repository.findNameDescriptionPrice(name, description, price);
	}
	public List<Product> expensiverThan(double price) {
		return repository.expensiverThan(price);
	}
	
	public void page(Integer page) {

		
		//--------PAGINAÇÃO-----------\\
		Pageable pageable = PageRequest.of((page-1), 5, Sort.by(Sort.Direction.ASC, "id"));  // número da pag q o cliente quer,
		//divisão de registros(linhas) por pagina e ordenação da pagina
		Page<Product> products = repository.findAll(pageable); // vai ser uma pagina de funcionarios

		System.out.println(products); // Mostrar o número de paginas
		System.out.println("Página atual : "+(products.getNumber()+1)); // pagina atual
		System.out.println("Total elementos : "+products.getTotalElements()); // quantidade de registros totais(todas as paginas)
		//--------PAGINAÇÃO-----------\\
		products.forEach(System.out::println); // Mostra os elementos da página escolhida
	}

}
