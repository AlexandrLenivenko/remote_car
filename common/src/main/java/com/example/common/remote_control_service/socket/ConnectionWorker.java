package com.example.common.remote_control_service.socket;

import android.util.Log;

import com.example.common.models.RemoteControlModel;
import com.example.common.parser.BaseParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import io.reactivex.subjects.BehaviorSubject;

public class ConnectionWorker implements Runnable {
    private static final String TAG = "ConnectionWorker";

    private final Socket socket;
    private final BehaviorSubject<RemoteControlModel> subject;
    private final BaseParser<String, RemoteControlModel> clientParser;
    private InputStream inputStream;

    public ConnectionWorker(Socket socket, BehaviorSubject<RemoteControlModel> subject, BaseParser<String, RemoteControlModel> clientParser) {
        this.socket = socket;
        this.subject = subject;
        this.clientParser = clientParser;
    }

    @Override
    public void run() {
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            subject.onError(new IOException("Cannot get inputStream"));
        }

        byte[] buffer = new byte[1024 * 4];
        while (true) {
            try {
                int count = inputStream.read(buffer, 0, buffer.length);
                if (count > 0) {
                    String response = new String(buffer, 0, count);
                    Log.d(TAG, response);
                    subject.onNext(clientParser.parse(response));
                } else if (count == -1) {
                    Log.d(TAG, "close socket");
                    socket.close();
                    break;
                }
            } catch (IOException e) {
                subject.onError(e);
            }
        }
    }
}
