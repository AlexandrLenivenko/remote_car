package com.example.aslen.remotecar.car_controller.handler.stop;

import com.example.aslen.remotecar.car_controller.handler.BaseHandler;
import com.example.common.models.RemoteControlModel;

public class StopHandler extends BaseHandler<RemoteControlModel> {
    private final StopListener stopListener;

    public StopHandler(StopListener stopListener) {
        this.stopListener = stopListener;
    }

    @Override
    public void handle(RemoteControlModel model) {
        if(model.isStop()) {
            stopListener.stop();
        }
        if(hasNext()) {
            next.handle(model);
        }
    }
}
