package ru.otus.http;

import com.google.gson.Gson;
import ru.otus.http.application.Product;

public class Application {
  public static void main(String[] args) {
    HttpServer server = new HttpServer(8189);
    server.start();

    Product product = new Product(10L, "SGUSHCHENKA");
    Gson gson = new Gson();
    String result = gson.toJson(product);
    System.out.println(result);

    String example = "{\"id\":11,\"name\":\"VARENAYA SGUSHCHENKA\"}";
    Product product2 = gson.fromJson(example, Product.class);
    System.out.println(product2.getId());
    System.out.println(product2.getName());
  }
}