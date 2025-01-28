package ru.otus.http.processors;

import ru.otus.http.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;

public interface RequestProcessor {
  void process(HttpRequest request, OutputStream output) throws IOException;
}
