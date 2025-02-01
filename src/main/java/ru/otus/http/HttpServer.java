package ru.otus.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
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
      System.out.println("Server started on port " + port);
      while (true) {
        try {
          Socket socket = serverSocket.accept();
          System.out.println("Accepted connection from " + socket.getRemoteSocketAddress());
          executor.execute(() -> {
            try {
              byte[] buffer = new byte[8192];
              int n = socket.getInputStream().read(buffer);
              String rawRequest = (n < 0) ? "" : new String(buffer, 0, n);
              HttpRequest request = new HttpRequest(rawRequest);
              System.out.println(Thread.currentThread().getName() + ": ");
              request.info(false);
              dispatcher.execute(request, socket.getOutputStream());
            } catch (IOException e) {
              e.printStackTrace();
            } finally {
              try {
                socket.close();
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          });
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          executor.shutdown();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}