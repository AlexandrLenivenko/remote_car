package com.example.common.models;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static com.example.common.models.RemoteControlModel.Contract.IGNORE_DIRECTION_CHANGES;
import static com.example.common.models.RemoteControlModel.Contract.PR_100;
import static com.example.common.models.RemoteControlModel.Contract.PR_25;
import static com.example.common.models.RemoteControlModel.Contract.PR_50;
import static com.example.common.models.RemoteControlModel.Contract.PR_75;
import static com.example.common.models.RemoteControlModel.Contract.ROTATION_ANGLE;
import static com.example.common.models.RemoteControlModel.Contract.STOP;
import static java.lang.annotation.RetentionPolicy.SOURCE;

public class RemoteControlModel {

   private final int direction;
   private final int rotation;

    public RemoteControlModel(int direction,int rotation) {
        this.direction = direction;
        this.rotation = rotation;
    }

    public int getDirection() {
        return direction;
    }

    public int getRotation() {
        return rotation;
    }
    @Retention(SOURCE)
    @IntDef({PR_25, PR_50, PR_75, PR_100, STOP, IGNORE_DIRECTION_CHANGES, ROTATION_ANGLE})
    @interface RemoteContract {}
    public interface Contract{
        int STOP = 0;
        int PR_25 = 25;
        int PR_50 = 50;
        int PR_75 = 75;
        int PR_100 = 100;
        int IGNORE_DIRECTION_CHANGES = -1000;
        int ROTATION_ANGLE = 10;
    }

}
