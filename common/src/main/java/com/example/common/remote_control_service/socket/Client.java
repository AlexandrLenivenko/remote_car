package com.example.common.remote_control_service.socket;

import com.example.common.models.RemoteControlModel;
import com.example.common.parser.ClientParser;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread {

    private final int port;
    private final ClientParser parser;
    private Socket client;
    private String message;
    private boolean hasNewMessage;
    private String host;

    public Client(int port, ClientParser parser) {
        this.port = port;
        this.parser = parser;
    }

    @Override
    public void run() {
        try {
            client = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        send();
        super.run();
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send() {
        while (!interrupted()) {
            if (hasNewMessage) {
                try (PrintWriter printWriter = new PrintWriter(client.getOutputStream(), true)) {
                    printWriter.println(message);
                    printWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void close() {
        interrupt();
    }

    public void sendMessage(RemoteControlModel model, String host) {
        if (client == null) {
            this.host = host;
            start();
        }
        message = parser.parseFromModel(model);
        hasNewMessage = true;
    }
}
