package com.example.aslen.remotecar.main_screen;

import android.annotation.SuppressLint;

import com.example.common.models.RemoteControlModel;
import com.example.common.presenter.BasePresenter;
import com.example.common.remote_control_service.RemoteControlService;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

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
                .subscribe(this::show, throwable -> runOnView(view -> view.onError(throwable.getMessage())));

    }


    /**
     * @param model stop - turn off
     *              up   - 1
     *              down - 2
     *              left - 3
     *              right- 4
     */
    private void show(RemoteControlModel model) {
        if (model.isStop()) {
            runOnView(view -> view.turnOnOff(false));
            return;
        }

        if (model.isUp()) {
            blink(Arrays.asList(true, false));
            return;
        } else {

        }
        if (model.isDown()) {
            blink(Arrays.asList(true, false, true, false));
            return;
        }
        if (model.isLeft()) {
            blink(Arrays.asList(true, false, true, false, true, false));
            return;
        }

        if (model.isRight()) {
            blink(Arrays.asList(true, false, true, false, true, false, true, false));
        }
    }

    @SuppressLint("CheckResult")
    private void blink(List<Boolean> first) {
        io.reactivex.Observable.fromIterable(first).delay(1500, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                .doOnSubscribe(compositeDisposable::add)
                .subscribe(aBoolean -> runOnView(view -> view.turnOnOff(aBoolean)));
    }
}
