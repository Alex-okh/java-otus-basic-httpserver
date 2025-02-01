package ru.otus.http.processors;

import com.google.gson.Gson;
import ru.otus.http.HttpRequest;
import ru.otus.http.application.Product;
import ru.otus.http.application.ProductsService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GetProductProcessor implements RequestProcessor {
  private ProductsService productsService;

  public GetProductProcessor(ProductsService productsService) {
    this.productsService = productsService;
  }

  @Override
  public void process(HttpRequest request, OutputStream output) throws IOException {
    Gson gson = new Gson();
    String jsonReply ="";

    if (request.hasParameter("id")) {
      Long id = Long.parseLong(request.getParameter("id"));
      Product product = productsService.getProductByID(id);
      jsonReply = gson.toJson(product);
    } else {
    List<Product> products = productsService.getAllProducts();
    jsonReply = gson.toJson(products);}

    String response = """
            HTTP/1.1 200 OK
            Content-Type: application/json
                              
            %s
            """.formatted(jsonReply);
    output.write(response.getBytes(StandardCharsets.UTF_8));
  }
}
