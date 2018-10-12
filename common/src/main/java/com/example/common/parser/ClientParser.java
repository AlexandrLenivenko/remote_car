package com.example.common.parser;

import com.example.common.models.RemoteControlModel;
import com.google.gson.Gson;


public class ClientParser {
    private static final String TAG = "ClientParser";

    private final RemoteControlModel remoteControlModel;
    private final Gson gson;

    public ClientParser() {
        remoteControlModel = new RemoteControlModel();
        gson = new Gson();
    }

    public RemoteControlModel parse(String string) {
        return gson.fromJson(string, RemoteControlModel.class);
    }

    public String parseFromModel(RemoteControlModel model) {
        return gson.toJson(model);
    }
}
