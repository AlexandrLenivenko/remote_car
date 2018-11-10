package com.example.mylibrary.steppermotor.light_driver.light_command;


import com.example.mylibrary.steppermotor.light_driver.BaseCommand;
import com.google.android.things.pio.Gpio;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TwoStateLightDriver extends BaseCommand {

    private Disposable disposable;
    private boolean state;

    public TwoStateLightDriver(Gpio[] gpios) {
        super(gpios);
    }

    @Override
    public void execute() {
        Observable.interval(450, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Long aLong) {
                state = !state;
                for (int i = 0; i < gpios.length; i++) {
                    setState(gpios[i], state);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void cancel() {
        state = false;
        if(disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.cancel();
    }

    @Override
    public void destroy() {
        if(disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.destroy();
    }
}
