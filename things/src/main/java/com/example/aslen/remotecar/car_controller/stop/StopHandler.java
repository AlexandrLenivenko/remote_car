package com.example.aslen.remotecar.car_controller.stop;

import com.example.aslen.remotecar.car_controller.BaseHandler;
import com.example.common.models.RemoteControlModel;

public class StopHandler extends BaseHandler<RemoteControlModel> {
    private final StopView view;

    public StopHandler(StopView view) {
        this.view = view;
    }

    @Override
    public void handle(RemoteControlModel model) {
        if(model.isStop()) {
            view.stop();
        }
        if(hasNext()) {
            next.handle(model);
        }
    }
}
