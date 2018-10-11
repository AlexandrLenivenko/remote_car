package com.example.aslen.remotecar.main_screen;

public interface CarView {
    void turnOnOff(boolean isOn);

    void onError(String message);

    void showIp(String ipAddress);
}
