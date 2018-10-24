package com.example.aslen.remotecar.car_controller;

import com.example.aslen.remotecar.car_controller.blink.BlinkView;
import com.example.aslen.remotecar.car_controller.step_motor.StepMotorView;
import com.example.aslen.remotecar.car_controller.stop.StopView;

public interface CarPeripheralView extends StepMotorView, BlinkView, StopView, AutoCloseable{
}
