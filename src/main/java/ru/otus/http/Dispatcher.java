package ru.otus.http;

import ru.otus.http.application.ProductsService;
import ru.otus.http.processors.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
  private final Map<String, RequestProcessor> router;
  ProductsService productsService;
  RequestProcessor welcomeProcessor;
  RequestProcessor calcProcessor;
  RequestProcessor getProductsProcessor;
  RequestProcessor createProductsProcessor;
  RequestProcessor default500Processor;
  RequestProcessor default400Processor;

  public Dispatcher() {

    productsService = new ProductsService();
    default500Processor = new Default500Processor();
    default400Processor = new Default400Processor();
    calcProcessor = new CalcProcessor();
    welcomeProcessor = new WelcomeProcessor();
    getProductsProcessor = new GetProductProcessor(productsService);
    createProductsProcessor = new CreateProductProcessor(productsService);

    router = new HashMap<>();
    router.put("GET /calc", calcProcessor);
    router.put("GET /welcome", welcomeProcessor);
    router.put("GET /products", getProductsProcessor);
    router.put("POST /products", createProductsProcessor);


  }

  public void execute(HttpRequest request, OutputStream output) throws IOException {
    try {
      router.getOrDefault(request.getRoutingKey(), new Default404Processor()).process(request, output);
    } catch (BadRequestException e) {
      e.printStackTrace();
      request.setErrorCause(e);
      default400Processor.process(request, output);
    } catch (Exception e) {
      e.printStackTrace();
      default500Processor.process(request, output);

    }
  }
}
