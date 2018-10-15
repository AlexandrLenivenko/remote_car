package com.example.aslen.remotecar.main_screen;

import android.annotation.SuppressLint;

import com.example.common.models.RemoteControlModel;
import com.example.common.presenter.BasePresenter;
import com.example.common.remote_control_service.RemoteControlService;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivityPresenter extends BasePresenter<MainView> {

    private final RemoteControlService remoteControlService;

    public MainActivityPresenter(RemoteControlService remoteControlService) {
        this.remoteControlService = remoteControlService;
    }

    @Override
    public void onStart() {
        runOnView(view -> {
            view.showIpAddress(remoteControlService.getIpAddress());
            view.showConnectViews();
        });
    }

    @Override
    public void onDestroy() {
        remoteControlService.unPublish();
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        remoteControlService.unPublish();
    }

    void onStopMoving() {
        RemoteControlModel remoteControlModel = new RemoteControlModel();
        remoteControlModel.setStop();
        remoteControlService.sendMessage(remoteControlModel);
    }

    void onUp() {
        RemoteControlModel remoteControlModel = new RemoteControlModel();
        remoteControlModel.setUp();
        remoteControlService.sendMessage(remoteControlModel);
    }

    void onDown() {
        RemoteControlModel remoteControlModel = new RemoteControlModel();
        remoteControlModel.setDown();
        remoteControlService.sendMessage(remoteControlModel);
    }

    void onLeft() {
        RemoteControlModel remoteControlModel = new RemoteControlModel();
        remoteControlModel.setLeft();
        remoteControlService.sendMessage(remoteControlModel);
    }

    void onRight() {
        RemoteControlModel remoteControlModel = new RemoteControlModel();
        remoteControlModel.setRight();
        remoteControlService.sendMessage(remoteControlModel);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    void onConnect(String host) {
        runOnView(MainView::showDialog);
        remoteControlService.publish(host)
                .doOnSubscribe(compositeDisposable::add)
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> runOnView(MainView::hideProgress))
                .subscribe(isConnect -> runOnView(MainView::showConnected),
                        throwable -> runOnView(view -> view.error(throwable)));
    }

    void onDisconnectClicked() {
        remoteControlService.unPublish();
        runOnView(view -> {
            view.showIpAddress(remoteControlService.getIpAddress());
            view.showConnectViews();
        });
    }
}
