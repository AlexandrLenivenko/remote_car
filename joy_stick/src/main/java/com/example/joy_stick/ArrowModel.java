package com.example.joy_stick;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.ColorInt;

public class ArrowModel {

    private final Bitmap bitmap;
    private final Paint paint;
    private int left;
    private int top;
    private boolean selected;

    public ArrowModel(Bitmap bitmap, Paint paint) {
        this.bitmap = bitmap;
        this.paint = paint;
        setColor(Color.WHITE);
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setColor(@ColorInt int color) {
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
    }

    public void setSelected(boolean selected) {
        setColor(selected ? Color.GREEN : Color.WHITE);
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }
}
