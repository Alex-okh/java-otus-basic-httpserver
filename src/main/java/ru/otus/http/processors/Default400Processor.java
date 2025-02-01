package ru.otus.http.processors;

import com.google.gson.Gson;
import ru.otus.http.BadRequestException;
import ru.otus.http.HttpRequest;
import ru.otus.http.application.ErrorDTO;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Default400Processor implements RequestProcessor {
  @Override
  public void process(HttpRequest request, OutputStream output) throws IOException {
    ErrorDTO err = new ErrorDTO(((BadRequestException) request.getErrorCause()).getCode(),
            ((BadRequestException) request.getErrorCause()).getDescription());
    Gson gson = new Gson();
    String json = gson.toJson(err);

    String response = """
                  HTTP/1.1 400 Bad Request
                  Content-Type: application/json
                  
                  %s
                  """.formatted(json);
    output.write(response.getBytes(StandardCharsets.UTF_8));
  }
}
