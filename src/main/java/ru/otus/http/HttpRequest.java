package ru.otus.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
  private static final Logger logger = LogManager.getLogger(HttpRequest.class.getName());
  private final String rawRequest;
  private HttpMethod method;
  private String uri;
  private Map<String, String> parameters;
  private Map<String, String> headers;
  private String body;
  private Exception errorCause;

  public HttpRequest(String rawRequest) {
    this.rawRequest = rawRequest;
    parseRequest();

  }

  public Exception getErrorCause() {
    return errorCause;
  }

  public void setErrorCause(Exception errorCause) {
    this.errorCause = errorCause;
  }

  public HttpMethod getMethod() {
    return method;
  }

  public String getUri() {
    return uri;
  }

  public String getParameter(String key) {
    return parameters.get(key);
  }

  public String getBody() {
    return body;
  }

  public boolean hasParameter(String key) {
    return parameters.containsKey(key);
  }

  public String getRoutingKey() {
    return method + " " + uri;
  }

  private void parseRequest() {
    parameters = new HashMap<>();
    headers = new HashMap<>();
    int startIndex = rawRequest.indexOf(" ");
    int endIndex = rawRequest.indexOf(" ", startIndex + 1);
    try {
      method = HttpMethod.valueOf(rawRequest.substring(0, startIndex));
    } catch (IllegalArgumentException e) {
      method = null;
      logger.warn("Invalid HTTP method: " + rawRequest);
    }
    uri = rawRequest.substring(startIndex + 1, endIndex);

    if (uri.contains("?")) {
      try {
        String[] parts = uri.split("\\?");
        uri = parts[0];
        String[] paramsPairs = parts[1].split("&");
        for (String pair : paramsPairs) {
          String[] keyValue = pair.split("=");
          parameters.put(keyValue[0], keyValue[1]);
        }
      } catch (Exception e) {
        logger.warn("Invalid request: %s".formatted(uri));

      }

    } rawRequest.lines().skip(1).takeWhile(s -> !s.isBlank()).forEach(line -> {
      String[] keyValue = line.split(": ");
      headers.put(keyValue[0], keyValue[1]);
    });
    body = rawRequest.substring(rawRequest.indexOf("\r\n\r\n") + 4, rawRequest.length());
  }


  public void info(boolean showRawRequest) {
    logger.info("method: %s , uri: %s , HEADERS: %s , BODY: %s ".formatted(method, uri, headers, body));
    if (showRawRequest) {
      logger.info("rawRequest: " + rawRequest);

    }
  }
}
