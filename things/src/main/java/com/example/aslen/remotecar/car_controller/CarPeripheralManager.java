package com.example.aslen.remotecar.car_controller;

import android.annotation.SuppressLint;

import com.example.aslen.remotecar.steppermotor.driver.uln2003.driver.ULN2003Resolution;
import com.example.aslen.remotecar.steppermotor.driver.uln2003.motor.ULN2003StepperMotor;
import com.example.mylibrary.steppermotor.BlinckingDriver;
import com.example.step_motor.steppermotor.Direction;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CarPeripheralManager implements CarPeripheralView {
    private final ULN2003StepperMotor uln2003StepperMotor;
    private final BlinckingDriver blinckingDriver;
    private final CompositeDisposable compositeDisposable;

    public CarPeripheralManager(ULN2003StepperMotor uln2003StepperMotor, BlinckingDriver blinckingDriver) {
        compositeDisposable = new CompositeDisposable();
        this.uln2003StepperMotor = uln2003StepperMotor;
        this.blinckingDriver = blinckingDriver;
    }

    @Override
    public void rotate(int degree, Direction direction) {
        uln2003StepperMotor.abortAllRotations();
        uln2003StepperMotor.rotate(degree, direction, ULN2003Resolution.FULL.getId(), 50);
    }

    @SuppressLint("CheckResult")
    @Override
    public void blink(List<Boolean> times) {
            io.reactivex.Observable.fromIterable(times).delay(50, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                    .doOnSubscribe(compositeDisposable::add)
                    .subscribe(blinckingDriver::setState);
    }

    @Override
    public void close() {
        try{
            uln2003StepperMotor.close();
            blinckingDriver.close();
            compositeDisposable.clear();
        }catch (Exception e) {

        }
    }

    @Override
    public void stop() {
        uln2003StepperMotor.stop();
        blinckingDriver.setState(false);
    }
}
