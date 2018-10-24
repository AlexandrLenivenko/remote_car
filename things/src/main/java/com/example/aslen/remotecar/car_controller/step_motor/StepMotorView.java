package com.example.aslen.remotecar.car_controller.step_motor;

import com.example.step_motor.steppermotor.Direction;

public interface StepMotorView {
    void rotate(int degree, Direction direction);
}
