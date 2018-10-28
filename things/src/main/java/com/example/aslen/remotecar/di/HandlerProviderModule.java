package com.example.aslen.remotecar.di;

import com.example.aslen.remotecar.car_controller.CarHandlerProvider;
import com.example.aslen.remotecar.car_controller.CarPeripheralListener;
import com.example.aslen.remotecar.car_controller.RemoteCarHandlerProvider;
import com.example.common.models.RemoteControlModel;

import dagger.Module;
import dagger.Provides;

@Module
public class HandlerProviderModule {

    @Provides
    CarHandlerProvider<RemoteControlModel, CarPeripheralListener> provideCarHandlerProvider() {
        return new RemoteCarHandlerProvider();
    }
}
