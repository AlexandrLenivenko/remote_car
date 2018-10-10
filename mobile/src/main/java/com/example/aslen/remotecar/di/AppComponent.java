package com.example.aslen.remotecar.di;

import com.example.aslen.remotecar.main_screen.MainActivity;

import dagger.Component;

@Component(modules = {SocketModule.class, PresenterModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);
}
