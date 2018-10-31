package com.example.aslen.remotecar.di;

import com.example.aslen.remotecar.car_controller.CarHandlerProvider;
import com.example.aslen.remotecar.car_controller.CarPeripheralListener;
import com.example.aslen.remotecar.car_controller.CarPeripheralManager;
import com.example.aslen.remotecar.main_screen.CarPresenter;
import com.example.common.models.RemoteControlModel;
import com.example.common.remote_control_service.RemoteControlService;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    CarPresenter provideCarPresenter(RemoteControlService service, CarPeripheralManager carPeripheralManager, CarHandlerProvider<RemoteControlModel, CarPeripheralListener> carHandlerProvider) {
        return new CarPresenter(service, carPeripheralManager, carHandlerProvider);
    }
}
