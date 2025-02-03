package ru.otus.http.processors.product;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.http.BadRequestException;
import ru.otus.http.HttpRequest;
import ru.otus.http.application.Product;
import ru.otus.http.application.ProductsService;
import ru.otus.http.processors.RequestProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class PutProductProcessor implements RequestProcessor {
  private static final Logger logger = LogManager.getLogger(PutProductProcessor.class.getName());
  private ProductsService productsService;

  public PutProductProcessor(ProductsService productsService) {
    this.productsService = productsService;
  }

  @Override
  public void process(HttpRequest request, OutputStream output) throws IOException {
    Gson gson = new Gson();
    Product newProduct;
    try {
      newProduct = gson.fromJson(request.getBody(), Product.class);
    } catch (JsonSyntaxException e) {
      logger.warn(e);
      throw new BadRequestException("JSON_ERROR", "Could not parse product info.");
    }
    long productId;
    try {
      productId = Long.parseLong(request.getParameter("id"));
    } catch (NumberFormatException e) {
      logger.warn(e);
      throw new BadRequestException("ID_ERROR", "Bad product ID parameter sent.");
    }
    newProduct.setId(productId);
    productsService.updateProduct(newProduct);

    String response = """
            HTTP/1.1 202 Accepted
            Content-Type: application/json
             
             <html>
                <body>
                   UPDATED
                </body>
             </html> 
             """;
    output.write(response.getBytes(StandardCharsets.UTF_8));
  }
}
