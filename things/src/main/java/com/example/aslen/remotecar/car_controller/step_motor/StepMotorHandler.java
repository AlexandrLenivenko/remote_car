package com.example.aslen.remotecar.car_controller.step_motor;

import com.example.aslen.remotecar.car_controller.BaseHandler;
import com.example.common.models.RemoteControlModel;
import com.example.step_motor.steppermotor.Direction;

public class StepMotorHandler extends BaseHandler<RemoteControlModel> {
    private final StepMotorView view;

    public StepMotorHandler(StepMotorView view) {
        this.view = view;
    }


    @Override
    public void handle(RemoteControlModel model) {
        if (model.isLeft()) {
            view.rotate(30, Direction.CLOCKWISE);
        }else if (model.isRight()) {
            view.rotate(30, Direction.COUNTERCLOCKWISE);
        }

        if (hasNext()) {
            next.handle(model);
        }
    }
}
