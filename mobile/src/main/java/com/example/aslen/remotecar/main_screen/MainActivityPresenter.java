package com.example.aslen.remotecar.main_screen;

import android.annotation.SuppressLint;

import com.example.common.presenter.BasePresenter;
import com.example.common.remote_control_service.RemoteControlService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivityPresenter extends BasePresenter<MainView> {

    private final RemoteControlService remoteControlService;
    private final CompositeDisposable compositeDisposable;

    public MainActivityPresenter(RemoteControlService remoteControlService) {
        this.remoteControlService = remoteControlService;
        compositeDisposable = new CompositeDisposable();

    }

    @Override
    public void onStart() {
        runOnView(view -> view.showIpAddress(remoteControlService.getIpAddress()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onStop() {
        super.onStop();
        remoteControlService.unPublish();
    }

    public void onStopMoving() {
        remoteControlService.sendMessage();
    }

    public void onUp() {

    }

    public void onDown() {

    }

    public void onLeft() {

    }

    public void onRight() {

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
}
