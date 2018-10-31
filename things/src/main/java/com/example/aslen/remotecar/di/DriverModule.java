package com.example.aslen.remotecar.di;

import com.example.aslen.remotecar.GpioContract;
import com.example.aslen.remotecar.steppermotor.driver.uln2003.motor.ULN2003StepperMotor;

import com.example.mylibrary.steppermotor.BlinkingDriver;
import com.example.mylibrary.steppermotor.l298n.L298nDriver;
import com.example.mylibrary.steppermotor.l298n.L298nDriverOneEngine;
import com.example.mylibrary.steppermotor.l298n.L298nDriverTwoEngines;
import com.google.android.things.pio.PeripheralManager;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.example.aslen.remotecar.GpioContract.ULN2003StepperMotor.IN_1;
import static com.example.aslen.remotecar.GpioContract.ULN2003StepperMotor.IN_2;
import static com.example.aslen.remotecar.GpioContract.ULN2003StepperMotor.IN_3;
import static com.example.aslen.remotecar.GpioContract.ULN2003StepperMotor.IN_4;

@Module
class DriverModule {

    @Provides
    ULN2003StepperMotor provideULN2003StepperMotor(PeripheralManager peripheralManager) {
        return new ULN2003StepperMotor(peripheralManager,IN_1, IN_2, IN_3, IN_4);
    }

    @Provides
    BlinkingDriver provideBlinkingDriver(PeripheralManager peripheralManager) {
        return new BlinkingDriver(peripheralManager, GpioContract.BlinkingDriver.IN_1);
    }

    @Provides
    @Named("OneEngine")
    L298nDriver provideL298nDriverOneEngine(PeripheralManager peripheralManager) {
        return new L298nDriverOneEngine(peripheralManager, GpioContract.L298nDriver.IN_1, GpioContract.L298nDriver.IN_2, GpioContract.L298nDriver.PWM1);
    }

    @Provides
    @Named("TwoEngine")
    L298nDriver provideL298nDriverTwoEngine(PeripheralManager peripheralManager) {
        return new L298nDriverTwoEngines(peripheralManager, GpioContract.L298nDriver.IN_1,
                GpioContract.L298nDriver.IN_2, GpioContract.L298nDriver.PWM1, GpioContract.L298nDriver.IN_3,
                GpioContract.L298nDriver.IN_4, GpioContract.L298nDriver.PWM2);
    }

    @Provides
    PeripheralManager providePeripheralManager() {
        return PeripheralManager.getInstance();
    }
}
