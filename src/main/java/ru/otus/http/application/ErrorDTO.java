package ru.otus.http.application;

import java.time.LocalDateTime;

public class ErrorDTO {
  private String code;
  private String description;
  private String timestamp;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public ErrorDTO(String code, String description) {
    this.code = code;
    this.description = description;
    this.timestamp = LocalDateTime.now().toString();
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTimestamp() {
    return timestamp;
  }


}
