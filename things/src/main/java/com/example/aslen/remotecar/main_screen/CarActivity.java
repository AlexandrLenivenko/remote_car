package com.example.aslen.remotecar.main_screen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aslen.remotecar.App;
import com.example.aslen.remotecar.R;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;

import javax.inject.Inject;

public class CarActivity extends Activity implements CarView {

    @Inject
    protected CarPresenter presenter;
    private Gpio ledGpio;
    private TextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((App) getApplication()).getAppComponent().inject(this);

        messageTextView = findViewById(R.id.tv_message);

        try {
            String pinName = "BCM17";
            ledGpio = PeripheralManager.getInstance().openGpio(pinName);
            ledGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        } catch (IOException e) {
            //Log.e(TAG, "Error on PeripheralIO API", e);
        }

        presenter.onAttachView(this);
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
        try {
            ledGpio.setValue(false);
            ledGpio.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void turnOnOff(boolean isOn) {
        try {
            ledGpio.setValue(isOn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String message) {
        messageTextView.setText(message);
    }

    @Override
    public void showIp(String ipAddress) {
        messageTextView.setText(ipAddress);
    }

    public void close(View view) {
        finish();
    }
}
