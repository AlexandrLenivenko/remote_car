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
    private final PorterDuffColorFilter whiteColorFilter;
    private final PorterDuffColorFilter greenColorFilter;
    private int left;
    private int top;
    private boolean selected;
    private int increase;

    public ArrowModel(Bitmap bitmap, Paint paint) {
        this.bitmap = bitmap;
        this.paint = paint;
        whiteColorFilter = new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        greenColorFilter = new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
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
        paint.setColorFilter(color == Color.GREEN ? greenColorFilter : whiteColorFilter);
    }

    public void setSelected(boolean selected) {
        setColor(selected ? Color.GREEN : Color.WHITE);
        increase = 0;
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean increase() {
        increase = increase < 100 ? increase+25 : 100;
        return increase < 100;
    }
}
