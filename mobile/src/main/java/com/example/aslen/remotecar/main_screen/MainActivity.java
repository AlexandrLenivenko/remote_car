package com.example.aslen.remotecar.main_screen;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aslen.remotecar.App;
import com.example.aslen.remotecar.R;
import com.example.joy_stick.JoystickView;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainView, JoystickView.JoystickListener {

    @Inject
    protected MainActivityPresenter presenter;

    private TextView messageTextView;
    private EditText serverIpAddress;
    private ProgressDialog progressDialog;
    private Group playGroup;
    private Group connectGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((App) getApplication()).getAppComponent().inject(this);

        findViews();
        presenter.onAttachView(this);
        createDialog();
    }

    private void findViews() {
        serverIpAddress = findViewById(R.id.edt_server_ip);
        JoystickView joystickView = findViewById(R.id.joystick);
        joystickView.setJoystickListener(this);
/*        findViewById(R.id.img_up).setOnClickListener(view -> presenter.onUp());
        findViewById(R.id.img_down).setOnClickListener(view -> presenter.onDown());
        findViewById(R.id.img_left).setOnClickListener(view -> presenter.onLeft());
        findViewById(R.id.img_right).setOnClickListener(view -> presenter.onRight());
        findViewById(R.id.img_stop).setOnClickListener(view -> presenter.onStopMoving());*/
        findViewById(R.id.btn_connect).setOnClickListener(view -> presenter.onConnect(serverIpAddress.getText().toString()));
        messageTextView = findViewById(R.id.tv_message);
        messageTextView.setOnClickListener(view -> presenter.onDisconnectClicked());

        playGroup = findViewById(R.id.group_play);
        connectGroup = findViewById(R.id.group_connect);
    }

    private void createDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Connect to server");
        progressDialog.setMessage("Connecting...");
    }


    @Override
    protected void onPause() {
        presenter.onDetachView();
        super.onPause();
    }

    @Override
    protected void onStart() {
        presenter.onAttachView(this);
        presenter.onStart();
        super.onStart();
    }

    @Override
    public void onLeft() {
        presenter.onLeft();
    }

    @Override
    public void onRight() {
        presenter.onRight();
    }

    @Override
    public void onForward() {
        presenter.onUp();
    }

    @Override
    public void onBackward() {
        presenter.onDown();
    }

    @Override
    public void onStopMoving() {
        presenter.onStopMoving();
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
    public void showIpAddress(String ipAddress) {
        messageTextView.setText(String.format("Your ip %s", serverIpAddress.getText()));
        playGroup.setVisibility(View.GONE);
        connectGroup.setVisibility(View.VISIBLE);
    }

    @Override
    public void error(Throwable throwable) {
        messageTextView.setText(throwable.getMessage());
        playGroup.setVisibility(View.GONE);
        connectGroup.setVisibility(View.VISIBLE);
        messageTextView.setTextColor(Color.RED);
    }

    @Override
    public void showConnected() {
        messageTextView.setText(String.format("Connected to %s", serverIpAddress.getText()));
        messageTextView.setTextColor(Color.GREEN);
        playGroup.setVisibility(View.VISIBLE);
        connectGroup.setVisibility(View.GONE);
        hideProgress();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void showDialog() {
        progressDialog.show();
    }

    @Override
    public void showConnectViews() {
        playGroup.setVisibility(View.GONE);
        connectGroup.setVisibility(View.VISIBLE);
    }
}
