package ru.otus.http.processors.product;

import com.google.gson.Gson;
import ru.otus.http.HttpRequest;
import ru.otus.http.application.Product;
import ru.otus.http.application.ProductsService;
import ru.otus.http.processors.RequestProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CreateProductProcessor implements RequestProcessor {
  ProductsService productsService;

  public CreateProductProcessor(ProductsService productsService) {
    this.productsService = productsService;
  }

  @Override
  public void process(HttpRequest request, OutputStream output) throws IOException {
    Gson gson = new Gson();
    Product newProduct = gson.fromJson(request.getBody(),Product.class);
productsService.createNewProduct(newProduct);

    String response = """
                  HTTP/1.1 201 OK
                  Content-Type: application/json
                   
                   <html>
                      <body>
                         OK
                      </body>
                   </html> 
                   """;
    output.write(response.getBytes(StandardCharsets.UTF_8));
  }
}
