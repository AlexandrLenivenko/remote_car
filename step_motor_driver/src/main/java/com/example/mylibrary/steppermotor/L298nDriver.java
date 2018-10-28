package com.example.mylibrary.steppermotor;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;

import static com.example.mylibrary.steppermotor.L298nDriver.Direction.DOWN;
import static com.example.mylibrary.steppermotor.L298nDriver.Direction.STOP;
import static com.example.mylibrary.steppermotor.L298nDriver.Direction.UP;

public class L298nDriver implements AutoCloseable {

    private Gpio in1;
    private Gpio in2;
    private Gpio[] gpios;

    public L298nDriver(PeripheralManager peripheralManager, String in1GpioId, String in2GpioId) {
        try {
            in1 = peripheralManager.openGpio(in1GpioId);
            in2 = peripheralManager.openGpio(in2GpioId);
            in1.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            in2.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            gpios = new Gpio[]{in1, in2};
        } catch (IOException e) {
            e.printStackTrace();
            gpios = new Gpio[0];
        }
    }

    public void move(int direction) {
        //todo
        try {
            if (direction == UP) {
                in1.setValue(true);
                in2.setValue(false);

            } else if(direction == DOWN) {
                in1.setValue(false);
                in2.setValue(true);
            } else {
                in1.setValue(false);
                in2.setValue(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        move(STOP);
        for (int i = 0; i < gpios.length; i++) {
            if(gpios[0] != null) {
                gpios[0].close();
            }
        }
    }

    public void stop() {
        move(STOP);
    }

    public interface Direction {
        int UP = 0;
        int DOWN = 1;
        int STOP = 1;
    }
}
