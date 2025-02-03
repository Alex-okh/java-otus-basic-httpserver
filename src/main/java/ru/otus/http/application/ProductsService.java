package ru.otus.http.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProductsService {
  private List<Product> products;

  public ProductsService() {
    this.products = new ArrayList<>(Arrays.asList(new Product(1L, "Milk"), new Product(2L, "Bread"), new Product(3L, "Cheese"), new Product(4L, "Meat")));
  }

  public List<Product> getAllProducts() {
    return Collections.unmodifiableList(products);
  }

  public Product getProductByID(long id) {
    return products.stream().filter(product -> product.getId() == id).findFirst().orElse(null);
  }

  public void createNewProduct(Product product) {
    long newId = products.stream().mapToLong(Product::getId).max().getAsLong() + 1;
    products.add(new Product(newId, product.getName()));
  }

  public boolean deleteProduct(long id) {
    return products.removeIf(product -> product.getId() == id);
  }

  public void deleteAllProducts() {
    products.clear();
  }

  public void updateProduct(Product product) {
    for (int i = 0; i < products.size(); i++) {
      if (products.get(i).getId() == product.getId()) {
        products.set(i, product);
        return;
      }
    }
    createNewProduct(product);
  }
}
