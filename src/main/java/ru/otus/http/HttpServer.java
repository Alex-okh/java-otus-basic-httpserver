package ru.otus.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
  private static final Logger logger = LogManager.getLogger(HttpServer.class.getName());
  private final int port;
  private final Dispatcher dispatcher;
  private final ExecutorService executor;


  public HttpServer(int port) {
    this.port = port;
    dispatcher = new Dispatcher();
    executor = Executors.newFixedThreadPool(10);
  }

  public void start() {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      logger.info("Server started on port " + port);
      try {
        while (true) {
          Socket socket = serverSocket.accept();
          logger.info("Accepted connection from " + socket.getRemoteSocketAddress());
          executor.execute(() -> {
            try {
              byte[] buffer = new byte[8192];
              int n = socket.getInputStream().read(buffer);
              if (n > 0) {
                String rawRequest = new String(buffer, 0, n);
                HttpRequest request = new HttpRequest(rawRequest);
                logger.info("Request is being handled by " + Thread.currentThread().getName());
                request.info(false);
                dispatcher.execute(request, socket.getOutputStream());
              }
            } catch (IOException e) {
              logger.error(e);
            } finally {
              try {
                socket.close();
              } catch (IOException e) {
                logger.error(e);
              }
            }

          });
        }
      } catch (IOException e) {
        logger.error(e);
      } finally {
        logger.info("Server stopped");
        executor.shutdown();
      }

    } catch (IOException e) {
      logger.error(e);
    }
  }
}

