package br.com.app.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String description;
	private Double price;
	@ManyToOne()
	private Category category;

	public Product() {

	}

	public Product(String name, String description, Double price) {
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public Product(String name, String description, Double price, Category category) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
	}

	public Product(Integer id, String name, String description, Double price, Category category) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		StringBuilder bd = new StringBuilder();
		bd.append("Id : " + id);
		bd.append(", Nome : " + name);
		bd.append(", Descrição : " + description);
		bd.append(", Preço R$" + String.format("%.2f", price));
		bd.append(", Categoria : " + category.getName());
		return bd.toString();
	}

}
