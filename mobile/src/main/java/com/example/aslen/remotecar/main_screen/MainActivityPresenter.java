package com.example.aslen.remotecar.main_screen;

import com.example.common.presenter.BasePresenter;
import com.example.common.remote_control_service.RemoteControlService;

public class MainActivityPresenter extends BasePresenter<MainView> {

    private final RemoteControlService remoteControlService;

    public MainActivityPresenter(RemoteControlService remoteControlService) {
        this.remoteControlService = remoteControlService;
    }

    @Override
    public void onStart() {
        runOnView(view -> view.showIpAdress(remoteControlService.getIpAddress()));
    }
}
