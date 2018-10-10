package com.example.common.presenter;

public abstract class BasePresenter<V> {

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

    }

    public interface Command<V> {
        void run(V view);
    }
}
