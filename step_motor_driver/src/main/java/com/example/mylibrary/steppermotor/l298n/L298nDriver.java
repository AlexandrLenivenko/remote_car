package com.example.mylibrary.steppermotor.l298n;

public interface L298nDriver extends AutoCloseable {
    void move(int direction, int speed);

    void stop();

    @Override
    void close() throws Exception;

    interface Direction {
        int UP = 0;
        int DOWN = 1;
        int STOP = 2;
    }
}
