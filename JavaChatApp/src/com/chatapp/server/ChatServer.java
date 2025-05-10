package com.chatapp.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
  // List to keep track of all connected clients
  private static List<ClientHandler> clients = new ArrayList<>();

  public static void main(String[] args) throws IOException {
      ServerSocket serverSocket = new ServerSocket(5000);
      System.out.println("Server started. Waiting for clients...");

      while (true) {
          Socket clientSocket = serverSocket.accept();
          System.out.println("Client connected: " + clientSocket);

          // Spawn a new thread for each client
          ClientHandler clientThread = new ClientHandler(clientSocket, clients);
          clients.add(clientThread);
          new Thread(clientThread).start();
      }
  }
}