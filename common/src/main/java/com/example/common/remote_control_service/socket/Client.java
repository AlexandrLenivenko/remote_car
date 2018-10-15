package com.example.common.remote_control_service.socket;

import com.example.common.models.RemoteControlModel;
import com.example.common.parser.BaseParser;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class Client extends Thread {

    private final int port;
    private final BaseParser<String, RemoteControlModel> parser;
    private BehaviorSubject<Boolean> subject;
    private Socket client;
    private String message;
    private boolean hasNewMessage;
    private String host;

    public Client(int port, BaseParser<String, RemoteControlModel> parser) {
        this.port = port;
        this.parser = parser;
        subject = BehaviorSubject.create();
    }

    @Override
    public void run() {
        try {
            client = new Socket(host, port);
            subject.onNext(true);
        } catch (IOException e) {
            subject.onError(e);
            e.printStackTrace();
            interrupt();
        }

        send();

        super.run();
    }

    private void send() {
        try (PrintWriter printWriter = new PrintWriter(client.getOutputStream(), true)) {
            while (client != null && client.isConnected()) {
                if (hasNewMessage) {
                    printWriter.println(message);
                    printWriter.flush();
                    hasNewMessage = false;
                }
                if (isInterrupted()) {
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            subject.onError(e);
        }
    }

    public void close() {
        interrupt();
        closeConnection();
    }

    public void sendMessage(RemoteControlModel model) {
        message = parser.parseFromModel(model);
        hasNewMessage = true;
    }


    public Observable<Boolean> connect(String host) {
        closeConnection();
        this.host = host;
        start();
        return subject;
    }

    private void closeConnection() {
        if (client != null && !client.isClosed()) {
            try {
                if (client != null)
                    client.close();
            } catch (IOException e) {
                e.printStackTrace();
                subject.onError(e);
            } finally {
                client = null;
                subject = null;
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        closeConnection();
    }
}
