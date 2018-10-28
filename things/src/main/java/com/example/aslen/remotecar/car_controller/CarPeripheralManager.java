package com.example.aslen.remotecar.car_controller;

import android.annotation.SuppressLint;

import com.example.aslen.remotecar.steppermotor.driver.uln2003.driver.ULN2003Resolution;
import com.example.aslen.remotecar.steppermotor.driver.uln2003.motor.ULN2003StepperMotor;
import com.example.mylibrary.steppermotor.BlinkingDriver;
import com.example.mylibrary.steppermotor.L298nDriver;
import com.example.step_motor.steppermotor.Direction;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CarPeripheralManager implements CarPeripheralListener {
    private final ULN2003StepperMotor uln2003StepperMotor;
    private final BlinkingDriver blinkingDriver;
    private final CompositeDisposable compositeDisposable;
    private final L298nDriver l298nDriver;

    @Inject
    CarPeripheralManager(ULN2003StepperMotor uln2003StepperMotor, BlinkingDriver blinkingDriver, L298nDriver l298nDriver) {
        compositeDisposable = new CompositeDisposable();
        this.uln2003StepperMotor = uln2003StepperMotor;
        this.blinkingDriver = blinkingDriver;
        this.l298nDriver = l298nDriver;
    }

    @Override
    public void rotate(int degree, Direction direction) {
        uln2003StepperMotor.abortAllRotations();
        uln2003StepperMotor.rotate(degree, direction, ULN2003Resolution.FULL.getId(), 50);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    @Override
    public void blink(List<Boolean> times) {
            io.reactivex.Observable.fromIterable(times).delay(50, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                    .doOnSubscribe(compositeDisposable::add)
                    .subscribe(blinkingDriver::setState);
    }

    @Override
    public void close() {
        try{
            uln2003StepperMotor.close();
            blinkingDriver.close();
            compositeDisposable.clear();
            l298nDriver.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        uln2003StepperMotor.stop();
        blinkingDriver.setState(false);
        l298nDriver.stop();
    }

    @Override
    public void move(int direction, int speed) {
        l298nDriver.move(direction, speed);
    }
}
