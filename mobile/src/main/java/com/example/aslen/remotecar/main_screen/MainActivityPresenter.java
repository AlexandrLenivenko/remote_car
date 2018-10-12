package com.example.aslen.remotecar.main_screen;

import android.annotation.SuppressLint;

import com.example.common.models.RemoteControlModel;
import com.example.common.presenter.BasePresenter;
import com.example.common.remote_control_service.RemoteControlService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivityPresenter extends BasePresenter<MainView> {

    private final RemoteControlService remoteControlService;
    private final CompositeDisposable compositeDisposable;
    private final RemoteControlModel remoteControlModel;

    public MainActivityPresenter(RemoteControlService remoteControlService) {
        this.remoteControlService = remoteControlService;
        compositeDisposable = new CompositeDisposable();
        remoteControlModel = new RemoteControlModel();
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
        super.onDestroy();
        remoteControlService.unPublish();
    }

    @Override
    public void onStop() {
        super.onStop();
        remoteControlService.unPublish();
    }

    public void onStopMoving() {
        remoteControlModel.setStop();
        remoteControlService.sendMessage(remoteControlModel);
    }

    public void onUp() {
        remoteControlModel.setUp();
        remoteControlService.sendMessage(remoteControlModel);
    }

    public void onDown() {
        remoteControlModel.setDown();
        remoteControlService.sendMessage(remoteControlModel);
    }

    public void onLeft() {
        remoteControlModel.setLeft();
        remoteControlService.sendMessage(remoteControlModel);
    }

    public void onRight() {
        remoteControlModel.setRight();
        remoteControlService.sendMessage(remoteControlModel);
    }

    @SuppressLint("CheckResult")
    public void onConnect(String host) {
        runOnView(view -> view.showDialog());
        remoteControlService.publish(host)
                .doOnSubscribe(compositeDisposable::add)
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> runOnView(MainView::hideProgress))
                .subscribe(isConnect -> runOnView(MainView::showConnected),
                        throwable -> runOnView(view -> view.error(throwable)));
    }

    public void onDisconnectClicked() {
        remoteControlService.unPublish();
        runOnView(view -> {
            view.showIpAddress(remoteControlService.getIpAddress());
            view.showConnectViews();
        });
    }
}
