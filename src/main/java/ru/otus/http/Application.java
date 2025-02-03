package ru.otus.http;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Application {
  private static final Logger logger = LogManager.getLogger(Application.class.getName());
  public static void main(String[] args) {
    logger.info("Starting.");
    HttpServer server = new HttpServer(8189);
    server.start();



  }
}