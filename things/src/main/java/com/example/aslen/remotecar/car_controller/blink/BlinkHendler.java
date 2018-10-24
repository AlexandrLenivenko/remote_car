package com.example.aslen.remotecar.car_controller.blink;

import com.example.aslen.remotecar.car_controller.BaseHandler;
import com.example.common.models.RemoteControlModel;

import java.util.Arrays;

public class BlinkHendler extends BaseHandler<RemoteControlModel> {
    private final BlinkView view;

    public BlinkHendler(BlinkView view) {
        this.view = view;
    }


    @Override
    public void handle(RemoteControlModel model) {
        if (model.isUp()) {
            view.blink(Arrays.asList(true, false));
        } else if (model.isDown()) {
            view.blink(Arrays.asList(true, false, true, false));
        }

        if (hasNext()) {
            next.handle(model);
        }
    }
}
