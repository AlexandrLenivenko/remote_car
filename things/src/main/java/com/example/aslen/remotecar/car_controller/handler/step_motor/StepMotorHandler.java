package com.example.aslen.remotecar.car_controller.handler.step_motor;

import com.example.aslen.remotecar.car_controller.handler.BaseHandler;
import com.example.common.models.RemoteControlModel;
import com.example.step_motor.steppermotor.Direction;

public class StepMotorHandler extends BaseHandler<RemoteControlModel> {
    private final StepMotorListener motorListener;

    public StepMotorHandler(StepMotorListener motorListener) {
        this.motorListener = motorListener;
    }


    @Override
    public void handle(RemoteControlModel model) {
        if(model.getRotation() != 0) {
            motorListener.rotate(Math.abs(model.getRotation()), model.getRotation() > 0 ? Direction.CLOCKWISE : Direction.COUNTERCLOCKWISE);
        }

        if (hasNext()) {
            next.handle(model);
        }
    }
}
