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
    public static final int DOWN_ARROW_ID = 0;
    public static final int UP_ARROW_ID = 1;
    public static final int LEFT_ARROW_ID = 2;
    public static final int RIGHT_ARROW_ID = 3;
    public static final int STOP_ARROW_ID = 4;
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
        paint = new Paint();
        colorPaint = new Paint();
        redPaint = new Paint();
        redPaint.setStyle(Paint.Style.STROKE);
        redPaint.setColor(Color.RED);
        bitmapConventor = new BitmapConverter(getResources());

        arrowDown = bitmapConventor.createBitmap(R.drawable.ic_keyboard_arrow_down);
        arrowUp = bitmapConventor.createBitmap(R.drawable.ic_keyboard_arrow_up);
        arrowLeft = bitmapConventor.createBitmap(R.drawable.ic_keyboard_arrow_left);
        arrowRight = bitmapConventor.createBitmap(R.drawable.ic_keyboard_arrow_right);
        stop = bitmapConventor.createBitmap(R.drawable.ic_stop);

        arrowModels[DOWN_ARROW_ID] = new ArrowModel(arrowDown, new Paint());
        arrowModels[UP_ARROW_ID] = new ArrowModel(arrowUp, new Paint());
        arrowModels[LEFT_ARROW_ID] = new ArrowModel(arrowLeft, new Paint());
        arrowModels[RIGHT_ARROW_ID] = new ArrowModel(arrowRight, new Paint());
        arrowModels[STOP_ARROW_ID] = new ArrowModel(stop, new Paint());

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
        arrowModels[DOWN_ARROW_ID].setLeft(right - MARGIN);
        arrowModels[DOWN_ARROW_ID].setTop(bottom - MARGIN + 100);
        arrowModels[UP_ARROW_ID].setLeft(right - MARGIN);
        arrowModels[UP_ARROW_ID].setTop(-100);
        arrowModels[LEFT_ARROW_ID].setLeft(-100);
        arrowModels[LEFT_ARROW_ID].setTop(bottom / 2 - 320);
        arrowModels[RIGHT_ARROW_ID].setLeft(300);
        arrowModels[RIGHT_ARROW_ID].setTop(bottom / 2 - 320);
        arrowModels[STOP_ARROW_ID].setLeft(right / 2 - 100);
        arrowModels[STOP_ARROW_ID].setTop(bottom / 2 - 160);

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
        super.onDraw(canvas);
