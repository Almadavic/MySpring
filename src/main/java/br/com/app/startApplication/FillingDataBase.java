package br.com.app.startApplication;

import java.util.List;

import br.com.app.models.Category;
import br.com.app.models.Product;
import br.com.app.readers.ReadCategories;
import br.com.app.readers.ReadProducts;
import br.com.app.repositories.CategoryRepository;
import br.com.app.repositories.ProductRepository;

public class FillingDataBase {
	
	private ProductRepository productRepository;
	private CategoryRepository categoryRepository;
	
	public FillingDataBase(ProductRepository proRepo,CategoryRepository proRe) {
		this.categoryRepository=proRe;
		this.productRepository=proRepo;
	}
	
	public List<Product> products() {
		ReadProducts rp = new ReadProducts("c:\\temp\\productsSpring.csv");
		return rp.products();
	}

	public List<Category> categories() {
		ReadCategories rp = new ReadCategories("c:\\temp\\categoriesSpring.csv");
		return rp.categories();
	}
	
	
	public void fillDataBase() {
		
		List<Product>products = products();
		List<Category>categories = categories();
		
		for(int i =0 ;i<products.size();i++) {
			if(products.get(i).getPrice()<=500) {
				products.get(i).setCategory(categories.get(0));
				categories.get(0).addProduct(products.get(i));
			} else if(products.get(i).getPrice()>500 && products.get(i).getPrice()<1000) {
				products.get(i).setCategory(categories.get(1));
				categories.get(1).addProduct(products.get(i));
			} else {
				products.get(i).setCategory(categories.get(2));
				categories.get(2).addProduct(products.get(i));
			}
		}
		
		categoryRepository.saveAll(categories);		
		productRepository.saveAll(products);

		
		
	}

}
