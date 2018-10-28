package com.example.aslen.remotecar.car_controller;

import com.example.aslen.remotecar.car_controller.handler.BaseHandler;

public interface CarHandlerProvider<M, L> {

    BaseHandler<M> getHandler(L listener);

    void close();
}
