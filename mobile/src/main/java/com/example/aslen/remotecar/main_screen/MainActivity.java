package com.example.aslen.remotecar.main_screen;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aslen.remotecar.App;
import com.example.aslen.remotecar.R;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainView {

    @Inject
    protected MainActivityPresenter presenter;

    private TextView messageTextView;
    private EditText serverIpAddress;
    private View connectButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((App) getApplication()).getAppComponent().inject(this);
        serverIpAddress = findViewById(R.id.edt_server_ip);
        findViewById(R.id.img_up).setOnClickListener(view -> presenter.onUp());
        findViewById(R.id.img_down).setOnClickListener(view -> presenter.onDown());
        findViewById(R.id.img_left).setOnClickListener(view -> presenter.onLeft());
        findViewById(R.id.img_right).setOnClickListener(view -> presenter.onRight());
        findViewById(R.id.img_stop).setOnClickListener(view -> presenter.onStopMoving());
        connectButton = findViewById(R.id.btn_connect);
        connectButton.setOnClickListener(view -> presenter.onConnect(serverIpAddress.getText().toString()));
        messageTextView = findViewById(R.id.tv_message);
        presenter.onAttachView(this);

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
    public void showIpAddress(String ipAddress) {
        messageTextView.setText(String.format("Your ip %s", serverIpAddress.getText()));
    }

    @Override
    public void error(Throwable throwable) {
        messageTextView.setText(throwable.getMessage());
        serverIpAddress.setVisibility(View.VISIBLE);
        connectButton.setVisibility(View.VISIBLE);
        messageTextView.setTextColor(Color.RED);
    }

    @Override
    public void showConnected() {
        messageTextView.setText(String.format("Connected to %s", serverIpAddress.getText()));
        serverIpAddress.setVisibility(View.GONE);
        connectButton.setVisibility(View.GONE);
        messageTextView.setTextColor(Color.GREEN);
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
}
