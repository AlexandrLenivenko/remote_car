package com.example.aslen.remotecar.main_screen;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.aslen.remotecar.App;
import com.example.aslen.remotecar.R;
import com.example.aslen.remotecar.steppermotor.driver.uln2003.driver.ULN2003Resolution;
import com.example.aslen.remotecar.steppermotor.driver.uln2003.motor.ULN2003StepperMotor;
import com.example.mylibrary.steppermotor.BlinckingDriver;
import com.example.step_motor.steppermotor.Direction;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

public class CarActivity extends Activity implements CarView {
    private static final String TAG = "CarActivity";

    @Inject
    protected CarPresenter presenter;
    private TextView messageTextView;
    private ULN2003StepperMotor uln2003StepperMotor;
    private boolean isClockWise;
    private BlinckingDriver blinckingDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((App) getApplication()).getAppComponent().inject(this);

        messageTextView = findViewById(R.id.tv_message);

        showPins();

        //uln2003StepperMotor = new ULN2003StepperMotor("BCM4", "BCM17", "BCM27", "BCM22");
        String pinName = "BCM2";
       // blinckingDriver = new BlinckingDriver(pinName);

        findViewById(R.id.btn_rotate).setOnClickListener(view -> {
            isClockWise = !isClockWise;
            uln2003StepperMotor.abortAllRotations();
            uln2003StepperMotor.rotate(270.0, isClockWise ? Direction.CLOCKWISE : Direction.COUNTERCLOCKWISE, ULN2003Resolution.FULL.getId(), 100);

        });
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
        uln2003StepperMotor.close();
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
        blinckingDriver.close();
        super.onDestroy();
    }

    @Override
    public void turnOnOff(boolean isOn) {
        blinckingDriver.setState(isOn);
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
        uln2003StepperMotor.abortAllRotations();
        uln2003StepperMotor.rotate(degree, direction, ULN2003Resolution.FULL.getId(), 50);
    }

    public void close(View view) {
        finish();
    }
}
