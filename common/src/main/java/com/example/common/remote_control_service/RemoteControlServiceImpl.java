package com.example.common.remote_control_service;

import com.example.common.models.RemoteControlModel;
import com.example.common.parser.ClientParser;
import com.example.common.remote_control_service.socket.Client;
import com.example.common.remote_control_service.socket.Server;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import io.reactivex.Observable;

public class RemoteControlServiceImpl implements RemoteControlService {
    private static final int PORT = 4543;
    private final ClientParser parser;
    private Client client;
    private final Server server;

    public RemoteControlServiceImpl(ClientParser parser) {

        this.server = new Server(PORT, parser);
        this.parser = parser;
    }


    @Override
    public Observable<RemoteControlModel> subscribe() {
        return server.subscribe();
    }

    @Override
    public Observable<Boolean> publish(String host) {
        this.client = new Client(PORT, parser);
        return client.connect(host);
    }

    @Override
    public String getIpAddress() {
        StringBuilder ip = new StringBuilder();
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress
                            .nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip.append("Server running at : ").append(inetAddress.getHostAddress());
                    }
                }
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip.append("Something Wrong! ").append(e.toString()).append("\n");
        }
        return ip.toString();
    }

    @Override
    public void unSubscribe() {
        server.close();
    }

    @Override
    public void unPublish() {
        client.close();
    }

    @Override
    public void sendMessage() {
        client.sendMessage(new RemoteControlModel());
    }
}
