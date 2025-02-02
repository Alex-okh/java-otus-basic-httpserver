package ru.otus.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.http.application.ProductsService;
import ru.otus.http.processors.*;
import ru.otus.http.processors.product.CreateProductProcessor;
import ru.otus.http.processors.product.DeleteProductProcessor;
import ru.otus.http.processors.product.GetProductProcessor;
import ru.otus.http.processors.product.PutProductProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
  private final Map<String, RequestProcessor> router;
  private static final Logger logger = LogManager.getLogger(Dispatcher.class.getName());
  ProductsService productsService;
  RequestProcessor welcomeProcessor;
  RequestProcessor calcProcessor;
  RequestProcessor getProductsProcessor;
  RequestProcessor createProductsProcessor;
  RequestProcessor default500Processor;
  RequestProcessor default400Processor;
  RequestProcessor deleteProductProcessor;
  RequestProcessor putProductProcessor;

  public Dispatcher() {

    productsService = new ProductsService();
    default500Processor = new Default500Processor();
    default400Processor = new Default400Processor();
    calcProcessor = new CalcProcessor();
    welcomeProcessor = new WelcomeProcessor();
    getProductsProcessor = new GetProductProcessor(productsService);
    createProductsProcessor = new CreateProductProcessor(productsService);
    deleteProductProcessor = new DeleteProductProcessor(productsService);
    putProductProcessor = new PutProductProcessor(productsService);

    router = new HashMap<>();
    router.put("GET /calc", calcProcessor);
    router.put("GET /welcome", welcomeProcessor);
    router.put("GET /products", getProductsProcessor);
    router.put("POST /products", createProductsProcessor);
    router.put("DELETE /products", deleteProductProcessor);
    router.put("PUT /products", putProductProcessor);



  }

  public void execute(HttpRequest request, OutputStream output) throws IOException {
    try {
      router.getOrDefault(request.getRoutingKey(), new Default404Processor()).process(request, output);
    } catch (BadRequestException e) {
      logger.warn(e);
      request.setErrorCause(e);
      default400Processor.process(request, output);
    } catch (Exception e) {
      logger.error(e);
      default500Processor.process(request, output);

    }
  }
}
