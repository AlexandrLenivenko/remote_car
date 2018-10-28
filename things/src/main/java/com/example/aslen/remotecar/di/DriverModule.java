package com.example.aslen.remotecar.di;

import com.example.aslen.remotecar.GpioContract;
import com.example.aslen.remotecar.steppermotor.driver.uln2003.motor.ULN2003StepperMotor;

import com.example.mylibrary.steppermotor.BlinkingDriver;
import com.example.mylibrary.steppermotor.L298nDriver;
import com.google.android.things.pio.PeripheralManager;

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
        return  new BlinkingDriver(peripheralManager, GpioContract.BlinkingDriver.IN_1);
    }

    @Provides
    L298nDriver provideL298nDriver(PeripheralManager peripheralManager) {
        return  new L298nDriver(peripheralManager, GpioContract.L298nDriver.IN_1, GpioContract.L298nDriver.IN_2);
    }

    @Provides
    PeripheralManager providePeripheralManager() {
        return PeripheralManager.getInstance();
    }
}
