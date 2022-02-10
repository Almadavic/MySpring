package br.com.app;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.app.functionMethods.MyComparator;
import br.com.app.models.Category;
import br.com.app.models.Product;
import br.com.app.repositories.CategoryRepository;
import br.com.app.repositories.ProductRepository;
import br.com.app.services.CategoryService;
import br.com.app.services.ProductService;
import br.com.app.startApplication.FillingDataBase;

@SpringBootApplication
public class MySpringApplication implements CommandLineRunner {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;

	public static String mainMenu() {
		StringBuilder bd = new StringBuilder();
		bd.append("------MAIN-----\n");
		bd.append("1-Produtos\n");
		bd.append("2-Categorias\n");
		bd.append("3-Sair\n");
		bd.append("------MAIN-----");
		return bd.toString();
	}

	public static String menuProduct() {
		StringBuilder bd = new StringBuilder();
		bd.append("---PRODUTO---\n");
		bd.append("1-Insere PRODUTO \n");
		bd.append("2-Encontre todos os PRODUTOS\n");
		bd.append("3-Procure PRODUTOS por Id\n");
		bd.append("4-Atualize PRODUTOS\n");
		bd.append("5-Delete PRODUTOS\n");
		bd.append("6-Encontrar PRODUTO por nome\n");
		bd.append("7-Pesquisar PRODUTO por nome,descrição e preço \n");
		bd.append("8-Preços mais caros que (Valor)\n");
		bd.append("9-Ver PRODUTOS paginados\n");
		bd.append("10-Sair\n");
		bd.append("---PRODUTO---\n");
		return bd.toString();
	}

	public static String menuCategory() {
		StringBuilder bd = new StringBuilder();
		bd.append("---CATEGORY---\n");
		bd.append("1- Todas as CATEGORIAS \n");
		bd.append("2- Todos os produtos de uma CATEGORIA especifica\n");
		bd.append("3- Alterar o nome de uma categoria\n");
		bd.append("4- Sair\n");
		bd.append("---CATEGORY---\n");
		return bd.toString();
	}

	public void fillingDataBase() {
		FillingDataBase filling = new FillingDataBase(productRepository, categoryRepository);
		filling.fillDataBase();
	}

	public static void main(String[] args) {
		SpringApplication.run(MySpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Scanner sc = new Scanner(System.in);
		String wrongInput = "Digito incorreto, digite um comando verdadeiro para continuar!";
		// -----------POUPULANDO O BANCO DE DADOS------------\\
		fillingDataBase();
		// -----------POUPULANDO O BANCO DE DADOS------------\\

		System.out.println(mainMenu());
		int optionMain = sc.nextInt();
		while (optionMain != 3) {
			if (optionMain == 1) {

				System.out.println(menuProduct());
				int option = sc.nextInt();
				while (option != 10) {
					switch (option) {
					case 1:
						System.out.println("Digite o nome do Produto : ");
						String name = sc.next();
						System.out.println("Digite a descrição : ");
						String description = sc.next();
						System.out.println("Digite o preço : ");
						Double price = sc.nextDouble();
						Product product = new Product(name, description, price);
						if (product.getPrice() <= 500) {
							product.setCategory(categoryService.findById(1));
						} else if (product.getPrice() > 500 && product.getPrice() < 1000) {
							product.setCategory(categoryService.findById(2));
						} else {
							product.setCategory(categoryService.findById(3));
						}
						productService.insert(product);
						break;
					case 2:
						List<Product> products = productService.findAll();
						System.out.println("");
						products.forEach(System.out::println);
						System.out.println("");
						break;
					case 3:
						System.out.println("Insira um id : ");
						Integer id = sc.nextInt();
						Product productSearch = productService.findById(id);
						System.out.println(productSearch);
						break;
					case 4:
						System.out.println("Insira um id para atualizar : ");
						Integer idAtt = sc.nextInt();
						System.out.println("Digite o nome do Produto : ");
						String nameAtt = sc.next();
						System.out.println("Digite a descrição : ");
						String descriptionAtt = sc.next();
						System.out.println("Digite o preço : ");
						Double priceAtt = sc.nextDouble();
						productService.update(idAtt, new Product(nameAtt, descriptionAtt, priceAtt));
						break;
					case 5:
						System.out.println("Insira um id para deletar : ");
						Integer idDel = sc.nextInt();
						productService.delete(idDel);
						break;
					case 6:
						System.out.println("Digite o nome para pesquisar : ");
						String nameProducts = sc.next();
						List<Product> productsByName = productService.findByName(nameProducts);
						productsByName.forEach(System.out::println);
						break;

					case 7:
						System.out.println("Digite o nome do Produto : ");
						String nameSearch = sc.next();
						System.out.println("Digite a descrição : ");
						String descriptionSeatch = sc.next();
						System.out.println("Digite o preço : ");
						Double priceSeatch = sc.nextDouble();
						List<Product> productsList = productService.findNomeDescricaoPrice(nameSearch, descriptionSeatch,
								priceSeatch);
						productsList.forEach(System.out::println);
						break;
					case 8:
						System.out.println("Digite um valor : ");
						double value = sc.nextDouble();
						List<Product> productsListExpensive = productService.expensiverThan(value);
						productsListExpensive.forEach(System.out::println);
						break;
					case 9:
						System.out.println("Qual pagina deseja pesquisar ? ");
						productService.page(sc.nextInt());
						break;
					default:
						System.out.println(wrongInput);
						break;
					}

					System.out.println(menuProduct());
					option = sc.nextInt();
				}

			} else if (optionMain == 2) {
				List<Category> categories = categoryService.findAll();
				System.out.println(menuCategory());
				int option = sc.nextInt();
				while (option != 4) {
					switch (option) {

					case 1:
						categories.forEach(System.out::println);
						break;
					case 2:

						System.out.println("Escolha um id da categoria para ver os produtos associados : ");
						for (Category category : categories) {
							System.out.println(category);
						}
						System.out.println("");
						System.out.print("Digite o Id : ");
						int choiceId = sc.nextInt();
						Category category = categoryService.findById(choiceId);
						System.out.println("-------" + category.getName() + "-------");
						Collections.sort(category.getProducts(), new MyComparator());
						for (Product product : category.getProducts()) {
							System.out.println(product);
						}
						System.out.println("-------" + category.getName() + "-------");
						break;
					case 3:
						System.out.println("Digite o id da categoria que deseja alterar : ");
						int idToChange = sc.nextInt();
						System.out.println("Digite o nome da nova categoria : ");
						String nameToChange = sc.next();
						Category newCategory = new Category(nameToChange);
						categoryService.update(idToChange, newCategory);
						break;
					default:
						System.out.println(wrongInput);
						break;

					}

					System.out.println(menuCategory());
					option = sc.nextInt();
				}

			} else {
				System.out.println(wrongInput);
			}

			System.out.println(mainMenu());
			optionMain = sc.nextInt();

		}

		sc.close();

		System.out.println("Programa Finalizado");
	}

}
