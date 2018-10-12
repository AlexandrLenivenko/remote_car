package com.example.common.remote_control_service.socket;

import android.util.Log;

import com.example.common.models.RemoteControlModel;
import com.example.common.parser.ClientParser;

import java.io.IOException;
import java.net.ServerSocket;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class Server extends Thread {
    private static final String TAG = "Server";

    private final int port;
    private final BehaviorSubject<RemoteControlModel> subject;
    private final ClientParser clientParser;
    private ServerSocket serverSocket;

    public Server(int port, ClientParser clientParser) {
        this.port = port;
        subject = BehaviorSubject.create();
        this.clientParser = clientParser;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            acceptNewClient();
        } catch (IOException e) {
            e.printStackTrace();
            subject.onError(e);
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.run();
    }

    private void acceptNewClient() {
        ConnectionWorker connectionWorker;
        while (!isInterrupted()) {
            try {
                Log.d(TAG, "Connected new device");
                connectionWorker = new ConnectionWorker(serverSocket.accept(), subject, clientParser);
                new Thread(connectionWorker).start();

            } catch (IOException e) {
                subject.onError(e);
                e.printStackTrace();
            }
        }
    }

    public void close() {
        interrupt();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            subject.onError(e);
        }

    }

    public Observable<RemoteControlModel> subscribe() {
        start();
        return subject;
    }
}
