package com.chatapp.client;

import java.io.*;
import java.util.function.Consumer;
import java.net.*;

public class ChatClient {
  private Socket socket = null;
  private PrintWriter out = null;
  private BufferedReader in = null;
  private Consumer<String> onMessageReceived;

  public ChatClient(String address, int port, Consumer<String> onMessageReceived) throws IOException{
          socket = new Socket(address, port);
          out = new PrintWriter(socket.getOutputStream(), true);
          in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          this.onMessageReceived = onMessageReceived;
  }
  
  public void sendMessage(String msg) {
      out.println(msg);
  }
  
  public void startClient() {
      new Thread(() -> {
          try {
              String line;
              while ((line = in.readLine()) != null) {
                  onMessageReceived.accept(line);
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
      }).start();
  }
}
