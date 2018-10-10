package com.example.common.remote_control_service.socket;

import android.util.Log;

import com.example.common.models.RemoteControlModel;
import com.example.common.parser.ClientParser;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class Server extends Thread {
    private static final String TAG = "Server";

    private final int port;
    private final BehaviorSubject<RemoteControlModel> subject;
    private final ClientParser clientParser;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    public Server(int port, ClientParser clientParser) {
        this.port = port;
        subject = BehaviorSubject.create();
        this.clientParser = clientParser;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            subject.onError(e);
        }
        acceptNewClient();
        super.run();
    }

    private void acceptNewClient() {
        while (!interrupted()) {
            try {
                clientSocket = serverSocket.accept();
                Log.d(TAG, "Connected new device");
                work();

            } catch (IOException e) {
                subject.onError(e);
                e.printStackTrace();
            }
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void work() {
        String request;
        while (clientSocket.isConnected()) {
            try (PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                 Scanner scanner = new Scanner(clientSocket.getInputStream())) {
                request = scanner.nextLine();
                Log.d(TAG, "request " + request);
                subject.onNext(clientParser.parse(request));

            } catch (IOException e) {
                subject.onError(e);
                e.printStackTrace();
            }
        }
    }

    public void close() {
        interrupt();
    }

    public Observable<RemoteControlModel> subscribe() {
        start();
        return subject;
    }
}
