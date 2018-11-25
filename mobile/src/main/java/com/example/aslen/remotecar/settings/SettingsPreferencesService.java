package com.example.aslen.remotecar.settings;

public interface SettingsPreferencesService {
    String getIpAddress();

    int getSpeedDelta();

    int getRotationAngel();

    void saveServerIpAddress(String host);

    void setSpeedDelta(int speedProgress);

    void setRotationDelta(int rotationProgress);
}
