package com.example.aslen.remotecar;

import android.app.Application;

import com.example.aslen.remotecar.di.AppComponent;
import com.example.aslen.remotecar.di.DaggerAppComponent;

public class App extends Application {
    private AppComponent appComponent;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().build();
    }
}
