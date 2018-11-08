package com.example.joy_stick;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class JoystickView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private static final String TAG = "JoystickView";
    private static final int SIZE = 60;
    private static final int MARGIN = 600;
    private final ArrowModel[] arrowModels = new ArrowModel[5];
    private SparseArray<PointF> activePointers;
    private int[] colors = {Color.BLUE, Color.GREEN, Color.MAGENTA,
            Color.BLACK, Color.CYAN, Color.GRAY, Color.RED, Color.DKGRAY,
            Color.LTGRAY, Color.YELLOW};
    private GestureDetectorCompat gestureDetectorCompat;
    private Vibrator vibrator;
    private BitmapConverter bitmapConventor;
    private Bitmap arrowDown;
    private Bitmap arrowUp;
    private Bitmap arrowLeft;
    private Bitmap arrowRight;
    private Paint paint;
    private Bitmap stop;
    private Paint colorPaint;
    private Paint redPaint;
    private RectF rectLeft;
    private RectF rectRight;
    private RectF rectStop;
    private RectF rectForward;
    private RectF rectDown;

    private JoystickListener listener = new JoystickListener() {
        @Override
        public void onLeft() {

        }

        @Override
        public void onRight() {

        }

        @Override
        public void onForward() {

        }

        @Override
        public void onBackward() {

        }

        @Override
        public void onStopMoving() {

        }
    };


    public JoystickView(Context context) {
        super(context);
        init(context, null);
    }

    public JoystickView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public JoystickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setJoystickListener(JoystickListener listener) {
        this.listener = listener;
    }

    public JoystickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }
        activePointers = new SparseArray<>();
        gestureDetectorCompat = new GestureDetectorCompat(context, this);
        gestureDetectorCompat.setIsLongpressEnabled(true);
        gestureDetectorCompat.setOnDoubleTapListener(this);

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        Paint fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fontPaint.setTextSize(66);
        fontPaint.setStyle(Paint.Style.STROKE);
        fontPaint.setColor(Color.BLACK);
        setBackgroundColor(Color.BLACK);
        paint = new Paint();
        colorPaint = new Paint();
        redPaint = new Paint();
        redPaint.setColor(Color.RED);
        bitmapConventor = new BitmapConverter(getResources());

        arrowDown = bitmapConventor.createBitmap(R.drawable.ic_keyboard_arrow_down);
        arrowUp = bitmapConventor.createBitmap(R.drawable.ic_keyboard_arrow_up);
        arrowLeft = bitmapConventor.createBitmap(R.drawable.ic_keyboard_arrow_left);
        arrowRight = bitmapConventor.createBitmap(R.drawable.ic_keyboard_arrow_right);
        stop = bitmapConventor.createBitmap(R.drawable.ic_stop);

        arrowModels[0] = new ArrowModel(arrowDown, new Paint());
        arrowModels[1] = new ArrowModel(arrowUp, new Paint());
        arrowModels[2] = new ArrowModel(arrowLeft, new Paint());
        arrowModels[3] = new ArrowModel(arrowRight, new Paint());
        arrowModels[4] = new ArrowModel(stop, new Paint());

        rectLeft = new RectF();
        rectRight = new RectF();
        rectStop = new RectF();
        rectForward = new RectF();
        rectDown = new RectF();
//        cardAnimator = new CardAnimator();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        arrowModels[0].setLeft(right - MARGIN);
        arrowModels[0].setTop(bottom - MARGIN + 100);
        arrowModels[1].setLeft(right - MARGIN);
        arrowModels[1].setTop(-100);
        arrowModels[2].setLeft(-100);
        arrowModels[2].setTop(bottom / 2 - 320);
        arrowModels[3].setLeft(300);
        arrowModels[3].setTop(bottom / 2 - 320);
        arrowModels[4].setLeft(right / 2 - 100);
        arrowModels[4].setTop(bottom / 2 - 160);

        rectLeft.left = 0;
        rectLeft.top = 0;
        rectLeft.right = 400;
        rectLeft.bottom = bottom;

        rectRight.left = 400;
        rectRight.top = 0;
        rectRight.right = 800;
        rectRight.bottom = bottom;

        rectStop.left = 800;
        rectStop.top = bottom / 2 - 320;
        rectStop.right = 1200;
        rectStop.bottom = bottom / 2 + 320;

        rectForward.left = 1200;
        rectForward.top = 0;
        rectForward.right = right;
        rectForward.bottom = bottom / 2;

        rectDown.left = 1200;
        rectDown.top = bottom / 2;
        rectDown.right = right;
        rectDown.bottom = bottom;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < arrowModels.length; i++) {
            canvas.drawBitmap(arrowModels[i].getBitmap(), arrowModels[i].getLeft(), arrowModels[i].getTop(), arrowModels[i].getPaint());
        }

        // draw all pointers
