package com.example.common.presenter;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<V> {
    protected final CompositeDisposable compositeDisposable;

    public BasePresenter() {
        compositeDisposable = new CompositeDisposable();
    }

    private V view;

    public void onAttachView(V view) {
        this.view = view;
    }

    public void onDetachView() {
        view = null;
    }

    public void runOnView(Command<V> command) {
        if (view != null) {
            command.run(view);
        }
    }

    public void onStart() {

    }

    public void onStop() {

    }

    public void onDestroy() {
        compositeDisposable.dispose();
    }

    public interface Command<V> {
        void run(V view);
    }
}
