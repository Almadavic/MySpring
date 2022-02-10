package br.com.app.readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.app.models.Product;

public class ReadProducts {

	private String path;

	public ReadProducts(String path) {
		this.path = path;
	}

	public List<Product> products() {
		List<Product> products = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			while (line != null) {
				String split[] = line.split(",");
				String name = split[0];
				String description = split[1];
				Double price = Double.parseDouble(split[2]);
				products.add(new Product(name, description, price));
				line = br.readLine();
			}

		} catch (IOException e) {
			System.out.println("Erro : " + e.getMessage());
		}

		return products;

	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
