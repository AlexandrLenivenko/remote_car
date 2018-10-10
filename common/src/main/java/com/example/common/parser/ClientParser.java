package com.example.common.parser;

import com.example.common.models.RemoteControlModel;


public class ClientParser {

    private final RemoteControlModel remoteControlModel;

    public ClientParser() {
        remoteControlModel = new RemoteControlModel();
    }

    public RemoteControlModel parse(String string) {
        switch (Integer.valueOf(string)) {
            //todo
        }
        return remoteControlModel;
    }

    public String parseFromModel(RemoteControlModel model) {
        return "";//todo
    }
}
