package ru.otus.http.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.http.BadRequestException;
import ru.otus.http.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CalcProcessor implements RequestProcessor {
  private static final Logger logger = LogManager.getLogger(CalcProcessor.class.getName());

  @Override
  public void process(HttpRequest request, OutputStream output) throws IOException {
    if (!request.hasParameter("a")) {
      logger.warn("No a parameter received.");
      throw new BadRequestException("VALIDATION_ERROR_MISSING_PARAMETER", "No a parameter received.");
    }
    if (!request.hasParameter("b")) {
      logger.warn("No b parameter received.");
      throw new BadRequestException("VALIDATION_ERROR_MISSING_PARAMETER", "No b parameter received.");
    }

    int a = Integer.parseInt(request.getParameter("a"));
    int b = Integer.parseInt(request.getParameter("b"));
    String result = a + " + " + b + " = " + (a + b);

    String response = """
                  HTTP/1.1 200 OK
                  Content-Type: text/html
                  
                   <html>
                      <body>
                         <h1>
                          %s
                         </h1>
                      </body>
                   </html>
                  """.formatted(result);
    output.write(response.getBytes(StandardCharsets.UTF_8));
  }
}
