package com.example.common.remote_control_service;

import com.example.common.models.RemoteControlModel;

import io.reactivex.Observable;

public interface RemoteControlService {

    Observable<RemoteControlModel> subscribe();

    void publish(RemoteControlModel model, String host);

    String getIpAddress();

    void unSubscribe();

    void unPublish();
}
