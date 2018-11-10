package com.example.mylibrary.steppermotor.light_driver;


import android.util.Log;
import android.util.SparseArray;

import com.example.mylibrary.steppermotor.light_driver.light_command.OneStateLightDriver;
import com.example.mylibrary.steppermotor.light_driver.light_command.TwoStateLightDriver;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;

import static com.example.mylibrary.steppermotor.light_driver.CarLightDriver.LightState.ALL;
import static com.example.mylibrary.steppermotor.light_driver.CarLightDriver.LightState.BACKWARD;
import static com.example.mylibrary.steppermotor.light_driver.CarLightDriver.LightState.FORWARD;
import static com.example.mylibrary.steppermotor.light_driver.CarLightDriver.LightState.LEFT;
import static com.example.mylibrary.steppermotor.light_driver.CarLightDriver.LightState.RIGHT;

public class CarLightDriver implements LightDriver {
    private static final String TAG = "CarLightDriver";

    private final SparseArray<BaseCommand> commandArray;

    /**
     * pinsName[0], pinsName[1] -> FORWARD
     * pinsName[2], pinsName[3] -> BACKWARD
     * pinsName[4], pinsName[5] -> LEFT
     * pinsName[6], pinsName[7] -> RIGHT
     * pinsName[0...7]          -> ALL
     */
    public CarLightDriver(PeripheralManager prManager, String [] pinsName)  {
        if (pinsName.length != 8) {
            throw new IllegalArgumentException("Number on pins should be 8");
        }

        commandArray = new SparseArray<>();
        final Gpio gpio0 = getGpio(prManager, pinsName[0]);
        final Gpio gpio1 = getGpio(prManager, pinsName[1]);
        final Gpio gpio2 = getGpio(prManager, pinsName[2]);
        final Gpio gpio3 = getGpio(prManager, pinsName[3]);
        final Gpio gpio4 = getGpio(prManager, pinsName[4]);
        final Gpio gpio5 = getGpio(prManager, pinsName[5]);
        final Gpio gpio6 = getGpio(prManager, pinsName[6]);
        final Gpio gpio7 = getGpio(prManager, pinsName[7]);

        commandArray.put(FORWARD, new OneStateLightDriver(new Gpio[]{gpio0, gpio1}));
        commandArray.put(BACKWARD, new OneStateLightDriver(new Gpio[]{gpio2, gpio3}));
        commandArray.put(LEFT, new TwoStateLightDriver(new Gpio[]{gpio4, gpio5}));
        commandArray.put(RIGHT, new TwoStateLightDriver(new Gpio[]{gpio6, gpio7}));
        commandArray.put(ALL, new TwoStateLightDriver(new Gpio[]{gpio0, gpio1, gpio2, gpio3, gpio6, gpio4, gpio5, gpio7}));

    }

    @Override
    public void invoke(int state) {
        BaseCommand command = commandArray.get(state);
        if(command == null) {
            return;
        }

        for (int i = 0; i < commandArray.size(); i++) {
            commandArray.get(i).cancel();
        }

        command.execute();
    }

    @Override
    public void destroy() {
        for (int i = 0; i < commandArray.size(); i++) {
            commandArray.get(i).destroy();
        }
    }

    private Gpio getGpio(PeripheralManager peripheralManager, String pinName) {
        Gpio gpio = null;
        try {
            gpio = peripheralManager.openGpio(pinName);
            gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            return gpio;
        } catch (IOException e) {
            Log.e(TAG, "CarLightDriver getGpio: " + e.getMessage() );
        }
        return gpio;
    }

    public interface LightState {
        int FORWARD = 0;
        int BACKWARD = 1;
        int LEFT = 2;
        int RIGHT = 3;
        int ALL = 4;
    }
}
