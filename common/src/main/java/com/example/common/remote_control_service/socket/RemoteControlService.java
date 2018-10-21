package com.example.common.remote_control_service.socket;

import com.example.common.models.RemoteControlModel;

import io.reactivex.Observable;

public interface RemoteControlService {

    Observable<RemoteControlModel> subscribe();

    void publish(RemoteControlModel model, String host);

    void destroy();

    String getIpAddress();
}
