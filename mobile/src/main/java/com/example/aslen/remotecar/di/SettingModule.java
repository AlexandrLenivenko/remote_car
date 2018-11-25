package com.example.aslen.remotecar.di;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.aslen.remotecar.settings.SettingsPreferencesService;
import com.example.aslen.remotecar.settings.SettingsPreferencesServiceImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingModule {

    @Provides
    SettingsPreferencesService provideSettingsPreferencesService(SharedPreferences sharedPreferences) {
        return new SettingsPreferencesServiceImpl(sharedPreferences);
    }

    @Provides
    SharedPreferences provideSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

}
