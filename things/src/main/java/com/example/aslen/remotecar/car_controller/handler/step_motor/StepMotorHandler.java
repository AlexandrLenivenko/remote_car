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
        if (model.isLeft()) {
            motorListener.rotate(30, Direction.CLOCKWISE);
        }else if (model.isRight()) {
            motorListener.rotate(30, Direction.COUNTERCLOCKWISE);
        }

        if (hasNext()) {
            next.handle(model);
        }
    }
}
