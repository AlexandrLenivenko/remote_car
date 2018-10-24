package com.example.mylibrary.steppermotor;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;

public class BlinckingDriver implements AutoCloseable   {

    private Gpio ledGpio;

    public BlinckingDriver(String pinName) {
        try {
            ledGpio = PeripheralManager.getInstance().openGpio(pinName);
            ledGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setState(boolean state) {
        try {
            ledGpio.setValue(state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close(){
        if (ledGpio != null) {
            try {
                ledGpio.setValue(false);
                ledGpio.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
