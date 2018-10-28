package com.example.aslen.remotecar.car_controller.handler.move;

import com.example.aslen.remotecar.car_controller.handler.BaseHandler;
import com.example.common.models.RemoteControlModel;
import com.example.mylibrary.steppermotor.L298nDriver;

public class MoveHandler extends BaseHandler<RemoteControlModel> {

    private final MoveListener listener;

    public MoveHandler(MoveListener listener) {
        this.listener = listener;
    }

    @Override
    public void handle(RemoteControlModel model) {
        if (model.isUp()) {
            listener.move(L298nDriver.Direction.UP);
        }
        if (model.isDown()) {
            listener.move(L298nDriver.Direction.DOWN);
        }

        if(hasNext()) {
            next.handle(model);
        }
    }
}
