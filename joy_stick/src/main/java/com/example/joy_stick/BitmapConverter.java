package com.example.joy_stick;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.VectorDrawable;
import android.support.annotation.DrawableRes;

public class BitmapConverter {

    private final Resources resources;

    public BitmapConverter(Resources resources) {
        this.resources = resources;
    }


    public Bitmap createBitmap(@DrawableRes int drawableId) {
        return createBitmapFromVectorDrawable((VectorDrawable) resources.getDrawable(drawableId));
    }

    private Bitmap createBitmapFromVectorDrawable(VectorDrawable vectorDrawable) {
        final Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        final Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }
}