/*        for (int size = activePointers.size(), i = 0; i < size; i++) {
            PointF point = activePointers.valueAt(i);
            if (point != null)
                colorPaint.setColor(colors[i % 9]);
            canvas.drawCircle(point.x, point.y, SIZE, colorPaint);
        }*/

/*        canvas.drawRect(rectLeft, redPaint);
        redPaint.setColor(Color.YELLOW);
        canvas.drawRect(rectRight, redPaint);
        redPaint.setColor(Color.GREEN);
        canvas.drawRect(rectStop, redPaint);
        redPaint.setColor(Color.BLUE);
        canvas.drawRect(rectForward, redPaint);
        redPaint.setColor(Color.GRAY);
        canvas.drawRect(rectDown, redPaint);*/
        super.onDraw(canvas);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        /*vibrator.vibrate(50);
        return true;*/
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();

        // get pointer ID
        int pointerId = event.getPointerId(pointerIndex);

        // get masked (not specific to a pointer) action
        int maskedAction = event.getActionMasked();

        switch (maskedAction) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                // We have a new pointer. Lets add it to the list of pointers
                if (activePointers.size() == 2) {
                    break;
                }
                PointF f = new PointF();
                f.x = event.getX(pointerIndex);
                f.y = event.getY(pointerIndex);

                activePointers.put(pointerId, f);
                onDownEvent(event.getX(pointerIndex), event.getY(pointerIndex));
                vibrator.vibrate(50);
                break;
            }
            case MotionEvent.ACTION_MOVE: { // a pointer was moved
                onDownEvent(event.getX(pointerIndex), event.getY(pointerIndex));
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL: {
                activePointers.remove(pointerId);
                break;
            }
        }
        invalidate();

        return true;
    }

    private void onDownEvent(float x, float y) {
        if (rectLeft.contains(x, y)) {
            Log.d(TAG, "onDownEvent: rectLeft");
            arrowModels[2].setSelected(true);
            arrowModels[3].setSelected(false);
            arrowModels[4].setSelected(false);
            listener.onLeft();
        } else if (rectRight.contains(x, y)) {
            Log.d(TAG, "onDownEvent: rectRight");
            arrowModels[3].setSelected(true);
            arrowModels[2].setSelected(false);
            arrowModels[4].setSelected(false);
            listener.onRight();
        } else if (rectStop.contains(x, y)) {
            Log.d(TAG, "onDownEvent: rectStop");
            arrowModels[0].setSelected(false);
            arrowModels[1].setSelected(false);
            arrowModels[2].setSelected(false);
            arrowModels[3].setSelected(false);
            arrowModels[4].setSelected(true);
            listener.onStopMoving();
        } else if (rectForward.contains(x, y)) {
            Log.d(TAG, "onDownEvent: rectForward");
            arrowModels[1].setSelected(true);
            arrowModels[0].setSelected(false);
            arrowModels[4].setSelected(false);
            listener.onForward();
        } else if (rectDown.contains(x, y)) {
            Log.d(TAG, "onDownEvent: rectDown");
            arrowModels[0].setSelected(true);
            arrowModels[1].setSelected(false);
            arrowModels[4].setSelected(false);
            listener.onBackward();
        }
    }

    public interface JoystickListener {
        void onLeft();
        void onRight();
        void onForward();
        void onBackward();
        void onStopMoving();

    }
}
