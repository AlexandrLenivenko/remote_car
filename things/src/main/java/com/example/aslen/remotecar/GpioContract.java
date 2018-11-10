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
        String IN_3 = "BCM?";//todo: add correct number when find
        String IN_4 = "BCM?";//todo: add correct number when find
        String PWM2 = "PWM?";//todo: add correct number when find
    }

    interface CarLightDriver {
        String IN_1 = "BCM14"; // forward
        String IN_2 = "BCM15"; // forward
        String IN_3 = "BCM10"; // backward right
        String IN_4 = "BCM25"; // backward left
        String IN_5 = "BCM20"; // left top
        String IN_6 = "BCM8";  //left bottom
        String IN_7 = "BCM21"; //right top
        String IN_8 = "BCM9"; //right bottom
        String[] PINS = new String[] {IN_1, IN_2, IN_3, IN_4,IN_5, IN_6,IN_7, IN_8,};
    }
}
