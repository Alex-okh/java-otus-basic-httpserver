package ru.otus.http.processors.product;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.http.BadRequestException;
import ru.otus.http.HttpRequest;
import ru.otus.http.application.ProductsService;
import ru.otus.http.processors.RequestProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DeleteProductProcessor implements RequestProcessor {
  public static final Logger logger = LogManager.getLogger(DeleteProductProcessor.class.getName());
  private ProductsService productsService;

  public DeleteProductProcessor(ProductsService productsService) {
    this.productsService = productsService;
  }

  @Override
  public void process(HttpRequest request, OutputStream output) throws IOException {
    boolean deleteResult = false;
    String idToDelete = request.getParameter("id");
    if (idToDelete.equals("all")) {
      productsService.deleteAllProducts();
    } else {
     try {
       deleteResult = productsService.deleteProduct(Long.parseLong(idToDelete));
     } catch (NumberFormatException e) {
       logger.warn(e);
       throw new BadRequestException("BAD_ID", "ID is not a number");
     }
    }


    String response = """
            HTTP/1.1 202 Accepted
            Content-Type: application/json
             
             <html>
                <body>
                   %s
                </body>
             </html> 
             """.formatted(idToDelete.equals("all") ? "Все товары удалены" : deleteResult ? "Товар удален" : "Товар не найден");
    output.write(response.getBytes(StandardCharsets.UTF_8));
  }
}
