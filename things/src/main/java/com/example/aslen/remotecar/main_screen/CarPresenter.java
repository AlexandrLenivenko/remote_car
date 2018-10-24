package com.example.aslen.remotecar.main_screen;

import android.annotation.SuppressLint;

import com.example.aslen.remotecar.car_controller.blink.BlinkHendler;
import com.example.aslen.remotecar.car_controller.CarPeripheralManager;
import com.example.aslen.remotecar.car_controller.step_motor.StepMotorHandler;
import com.example.aslen.remotecar.car_controller.stop.StopHandler;
import com.example.aslen.remotecar.steppermotor.driver.uln2003.motor.ULN2003StepperMotor;
import com.example.common.models.RemoteControlModel;
import com.example.common.presenter.BasePresenter;
import com.example.common.remote_control_service.RemoteControlService;
import com.example.mylibrary.steppermotor.BlinckingDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CarPresenter extends BasePresenter<CarView> {

    private final RemoteControlService service;
    private final StepMotorHandler stepMotorHandler;
    private final CarPeripheralManager carPeripheralManager;


    public CarPresenter(RemoteControlService service) {
        this.service = service;

        carPeripheralManager = new CarPeripheralManager(new ULN2003StepperMotor("BCM4", "BCM17", "BCM27", "BCM22"), new BlinckingDriver("BCM2"));

        stepMotorHandler = new StepMotorHandler(carPeripheralManager);
        BlinkHendler blinkHendler = new BlinkHendler(carPeripheralManager);
        stepMotorHandler.setNextHandler(blinkHendler);
        blinkHendler.setNextHandler(new StopHandler(carPeripheralManager));
    }

    @SuppressLint("CheckResult")
    @Override
    public void onStart() {
        runOnView(view -> view.showIp(service.getIpAddress()));
        service.subscribe()
                .doOnSubscribe(compositeDisposable::add)
                //.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::show, throwable -> runOnView(view -> view.onError(throwable.getMessage())));

    }


    /**
     * @param model stop - turn on
     *              up   - 1
     *              down - 2
     *              left - 3
     *              right- 4
     */
    private void show(RemoteControlModel model) {
        stepMotorHandler.handle(model);
       /* runOnView(view -> view.turnOnOff(model.isStop()));

        if (model.isUp()) {
            blink(Arrays.asList(true, false));
            return;
        } else {

        }
        if (model.isDown()) {
            blink(Arrays.asList(true, false, true, false));
            return;
        }*/

    }

    @SuppressLint("CheckResult")
    private void blink(List<Boolean> first) {
        io.reactivex.Observable.fromIterable(first).delay(50, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                .doOnSubscribe(compositeDisposable::add)
                .subscribe(aBoolean -> runOnView(view -> view.turnOnOff(aBoolean)));
    }
}
