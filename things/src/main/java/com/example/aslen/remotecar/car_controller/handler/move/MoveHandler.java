package com.example.aslen.remotecar.car_controller.handler.move;

import com.example.aslen.remotecar.car_controller.handler.BaseHandler;
import com.example.common.models.RemoteControlModel;
import com.example.mylibrary.steppermotor.L298nDriver;

import static com.example.common.models.RemoteControlModel.Contract.IGNORE_DIRECTION_CHANGES;

public class MoveHandler extends BaseHandler<RemoteControlModel> {

    private final MoveListener listener;

    public MoveHandler(MoveListener listener) {
        this.listener = listener;
    }

    @Override
    public void handle(RemoteControlModel model) {

        if (model.getDirection() != IGNORE_DIRECTION_CHANGES ) {
            int speed = Math.abs(model.getDirection());
            speed = speed >=100 ? 100 : speed;
            listener.move(model.getDirection() > 0 ? L298nDriver.Direction.UP : L298nDriver.Direction.DOWN, speed);
        }

        if(hasNext()) {
            next.handle(model);
        }
    }
}
