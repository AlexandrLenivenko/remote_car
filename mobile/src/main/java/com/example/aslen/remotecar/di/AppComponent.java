package com.example.aslen.remotecar.di;

import com.example.aslen.remotecar.SettingsDialogFragment;
import com.example.aslen.remotecar.main_screen.MainActivity;

import dagger.Component;

@Component(modules = {ApplicationModule.class, SocketModule.class, PresenterModule.class, SettingModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(SettingsDialogFragment fragment);
}
