package com.example.aslen.remotecar.car_controller.handler;

public abstract class BaseHandler<M> {
    protected BaseHandler<M> next;

    protected boolean hasNext() {
        return next != null;
    }

    public void setNextHandler(BaseHandler<M> next) {
        this.next = next;
    }

    public abstract void handle(M model);
}
