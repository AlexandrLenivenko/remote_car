package com.example.aslen.remotecar;

public interface GpioContract {

    interface ULN2003StepperMotor {
        String IN_1 = "BCM4";
        String IN_2 = "BCM17";
        String IN_3 = "BCM27";
        String IN_4 = "BCM22";
    }

    interface BlinkingDriver {
        String IN_1 = "BCM2";
    }

    interface L298nDriver {
        String IN_1 = "BCM6";
        String IN_2 = "BCM5";
        String PWM1 = "PWM1";
        String IN_3 = "BCM6";//todo: add correct number when find
        String IN_4 = "BCM5";//todo: add correct number when find
        String PWM2 = "PWM1";//todo: add correct number when find
    }
}
