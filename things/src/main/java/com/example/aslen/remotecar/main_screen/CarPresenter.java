package com.example.aslen.remotecar.main_screen;

import android.annotation.SuppressLint;

import com.example.aslen.remotecar.car_controller.handler.BaseHandler;
import com.example.aslen.remotecar.car_controller.CarHandlerProvider;
import com.example.aslen.remotecar.car_controller.CarPeripheralListener;
import com.example.aslen.remotecar.car_controller.CarPeripheralManager;
import com.example.common.models.RemoteControlModel;
import com.example.common.presenter.BasePresenter;
import com.example.common.remote_control_service.RemoteControlService;

public class CarPresenter extends BasePresenter<CarView> {

    private final RemoteControlService service;
    private final BaseHandler<RemoteControlModel> controlModelHandler;
    private final CarPeripheralManager carPeripheralManager;


    public CarPresenter(RemoteControlService service, CarPeripheralManager carPeripheralManager,
                        CarHandlerProvider<RemoteControlModel, CarPeripheralListener> carHandlerProvider) {

        this.service = service;
        controlModelHandler = carHandlerProvider.getHandler(carPeripheralManager);
        this.carPeripheralManager = carPeripheralManager;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    @Override
    public void onStart() {
        runOnView(view -> view.showIp(service.getIpAddress()));
        service.subscribe()
                .doOnSubscribe(compositeDisposable::add)
                .subscribe(controlModelHandler::handle, throwable -> runOnView(view -> view.onError(throwable.getMessage())));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        carPeripheralManager.close();
    }
}
