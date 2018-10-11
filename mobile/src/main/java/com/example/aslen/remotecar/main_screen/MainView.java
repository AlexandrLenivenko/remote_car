package com.example.aslen.remotecar.main_screen;

public interface MainView {
    void showIpAddress(String ipAddress);

    void error(Throwable throwable);

    void showConnected();

    void hideProgress();

    void showDialog();
}
