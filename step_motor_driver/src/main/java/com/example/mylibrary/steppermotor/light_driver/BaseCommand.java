package com.example.mylibrary.steppermotor.light_driver;


import com.google.android.things.pio.Gpio;

import java.io.IOException;

public abstract class BaseCommand {
    protected final Gpio[] gpios;

    protected BaseCommand(Gpio[] gpios) {
        this.gpios = gpios;
    }

    public void cancel() {
        for (int i = 0; i < gpios.length; i++) {
            setState(gpios[i], false);
        }
    }

    public void destroy() {
        for (int i = 0; i < gpios.length; i++) {
            if (gpios[i] != null) {
                try {
                    gpios[i].setValue(false);
                    gpios[i].close();
                } catch (IOException e) {
                    // TODO: 11/8/18 maybe should add error listener
                }
            }
        }

    }

    public abstract void execute();

    protected void setState(Gpio gpio, boolean state) {
        try {
            gpio.setValue(state);
        } catch (IOException e) {
            // TODO: 11/8/18 maybe should add error listener
        }
    }

}
