package ru.otus.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
  private final int port;
  private final Dispatcher dispatcher;


  public HttpServer(int port) {
    this.port = port;
    dispatcher = new Dispatcher();


  }

  public void start() {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.println("Server started on port " + port);
      try (Socket socket = serverSocket.accept()) {
        System.out.println("Accepted connection from " + socket.getRemoteSocketAddress());
        byte[] buffer = new byte[8192];
        int n = socket.getInputStream().read(buffer);
        String rawRequest = new String(buffer, 0, n);
        HttpRequest request = new HttpRequest(rawRequest);
        request.info(false);
        dispatcher.execute(request, socket.getOutputStream());
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}

