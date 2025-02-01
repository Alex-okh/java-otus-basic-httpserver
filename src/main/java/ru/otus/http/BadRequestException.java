package ru.otus.http;

public class BadRequestException extends RuntimeException {
  private String code;
  private String description;

  public BadRequestException(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }
}
