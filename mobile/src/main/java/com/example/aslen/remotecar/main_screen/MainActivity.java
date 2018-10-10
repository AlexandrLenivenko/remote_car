package com.example.aslen.remotecar.main_screen;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.aslen.remotecar.App;
import com.example.aslen.remotecar.R;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainView {

    @Inject
    protected MainActivityPresenter presenter;

    private TextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((App) getApplication()).getAppComponent().inject(this);
        messageTextView = findViewById(R.id.tv_message);
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
        super.onDestroy();
    }

    @Override
    public void showIpAdress(String ipAddress) {
        messageTextView.setText(ipAddress);
    }
}
