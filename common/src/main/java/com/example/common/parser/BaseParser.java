package com.example.common.parser;

import com.example.common.models.RemoteControlModel;
import com.google.gson.JsonSyntaxException;

public interface BaseParser<I, M> {
    RemoteControlModel parse(I input) throws JsonSyntaxException;

    String parseFromModel(M model);
}
