package com.example.common.parser;

import com.example.common.models.RemoteControlModel;
import com.google.gson.Gson;


public class ClientServerParser implements BaseParser<String, RemoteControlModel> {
    private final Gson gson;

    public ClientServerParser(Gson gson) {
        this.gson = gson;
    }

    @Override
    public RemoteControlModel parse(String string) {
        return gson.fromJson(string, RemoteControlModel.class);
    }

    @Override
    public String parseFromModel(RemoteControlModel model) {
        return gson.toJson(model);
    }
}
