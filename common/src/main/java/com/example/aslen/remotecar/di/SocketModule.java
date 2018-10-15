package com.example.aslen.remotecar.di;

import com.example.common.BuildConfig;
import com.example.common.parser.ClientServerParser;
import com.example.common.remote_control_service.RemoteControlService;
import com.example.common.remote_control_service.RemoteControlServiceImpl;
import com.example.common.remote_control_service.socket.Server;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

@Module
class SocketModule {

    @Provides
    RemoteControlService provideRemoteControlService(ClientServerParser parser, Server server) {
        return new RemoteControlServiceImpl(parser, server);
    }

    @Provides
    ClientServerParser provideClientParser(Gson gson) {
        return new ClientServerParser(gson);
    }

    @Provides
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    Server provideServer(ClientServerParser parser) {
        return new Server(BuildConfig.SERVER_CLIENT_PORT, parser);
    }
}
