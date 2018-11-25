package com.example.mylibrary.steppermotor;


import com.google.android.things.pio.PeripheralManager;
import com.google.android.things.pio.Pwm;

import java.io.IOException;

public class PWMBlinkingDriver implements AutoCloseable {
    private static final float FREQUENCY_HZ = 120.0f;
    private Pwm ledGpio;

    public PWMBlinkingDriver(PeripheralManager peripheralManager, String pinName) {
        try {
            this.ledGpio = peripheralManager.openPwm(pinName);
            this.ledGpio.setPwmFrequencyHz(FREQUENCY_HZ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setState(boolean state) {
        try {
            ledGpio.setEnabled(state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBrightness(float value) {
        try {
            ledGpio.setPwmDutyCycle(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close(){
        if (ledGpio != null) {
            try {
                ledGpio.setEnabled(false);
                ledGpio.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
