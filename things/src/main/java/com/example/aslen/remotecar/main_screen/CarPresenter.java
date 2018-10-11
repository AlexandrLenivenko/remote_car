package com.example.aslen.remotecar.main_screen;

import android.annotation.SuppressLint;

import com.example.common.presenter.BasePresenter;
import com.example.common.remote_control_service.RemoteControlService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class CarPresenter extends BasePresenter<CarView> {

    private final RemoteControlService service;
    private final CompositeDisposable compositeDisposable;
    private boolean isOn = false;

    public CarPresenter(RemoteControlService service) {
        this.service = service;
        compositeDisposable = new CompositeDisposable();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onStart() {
        runOnView(view -> view.showIp(service.getIpAddress()));
        service.subscribe()
                .doOnSubscribe(compositeDisposable::add)
                //.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    isOn = !isOn;
                    runOnView(view -> view.turnOnOff(isOn));
                }, throwable -> runOnView(view -> view.onError(throwable.getMessage())));

    }
}
