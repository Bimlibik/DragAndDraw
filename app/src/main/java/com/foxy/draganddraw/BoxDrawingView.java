package com.foxy.draganddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BoxDrawingView extends View {
    private static final String TAG = "BoxDrawingView";

    private Box currentBox;
    private List<Box> boxen = new ArrayList<>();
    private Paint boxPaint;
    private Paint backgroundPaint;

    private float rotate;
    private int currentIndex;

    // используется при создании view в коде
    public BoxDrawingView(Context context) {
        super(context);
    }

    // используется при заполнении view из XML
    public BoxDrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // цвет прямоугольников
        boxPaint = new Paint();
        boxPaint.setColor(0x22ff0000);

        // цвет фона
        backgroundPaint = new Paint();
        backgroundPaint.setColor(0xfff8efe0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(), event.getY());
        String action = "";

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:                  // первое касание
                action = "ACTION_DOWN";

                // сброс текущего состояния
                currentBox = new Box(current);
                boxen.add(currentBox);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:         // последующие косания
                currentIndex = event.getActionIndex();
                break;
            case MotionEvent.ACTION_MOVE:                // любое движение
                action = "ACTION_MOVE";
                if (currentBox != null) {
                    if (currentIndex != 0 && currentIndex == event.getActionIndex()) {
                        rotate = event.getHistoricalY(currentIndex, 1);
                        current.set(event.getX() + rotate, event.getY() + rotate);
                    }
                    currentBox.setCurrent(current);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:         // отпускание всех пальцев кроме последнего
                rotate = 0;
                currentIndex = 0;
                break;
            case MotionEvent.ACTION_UP:                 // отпускание последнего пальца
                action = "ACTION_UP";
                currentBox = null;
                break;
            case MotionEvent.ACTION_CANCEL:            // сбой
                action = "ACTION_CANCEL";
                currentBox = null;
                break;
        }

        Log.i(TAG, action + "at x=" + current.x + ", y=" + current.y);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // заполнение фона
        canvas.drawPaint(backgroundPaint);

        for (Box box : boxen) {
            float left = Math.min(box.getOrigin().x, box.getCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);

            if (rotate == 0) {
                canvas.rotate(rotate, left, bottom);
            }
            canvas.drawRect(left, top, right, bottom, boxPaint);
        }
    }
}
