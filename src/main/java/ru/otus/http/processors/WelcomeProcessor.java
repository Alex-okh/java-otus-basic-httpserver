package ru.otus.http.processors;

import ru.otus.http.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class WelcomeProcessor implements RequestProcessor {
  @Override
  public void process(HttpRequest request, OutputStream output) throws IOException {
    String response = """
                  HTTP/1.1 200 OK
                  Content-Type: text/html
                   
                   <html>
                      <body>
                         <h1>
                          Welcome page
                         </h1>
                      </body>
                   </html> 
                   """;
    output.write(response.getBytes(StandardCharsets.UTF_8));
  }
}
