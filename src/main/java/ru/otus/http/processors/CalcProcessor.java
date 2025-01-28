package ru.otus.http.processors;

import ru.otus.http.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CalcProcessor implements RequestProcessor {
  @Override
  public void process(HttpRequest request, OutputStream output) throws IOException {
    int a = Integer.parseInt(request.getParameter("a"));
    int b = Integer.parseInt(request.getParameter("b"));
    String result = a + " + " + b + " = " + (a + b);

    String response = """
                  HTTP/1.1 200 OK
                  "Content-Type: text/html
                  
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
