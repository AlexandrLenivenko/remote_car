package com.example.aslen.remotecar.di;

import com.example.aslen.remotecar.main_screen.CarActivity;

import dagger.Component;

@Component(modules = {SocketModule.class, PresenterModule.class, DriverModule.class, HandlerProviderModule.class})
public interface AppComponent {

    void inject(CarActivity mainActivity);
}
