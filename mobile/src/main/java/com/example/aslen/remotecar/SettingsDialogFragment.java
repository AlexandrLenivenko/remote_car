package com.example.aslen.remotecar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.aslen.remotecar.settings.SettingsPreferencesService;

import javax.inject.Inject;


public class SettingsDialogFragment extends DialogFragment {
    private static final String TAG = "SettingsDialogFragment";
    @Inject
    SettingsPreferencesService settingsPreferencesService;
    private int speedProgress;
    private int rotationProgress;

    public static SettingsDialogFragment newInstance() {
        return new SettingsDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) requireActivity().getApplication()).getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragemtn_settings, container, false);
        final SeekBar speedSeekBar = inflate.findViewById(R.id.seek_bar_speed);
        final SeekBar rotationSeekBar = inflate.findViewById(R.id.seek_bar_rotation);

        speedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speedProgress = progress;
                Log.d(TAG, "onProgressChanged: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStartTrackingTouch: ");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                saveChanges();
            }
        });

        rotationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rotationProgress = progress;
                Log.d(TAG, "onProgressChanged: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStartTrackingTouch: ");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                saveChanges();
            }
        });
        speedProgress = settingsPreferencesService.getSpeedDelta();
        rotationProgress = settingsPreferencesService.getRotationAngel();
        speedSeekBar.setProgress(speedProgress);
        rotationSeekBar.setProgress(rotationProgress);
        return inflate;
    }

    private void saveChanges() {
        Log.d(TAG, "onStopTrackingTouch: ");
        settingsPreferencesService.setSpeedDelta(speedProgress);
        settingsPreferencesService.setRotationDelta(rotationProgress);
        Toast.makeText(requireContext(), "speed: " + speedProgress +"\n"
                + "rotation:" + rotationProgress, Toast.LENGTH_SHORT).show();
    }
}
