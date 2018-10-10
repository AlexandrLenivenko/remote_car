package com.example.aslen.remotecar.di;

import com.example.common.parser.ClientParser;
import com.example.common.remote_control_service.RemoteControlService;
import com.example.common.remote_control_service.RemoteControlServiceImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class SocketModule {

    @Provides
    public RemoteControlService provideRemoteControlService(ClientParser clientParser) {
        return new RemoteControlServiceImpl(clientParser);
    }

    @Provides
    public ClientParser provideClientParser() {
        return new ClientParser();
    }
}
