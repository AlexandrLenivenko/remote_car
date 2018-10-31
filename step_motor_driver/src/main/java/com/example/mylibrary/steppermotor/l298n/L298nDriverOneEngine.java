package com.example.mylibrary.steppermotor.l298n;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;
import com.google.android.things.pio.Pwm;

import java.io.Closeable;
import java.io.IOException;

import static com.example.mylibrary.steppermotor.l298n.L298nDriver.Direction.DOWN;
import static com.example.mylibrary.steppermotor.l298n.L298nDriver.Direction.STOP;
import static com.example.mylibrary.steppermotor.l298n.L298nDriver.Direction.UP;

public class L298nDriverOneEngine implements L298nDriver {

    private static final int FREQUENCY_HZ = 120;
    private Gpio in1;
    private Gpio in2;
    private Pwm pwm;
    private Closeable[] gpios;

    public L298nDriverOneEngine(PeripheralManager peripheralManager, String in1GpioId, String in2GpioId, String pwm) {
        try {
            in1 = peripheralManager.openGpio(in1GpioId);
            in2 = peripheralManager.openGpio(in2GpioId);
            this.pwm = peripheralManager.openPwm(pwm);
            in1.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            in2.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            this.pwm.setPwmFrequencyHz(FREQUENCY_HZ);
            this.pwm.setEnabled(true);
            gpios = new Closeable[]{in1, in2, this.pwm};
        } catch (IOException e) {
            e.printStackTrace();
            gpios = new Gpio[0];
        }
    }

    @Override
    public void move(int direction, int speed) {
        try {
            switch (direction) {
                case UP:
                    in1.setValue(true);
                    in2.setValue(false);
                    pwm.setPwmDutyCycle(speed);
                    break;
                case DOWN:
                    in1.setValue(false);
                    in2.setValue(true);
                    pwm.setPwmDutyCycle(Math.abs(speed));
                    break;
                default:
                    in1.setValue(false);
                    in2.setValue(false);
                    pwm.setPwmDutyCycle(Math.abs(speed));
                    pwm.setPwmDutyCycle(Math.abs(speed));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        stop();
        for (int i = 0; i < gpios.length; i++) {
            if(gpios[i] != null) {
                gpios[i].close();
                gpios[i] = null;
            }
        }
    }

    @Override
    public void stop() {
        move(STOP, 0);
    }

}
