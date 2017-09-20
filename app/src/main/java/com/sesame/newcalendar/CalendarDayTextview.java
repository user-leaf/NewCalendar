package com.sesame.newcalendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by SONY on 2017/8/23.
 */

public class CalendarDayTextview extends android.support.v7.widget.AppCompatTextView {
    public boolean isToday = false; // 暴露一个属性
    private Paint paint = new Paint();

    public CalendarDayTextview(Context context) {
        super(context);
    }

    public CalendarDayTextview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initControl();
    }

    public CalendarDayTextview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl();
    }

    private void initControl() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#ff0000"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isToday) {
            canvas.translate(getWidth() / 2, getHeight() / 2);
            canvas.drawCircle(0, 0, getWidth() / 2, paint);
        }
    }
}
