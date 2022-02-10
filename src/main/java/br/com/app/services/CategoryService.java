package br.com.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.app.models.Category;
import br.com.app.repositories.CategoryRepository;

@Service
public class CategoryService implements CrudService<Category> {

	@Autowired
	private CategoryRepository repository;

	@Override
	public void insert(Category category) {
		repository.save(category);
		System.out.println("Categoria : " + category.getId() + " : " + category.getName() + " salva");
	}

	@Override
	public List<Category> findAll() {
		return repository.findAll();
	}

	@Override
	public Category findById(Integer id) {
		Optional<Category> category = repository.findById(id);
		try {
			return category.orElseThrow(() -> new Exception());
		} catch (Exception e) {
			System.out.println("Erro : id não encontrado");
		}
		return null;
	}

	@Override
	public void update(Integer id, Category category) {
		Category newCategory = new Category(id, category.getName());
		repository.save(newCategory);
	}

	@Override
	public void delete(Integer id) {
		Category newCategory = null;
		try {
			Optional<Category> category = repository.findById(id);
			newCategory = category.orElseThrow(() -> new Exception());
		} catch (Exception e) {
			System.out.println("Erro : Id não encontrado");
		}

		repository.deleteById(id);
		System.out.println("Categoria : " + newCategory.getId() + " : " + newCategory.getName() + " deletada");
	}

}
