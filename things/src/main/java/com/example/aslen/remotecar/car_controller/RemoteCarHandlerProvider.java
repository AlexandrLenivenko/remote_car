package com.example.aslen.remotecar.car_controller;

import com.example.aslen.remotecar.car_controller.handler.blink.BlinkHandler;
import com.example.aslen.remotecar.car_controller.handler.BaseHandler;
import com.example.aslen.remotecar.car_controller.handler.move.MoveHandler;
import com.example.aslen.remotecar.car_controller.handler.step_motor.StepMotorHandler;
import com.example.aslen.remotecar.car_controller.handler.stop.StopHandler;
import com.example.common.models.RemoteControlModel;

public class RemoteCarHandlerProvider implements CarHandlerProvider<RemoteControlModel, CarPeripheralListener> {

    @Override
    public BaseHandler<RemoteControlModel> getHandler(CarPeripheralListener listener) {
        BaseHandler<RemoteControlModel> controlModelHandler = new StepMotorHandler(listener);
        BlinkHandler blinkHendler = new BlinkHandler(listener);
        controlModelHandler.setNextHandler(blinkHendler);
        StopHandler stopHandler = new StopHandler(listener);
        blinkHendler.setNextHandler(stopHandler);
        stopHandler.setNextHandler(new MoveHandler(listener));
        return controlModelHandler;
    }

    @Override
    public void close() {
        //todo: controlModelHandler.close(); ask Vadim if it is needed
    }
}
