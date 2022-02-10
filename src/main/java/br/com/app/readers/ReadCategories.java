package br.com.app.readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.app.models.Category;

public class ReadCategories {

	private String path;

	public ReadCategories(String path) {
		this.path = path;
	}

	public List<Category> categories() {
		List<Category> categories = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			while (line != null) {
				String split[] = line.split(",");
				String name = split[0];
				categories.add(new Category(name));
				line = br.readLine();
			}

		} catch (IOException e) {
			System.out.println("Erro : " + e.getMessage());
		}

		return categories;

	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
