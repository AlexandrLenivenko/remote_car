package com.example.aslen.remotecar.car_controller;

import com.example.aslen.remotecar.car_controller.handler.blink.BlinkListener;
import com.example.aslen.remotecar.car_controller.handler.move.MoveListener;
import com.example.aslen.remotecar.car_controller.handler.step_motor.StepMotorListener;
import com.example.aslen.remotecar.car_controller.handler.stop.StopListener;

public interface CarPeripheralListener extends StepMotorListener, BlinkListener, StopListener, AutoCloseable, MoveListener {
}
