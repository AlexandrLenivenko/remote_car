package com.example.aslen.remotecar.main_screen;


import com.example.step_motor.steppermotor.Direction;

public interface CarView {
    void turnOnOff(boolean isOn);

    void onError(String message);

    void showIp(String ipAddress);

    void rotate(int degree, Direction direction);
}
