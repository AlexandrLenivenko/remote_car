package com.example.mylibrary.steppermotor.l298n;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;
import com.google.android.things.pio.Pwm;

import java.io.Closeable;
import java.io.IOException;

import static com.example.mylibrary.steppermotor.l298n.L298nDriver.Direction.DOWN;
import static com.example.mylibrary.steppermotor.l298n.L298nDriver.Direction.STOP;
import static com.example.mylibrary.steppermotor.l298n.L298nDriver.Direction.UP;

public class L298nDriverTwoEngines implements L298nDriver {
    private static final int FREQUENCY_HZ = 120;
    private Gpio in1;
    private Gpio in2;
    private Pwm pwm1;
    private Gpio in3;
    private Gpio in4;
    private Pwm pwm2;
    private Closeable[] gpios;

    public L298nDriverTwoEngines(PeripheralManager peripheralManager, String in1GpioId, String in2GpioId, String pwm1,
                                 String in3GpioId, String in4GpioId, String pwm2 ) {
        try {
            in1 = peripheralManager.openGpio(in1GpioId);
            in2 = peripheralManager.openGpio(in2GpioId);
            in3 = peripheralManager.openGpio(in3GpioId);
            in4 = peripheralManager.openGpio(in4GpioId);
            in1.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            in2.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            in3.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            in4.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

            this.pwm1 = peripheralManager.openPwm(pwm1);
            this.pwm2 = peripheralManager.openPwm(pwm2);
            this.pwm1.setPwmFrequencyHz(FREQUENCY_HZ);
            this.pwm2.setPwmFrequencyHz(FREQUENCY_HZ);
            this.pwm1.setEnabled(true);
            this.pwm2.setEnabled(true);
            gpios = new Closeable[]{in1, in2,  in3, in4, this.pwm1, this.pwm2};
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
                    in3.setValue(true);
                    in4.setValue(false);
                    pwm1.setPwmDutyCycle(speed);
                    pwm2.setPwmDutyCycle(speed);
                    break;
                case DOWN:
                    in1.setValue(false);
                    in2.setValue(true);
                    in3.setValue(false);
                    in4.setValue(true);
                    pwm1.setPwmDutyCycle(Math.abs(speed));
                    pwm2.setPwmDutyCycle(Math.abs(speed));
                    break;
                default:
                    in1.setValue(false);
                    in2.setValue(false);
                    in3.setValue(false);
                    in4.setValue(false);
                    pwm1.setPwmDutyCycle(Math.abs(speed));
                    pwm1.setPwmDutyCycle(Math.abs(speed));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        move(STOP, 0);
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
}
