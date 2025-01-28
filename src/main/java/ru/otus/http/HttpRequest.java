package ru.otus.http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
  private final String rawRequest;

  private String method;
  private String uri;
  private Map<String, String> parameters;


  public HttpRequest(String rawRequest) {
    this.rawRequest = rawRequest;
    parseRequest();

  }

  public String getMethod() {
    return method;
  }

  public String getUri() {
    return uri;
  }

  public String getParameter(String key) {
    return parameters.get(key);
  }

  private void parseRequest() {
    parameters = new HashMap<>();
    int startIndex = rawRequest.indexOf(" ");
    int endIndex = rawRequest.indexOf(" ", startIndex+1);
    method = rawRequest.substring(0, startIndex);
    uri = rawRequest.substring(startIndex+1, endIndex);

    if (uri.contains("?")) {
      String[] parts = uri.split("\\?");
      uri = parts[0];
      String[] paramsPairs = parts[1].split("&");
      for (String pair : paramsPairs) {
        String[] keyValue = pair.split("=");
        parameters.put(keyValue[0], keyValue[1]);
      }

    }
  }

  public void info(boolean showRawRequest) {
    System.out.println("method: " + method);
    System.out.println("uri: " + uri);
    if (showRawRequest) {
      System.out.println("rawRequest: " + rawRequest);
    }
  }
}
