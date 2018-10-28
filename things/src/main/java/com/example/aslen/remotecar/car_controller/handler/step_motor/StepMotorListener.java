package com.example.aslen.remotecar.car_controller.handler.step_motor;

import com.example.step_motor.steppermotor.Direction;

public interface StepMotorListener {
    void rotate(int degree, Direction direction);
}
