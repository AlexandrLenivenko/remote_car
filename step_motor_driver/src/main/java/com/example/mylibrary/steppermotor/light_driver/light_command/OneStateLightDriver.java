package com.example.mylibrary.steppermotor.light_driver.light_command;


import com.example.mylibrary.steppermotor.light_driver.BaseCommand;
import com.google.android.things.pio.Gpio;


public class OneStateLightDriver extends BaseCommand {

    public OneStateLightDriver(Gpio[] gpios) {
        super(gpios);
    }

    @Override
    public void execute() {
        for (int i = 0; i < gpios.length; i++) {
            setState(gpios[i], true);
        }
    }

}
