package com.example.common.models;

public class RemoteControlModel {

    private boolean stop;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    public RemoteControlModel() {
    }

    public RemoteControlModel(boolean stop, boolean up, boolean down, boolean left, boolean right) {
        this.stop = stop;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop() {
        this.stop = !stop;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp() {
        this.up = true;
        this.down = false;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown() {
        this.down = true;
        this.up = false;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft() {
        this.left = true;
        this.right = false;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight() {
        this.right = true;
        this.left = false;
    }
}
