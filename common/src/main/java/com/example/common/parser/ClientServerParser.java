package com.example.common.parser;

import com.example.common.models.RemoteControlModel;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;


public class ClientServerParser implements BaseParser<String, RemoteControlModel> {
    private final Gson gson;

    public ClientServerParser(Gson gson) {
        this.gson = gson;
    }

    @Override
    public RemoteControlModel parse(String string) {
        JsonReader reader = new JsonReader(new StringReader(string));
        reader.setLenient(true);
        return gson.fromJson(string, RemoteControlModel.class);
    }

    @Override
    public String parseFromModel(RemoteControlModel model) {
        return gson.toJson(model);
    }
}
