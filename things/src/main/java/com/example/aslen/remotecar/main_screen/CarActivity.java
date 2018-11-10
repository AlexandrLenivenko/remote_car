package com.example.aslen.remotecar.main_screen;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.aslen.remotecar.App;
import com.example.aslen.remotecar.R;
import com.example.step_motor.steppermotor.Direction;
import com.google.android.things.pio.PeripheralManager;

import java.util.List;

import javax.inject.Inject;

public class CarActivity extends Activity implements CarView {
    private static final String TAG = "CarActivity";

    @Inject
    protected CarPresenter presenter;
    private TextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((App) getApplication()).getAppComponent().inject(this);

        messageTextView = findViewById(R.id.tv_message);

        showPins();

        presenter.onAttachView(this);
    }

    private void showPins() {
        PeripheralManager manager = PeripheralManager.getInstance();
        final List<String> portList = manager.getGpioList();
        if (portList.isEmpty()) {
            Log.i(TAG, "No GPIO port available on this device.");
        } else {
            Log.i(TAG, "List of available ports: " + portList);
        }
    }

    @Override
    protected void onPause() {
        presenter.onDetachView();
        super.onPause();
    }

    @Override
    protected void onStart() {
        presenter.onStart();
        super.onStart();
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void turnOnOff(boolean isOn) {
    }

    @Override
    public void onError(String message) {
        messageTextView.setText(message);
    }

    @Override
    public void showIp(String ipAddress) {
        messageTextView.setText(ipAddress);
    }

    @Override
    public void rotate(int degree, Direction direction) {
    }

    public void close(View view) {
        finish();
    }
}
