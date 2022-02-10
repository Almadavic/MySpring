package br.com.app.functionMethods;

import java.util.Comparator;

import br.com.app.models.Product;

public class MyComparator implements Comparator<Product> {

	@Override
	public int compare(Product p1, Product p2) {
		return p1.getPrice().compareTo(p2.getPrice());
	}

}
