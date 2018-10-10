package com.example.aslen.remotecar.main_screen;

import com.example.common.presenter.BasePresenter;
import com.example.common.remote_control_service.RemoteControlService;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class CarPresenter extends BasePresenter<CarView> {

    private final RemoteControlService service;

    public CarPresenter(RemoteControlService service) {
        this.service = service;
    }

    @Override
    public void onStart() {
        Observable.just(false, true, false, true, false, true, false, true, false, true)
                .delay(1, TimeUnit.SECONDS, Schedulers.trampoline())
                /*.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())*/
                .subscribe(isOn -> runOnView(view -> view.turnOnOff(isOn)));

    }
}
