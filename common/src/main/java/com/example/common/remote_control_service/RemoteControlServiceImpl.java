package com.example.common.remote_control_service;

import com.example.common.BuildConfig;
import com.example.common.models.RemoteControlModel;
import com.example.common.parser.BaseParser;
import com.example.common.remote_control_service.socket.Client;
import com.example.common.remote_control_service.socket.Server;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import io.reactivex.Observable;

public class RemoteControlServiceImpl implements RemoteControlService {
    private final BaseParser<String, RemoteControlModel> parser;
    private Client client;
    private final Server server;

    public RemoteControlServiceImpl(BaseParser<String, RemoteControlModel> parser, Server server) {
        this.server = server;
        this.parser = parser;
    }


    @Override
    public Observable<RemoteControlModel> subscribe() {
        return server.subscribe();
    }

    @Override
    public Observable<Boolean> publish(String host) {
        client = new Client(BuildConfig.SERVER_CLIENT_PORT, parser);
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
    public void sendMessage(RemoteControlModel remoteControlModel) {
        client.sendMessage(remoteControlModel);
    }
}
