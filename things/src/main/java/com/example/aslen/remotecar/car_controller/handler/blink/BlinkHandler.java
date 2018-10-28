package com.example.aslen.remotecar.car_controller.handler.blink;

import com.example.aslen.remotecar.car_controller.handler.BaseHandler;
import com.example.common.models.RemoteControlModel;

import java.util.Arrays;

public class BlinkHandler extends BaseHandler<RemoteControlModel> {
    private BlinkListener blinkListener;

    public BlinkHandler(BlinkListener blinkListener) {
        this.blinkListener = blinkListener;
    }


    @Override
    public void handle(RemoteControlModel model) {
        if (model.isUp()) {
            blinkListener.blink(Arrays.asList(true, false));
        } else if (model.isDown()) {
            blinkListener.blink(Arrays.asList(true, false, true, false));
        }

        if (hasNext()) {
            next.handle(model);
        }
    }
}
