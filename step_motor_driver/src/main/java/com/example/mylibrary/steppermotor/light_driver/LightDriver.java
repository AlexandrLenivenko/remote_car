package com.example.mylibrary.steppermotor.light_driver;

public interface LightDriver {

    void invoke(int state);
    void destroy();
}
