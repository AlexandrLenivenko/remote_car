package com.example.aslen.remotecar.car_controller;

import com.example.aslen.remotecar.steppermotor.driver.uln2003.driver.ULN2003Resolution;
import com.example.aslen.remotecar.steppermotor.driver.uln2003.motor.ULN2003StepperMotor;
import com.example.mylibrary.steppermotor.l298n.L298nDriver;
import com.example.mylibrary.steppermotor.l298n.L298nDriverOneEngine;
import com.example.mylibrary.steppermotor.light_driver.CarLightDriver;
import com.example.mylibrary.steppermotor.light_driver.LightDriver;
import com.example.step_motor.steppermotor.Direction;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.CompositeDisposable;

import static com.example.mylibrary.steppermotor.light_driver.CarLightDriver.LightState.ALL;

public class CarPeripheralManager implements CarPeripheralListener {
    private final ULN2003StepperMotor uln2003StepperMotor;
    private final CompositeDisposable compositeDisposable;
    private final L298nDriver l298nDriver;
    private final LightDriver lightDriver;

    @Inject
    CarPeripheralManager(ULN2003StepperMotor uln2003StepperMotor, @Named("OneEngine") L298nDriver l298nDriver, LightDriver lightDriver) {
        compositeDisposable = new CompositeDisposable();
        this.uln2003StepperMotor = uln2003StepperMotor;
        this.l298nDriver = l298nDriver;
        this.lightDriver = lightDriver;
    }

    @Override
    public void rotate(int degree, Direction direction) {
        uln2003StepperMotor.abortAllRotations();
        uln2003StepperMotor.rotate(degree, direction, ULN2003Resolution.FULL.getId(), 50);
        lightDriver.invoke(direction == Direction.CLOCKWISE ? CarLightDriver.LightState.RIGHT : CarLightDriver.LightState.LEFT);
    }

    @Override
    public void close() {
        try{
            uln2003StepperMotor.close();
            //blinkingDriver.close();
            compositeDisposable.clear();
            l298nDriver.close();
            lightDriver.destroy();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        uln2003StepperMotor.stop();
        l298nDriver.stop();
        lightDriver.invoke(ALL);
    }

    @Override
    public void move(int direction, int speed) {
        l298nDriver.move(direction, speed);
        if (speed == 0) {
            lightDriver.invoke(ALL);
        } else
            lightDriver.invoke(direction == L298nDriverOneEngine.Direction.UP ? CarLightDriver.LightState.FORWARD : CarLightDriver.LightState.BACKWARD);
    }

    @Override
    public void blink(List<Boolean> times) {

    }
}
