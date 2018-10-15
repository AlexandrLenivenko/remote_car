package com.example.common.parser;

import com.example.common.models.RemoteControlModel;

public interface BaseParser<I, M> {
    RemoteControlModel parse(I input);

    String parseFromModel(M model);
}
