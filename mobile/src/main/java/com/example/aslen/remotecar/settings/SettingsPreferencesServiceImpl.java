package com.example.aslen.remotecar.settings;

import android.content.SharedPreferences;
import android.util.Log;

import static com.example.common.models.RemoteControlModel.Contract.PR_25;
import static com.example.common.models.RemoteControlModel.Contract.ROTATION_ANGLE;

public class SettingsPreferencesServiceImpl implements SettingsPreferencesService {
    private static final String TAG = "SettingsPreferencesServ";
    public static final String IP_KEY = "IP";
    public static final String IP_DEF_VALUE = "192.168.1.100";
    private static final String SPEED_KEY = "SPEED_KEY";
    private static final String ROTATION_KEY = "ROTATION_KEY";

    private final SharedPreferences sharedPreferences;

    public SettingsPreferencesServiceImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }


    @Override
    public String getIpAddress() {
        return sharedPreferences.getString(IP_KEY, IP_DEF_VALUE);
    }

    @Override
    public int getSpeedDelta() {
        return sharedPreferences.getInt(SPEED_KEY, PR_25);
    }

    @Override
    public int getRotationAngel() {
        return sharedPreferences.getInt(ROTATION_KEY, ROTATION_ANGLE);
    }

    @Override
    public void saveServerIpAddress(String host) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(IP_KEY, host);
        edit.apply();
    }

    @Override
    public void setSpeedDelta(int speedProgress) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(SPEED_KEY, speedProgress);
        edit.apply();
    }

    @Override
    public void setRotationDelta(int rotationProgress) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(ROTATION_KEY, rotationProgress);
        edit.apply();
    }
}
