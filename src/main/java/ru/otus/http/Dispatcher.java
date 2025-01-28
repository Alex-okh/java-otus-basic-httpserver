package ru.otus.http;

import ru.otus.http.processors.CalcProcessor;
import ru.otus.http.processors.Default404Processor;
import ru.otus.http.processors.RequestProcessor;
import ru.otus.http.processors.WelcomeProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
  private final Map<String, RequestProcessor> router;
  RequestProcessor welcomeProcessor;
  RequestProcessor calcProcessor;

  public Dispatcher() {
    calcProcessor = new CalcProcessor();
    welcomeProcessor = new WelcomeProcessor();
    router = new HashMap<>();
    router.put("/calc", calcProcessor);
    router.put("/welcome", welcomeProcessor);


  }

  public void execute(HttpRequest request, OutputStream output) throws IOException {
    router.getOrDefault(request.getUri(), new Default404Processor()).process(request, output);
  }
}
