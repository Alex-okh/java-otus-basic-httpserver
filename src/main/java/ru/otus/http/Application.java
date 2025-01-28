package ru.otus.http;

public class Application {
  public static void main(String[] args) {
    HttpServer server = new HttpServer(8189);
    server.start();

  }
}