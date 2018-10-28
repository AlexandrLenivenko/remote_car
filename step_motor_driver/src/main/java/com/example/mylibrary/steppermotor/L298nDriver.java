package com.example.mylibrary.steppermotor;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;
import com.google.android.things.pio.Pwm;

import java.io.Closeable;
import java.io.IOException;

import static com.example.mylibrary.steppermotor.L298nDriver.Direction.DOWN;
import static com.example.mylibrary.steppermotor.L298nDriver.Direction.STOP;
import static com.example.mylibrary.steppermotor.L298nDriver.Direction.UP;

public class L298nDriver implements AutoCloseable {

    public static final int FREQUENCY_HZ = 120;
    private Gpio in1;
    private Gpio in2;
    private Pwm in3;
    private Closeable[] gpios;

    public L298nDriver(PeripheralManager peripheralManager, String in1GpioId, String in2GpioId, String in3GpioId) {
        try {
            in1 = peripheralManager.openGpio(in1GpioId);
            in2 = peripheralManager.openGpio(in2GpioId);
            in3 = peripheralManager.openPwm(in3GpioId);
            in1.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            in2.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            in3.setPwmFrequencyHz(FREQUENCY_HZ);
            in3.setEnabled(true);
            gpios = new Closeable[]{in1, in2, in3};
        } catch (IOException e) {
            e.printStackTrace();
            gpios = new Gpio[0];
        }
    }

    public void move(int direction, int speed) {
        //todo
        try {
            if (direction == UP) {
                in1.setValue(true);
                in2.setValue(false);
                in3.setPwmDutyCycle(speed);
            } else if(direction == DOWN) {
                in1.setValue(false);
                in2.setValue(true);
                in3.setPwmDutyCycle(Math.abs(speed));
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
        move(STOP, 0);
        for (int i = 0; i < gpios.length; i++) {
            if(gpios[0] != null) {
                gpios[0].close();
                gpios[0] = null;
            }
        }
    }

    public void stop() {
        move(STOP, 0);
    }

    public interface Direction {
        int UP = 0;
        int DOWN = 1;
        int STOP = 2;
    }
}