/*        canvas.drawRect(rectLeft, redPaint);
        canvas.drawRect(rectRight, redPaint);
        canvas.drawRect(rectStop, redPaint);
        canvas.drawRect(rectForward, redPaint);
        canvas.drawRect(rectDown, redPaint);*/

        for (int i = 0; i < arrowModels.length; i++) {
            canvas.drawBitmap(arrowModels[i].getBitmap(), arrowModels[i].getLeft(), arrowModels[i].getTop(), arrowModels[i].getPaint());
        }
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
                onMotionEvent(event.getX(pointerIndex), event.getY(pointerIndex), event);
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL: {
/*                if(!rectLeft.contains(event.getX(), getY()) && !rectRight.contains(event.getX(), getY())){
                    arrowModels[DOWN_ARROW_ID].setSelected(false);
                    arrowModels[UP_ARROW_ID].setSelected(false);
                    arrowModels[LEFT_ARROW_ID].setSelected(false);
                    arrowModels[RIGHT_ARROW_ID].setSelected(false);
                    arrowModels[STOP_ARROW_ID].setSelected(true);
                    listener.onStopMoving();
                }*/
                activePointers.remove(pointerId);
                break;
            }
        }
        invalidate();

        return true;
    }

    private void onMotionEvent(float x, float y, MotionEvent event) {
        if (rectLeft.contains(x, y)) {
            Log.d(TAG, "onMotionEvent: rectLeft");
            if (!arrowModels[LEFT_ARROW_ID].isSelected()) {
                arrowModels[LEFT_ARROW_ID].setSelected(true);
                arrowModels[RIGHT_ARROW_ID].setSelected(false);
                arrowModels[STOP_ARROW_ID].setSelected(false);
                listener.onLeft();
            }else {
                for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                    PointF point = activePointers.get(event.getPointerId(i));
                    if (point != null) {
                        if (point.x - x > 100) {
                            point.x = x;
                            point.y = y;
                            if(arrowModels[LEFT_ARROW_ID].increase()) {
                                listener.onLeft();
                            }
                        }
                    }
                }
            }
        } else if (rectRight.contains(x, y)) {
            Log.d(TAG, "onMotionEvent: rectRight");
            if (!arrowModels[RIGHT_ARROW_ID].isSelected()) {
                arrowModels[RIGHT_ARROW_ID].setSelected(true);
                arrowModels[LEFT_ARROW_ID].setSelected(false);
                arrowModels[STOP_ARROW_ID].setSelected(false);
                listener.onRight();
            }
        } else if (rectStop.contains(x, y)) {
            Log.d(TAG, "onMotionEvent: rectStop");
            if (!arrowModels[STOP_ARROW_ID].isSelected()) {
                arrowModels[DOWN_ARROW_ID].setSelected(false);
                arrowModels[UP_ARROW_ID].setSelected(false);
                arrowModels[LEFT_ARROW_ID].setSelected(false);
                arrowModels[RIGHT_ARROW_ID].setSelected(false);
                arrowModels[STOP_ARROW_ID].setSelected(true);
                listener.onStopMoving();
            }
        } else if (rectForward.contains(x, y)) {
            Log.d(TAG, "onMotionEvent: rectForward");
            if (!arrowModels[UP_ARROW_ID].isSelected()) {
                arrowModels[UP_ARROW_ID].setSelected(true);
                arrowModels[DOWN_ARROW_ID].setSelected(false);
                arrowModels[STOP_ARROW_ID].setSelected(false);
                listener.onForward();
            }else {
                for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                    PointF point = activePointers.get(event.getPointerId(i));
                    if (point != null) {
                        if (point.y - y > 100) {
                            point.x = x;
                            point.y = y;
                            if(arrowModels[UP_ARROW_ID].increase()) {
                                listener.onForward();
                            }
                        }
                    }
                }
            }

        } else if (rectDown.contains(x, y)) {
            Log.d(TAG, "onMotionEvent: rectDown");
            if (!arrowModels[DOWN_ARROW_ID].isSelected()) {
                arrowModels[DOWN_ARROW_ID].setSelected(true);
                arrowModels[UP_ARROW_ID].setSelected(false);
                arrowModels[STOP_ARROW_ID].setSelected(false);
                listener.onBackward();
            }
            else {
                for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                    PointF point = activePointers.get(event.getPointerId(i));
                    if (point != null) {
                        if (y - point.y > 100) {
                            point.x = x;
                            point.y = y;
                            if(arrowModels[DOWN_ARROW_ID].increase()) {
                                listener.onBackward();
                            }
                        }
                    }
                }
            }
        }
    }

    private void onDownEvent(float x, float y) {
        if (rectLeft.contains(x, y)) {
            Log.d(TAG, "onDownEvent: rectLeft");
            arrowModels[LEFT_ARROW_ID].setSelected(true);
            arrowModels[RIGHT_ARROW_ID].setSelected(false);
            arrowModels[STOP_ARROW_ID].setSelected(false);
            listener.onLeft();
        } else if (rectRight.contains(x, y)) {
            Log.d(TAG, "onDownEvent: rectRight");
            arrowModels[RIGHT_ARROW_ID].setSelected(true);
            arrowModels[LEFT_ARROW_ID].setSelected(false);
            arrowModels[STOP_ARROW_ID].setSelected(false);
            listener.onRight();
        } else if (rectStop.contains(x, y)) {
            Log.d(TAG, "onDownEvent: rectStop");
            arrowModels[DOWN_ARROW_ID].setSelected(false);
            arrowModels[UP_ARROW_ID].setSelected(false);
            arrowModels[LEFT_ARROW_ID].setSelected(false);
            arrowModels[RIGHT_ARROW_ID].setSelected(false);
            arrowModels[STOP_ARROW_ID].setSelected(true);
            listener.onStopMoving();
        } else if (rectForward.contains(x, y)) {
            Log.d(TAG, "onDownEvent: rectForward");
            arrowModels[UP_ARROW_ID].setSelected(true);
            arrowModels[DOWN_ARROW_ID].setSelected(false);
            arrowModels[STOP_ARROW_ID].setSelected(false);
            listener.onForward();
        } else if (rectDown.contains(x, y)) {
            Log.d(TAG, "onDownEvent: rectDown");
            arrowModels[DOWN_ARROW_ID].setSelected(true);
            arrowModels[UP_ARROW_ID].setSelected(false);
            arrowModels[STOP_ARROW_ID].setSelected(false);
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
