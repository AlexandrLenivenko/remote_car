package com.example.aslen.remotecar.di;

import com.example.aslen.remotecar.main_screen.MainActivityPresenter;
import com.example.common.remote_control_service.RemoteControlService;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    MainActivityPresenter provideMainActivityPresenter(RemoteControlService service) {
        return new MainActivityPresenter(service);
    }
}
