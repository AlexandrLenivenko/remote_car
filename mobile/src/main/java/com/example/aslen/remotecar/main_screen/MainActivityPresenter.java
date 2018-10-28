package com.example.aslen.remotecar.main_screen;

import android.annotation.SuppressLint;

import com.example.common.models.RemoteControlModel;
import com.example.common.presenter.BasePresenter;
import com.example.common.remote_control_service.RemoteControlService;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.example.common.models.RemoteControlModel.Contract.IGNORE_DIRECTION_CHANGES;
import static com.example.common.models.RemoteControlModel.Contract.PR_25;
import static com.example.common.models.RemoteControlModel.Contract.ROTATION_ANGLE;
import static com.example.common.models.RemoteControlModel.Contract.STOP;

public class MainActivityPresenter extends BasePresenter<MainView> {

    private final RemoteControlService remoteControlService;
    private RemoteControlModel model;

    public MainActivityPresenter(RemoteControlService remoteControlService) {
        this.remoteControlService = remoteControlService;
        model = new RemoteControlModel(0, 0);
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
        model = new RemoteControlModel(STOP, 0);
        remoteControlService.sendMessage(model);
    }

    void onUp() {
        RemoteControlModel remoteControlModel = new RemoteControlModel(model.getDirection() + PR_25, 0);
        model = remoteControlModel;
        remoteControlService.sendMessage(remoteControlModel);
    }

    void onDown() {
        RemoteControlModel remoteControlModel = new RemoteControlModel(model.getDirection() - PR_25, 0);
        model = remoteControlModel;
        remoteControlService.sendMessage(remoteControlModel);
    }

    void onLeft() {
        RemoteControlModel remoteControlModel = new RemoteControlModel(IGNORE_DIRECTION_CHANGES, -ROTATION_ANGLE);
        remoteControlService.sendMessage(remoteControlModel);
    }

    void onRight() {
        RemoteControlModel remoteControlModel = new RemoteControlModel(IGNORE_DIRECTION_CHANGES, ROTATION_ANGLE);
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
