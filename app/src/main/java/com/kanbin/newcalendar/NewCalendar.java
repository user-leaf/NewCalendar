package com.kanbin.newcalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by SONY on 2017/8/23.
 */

public class NewCalendar extends LinearLayout {

    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView gridView;

    private Calendar curDate = Calendar.getInstance();
    private String displayFormat;

    public NewCalendarListener listener;

    /**
     * 构造方法：初始化时的入口
     *
     * @param context
     */
    public NewCalendar(Context context) {
        super(context);
    }

    public NewCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initControl(context, attrs);
    }

    public NewCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }

    private void initControl(Context context, AttributeSet attrs) {
        bindControl(context);
        bindControlEvent();

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NewCalendar);
        try {
            String format = typedArray.getString(R.styleable.NewCalendar_dateFormat);
            displayFormat = format;
            if (displayFormat == null){
                displayFormat = "MMM yyyy";
            }
        } finally {
            typedArray.recycle();
        }

        renderCalendar();
    }

    private void bindControl(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.calendar_view, this);
        btnPrev = (ImageView) findViewById(R.id.btnPrev);
        btnNext = (ImageView) findViewById(R.id.btnNext);
        txtDate = (TextView) findViewById(R.id.txtDate);
        gridView = (GridView) findViewById(R.id.calendar_grid);
    }

    private void bindControlEvent() {
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                curDate.add(Calendar.MONTH, -1);
                renderCalendar();   // 渲染 日历绘制
            }
        });

        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                curDate.add(Calendar.MONTH, 1);
                renderCalendar();
            }
        });
    }

    private void renderCalendar() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(displayFormat);
        txtDate.setText(simpleDateFormat.format(curDate.getTime()));

        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) curDate.clone();

        calendar.set(Calendar.DAY_OF_MONTH, 1); // calendar置于1号
        int prevDays = calendar.get(Calendar.DAY_OF_WEEK) - 1;  // 这个月之前有几天
        calendar.add(Calendar.DAY_OF_MONTH, -prevDays); // calendar往前进

        int maxCellCount = 6 * 7;
        while (cells.size() < maxCellCount) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1); // 每添加一天，往后挪1天
        }
        gridView.setAdapter(new CalendarAdapter(getContext(), cells));
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener == null) {
                    return false;
                }else {
                    listener.onItemLongPress((Date) parent.getItemAtPosition(position));
                    return true;
                }
            }

        });

    }

    private class CalendarAdapter extends ArrayAdapter<Date> {
        LayoutInflater inflater;

        public CalendarAdapter(@NonNull Context context, ArrayList<Date> days) {
            super(context, R.layout.calendar_text_day, days);
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Date date = getItem(position);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.calendar_text_day, parent, false);
            }

            int day = date.getDate();
            ((TextView) convertView).setText(String.valueOf(day));

            // 非当月日期置灰
            Date now = new Date();
            boolean isTheSameMonth = false;
            if (date.getMonth() == now.getMonth()) {
                isTheSameMonth = true;
            }
            if (isTheSameMonth) { // 有效的月份，否则不是当月日期
                ((TextView) convertView).setTextColor(Color.parseColor("#000000"));

            } else {
                ((TextView) convertView).setTextColor(Color.parseColor("#666666"));

            }

            // 如果是当天
            if (now.getDate() == date.getDate() && now.getMonth() == date.getMonth()
                    && now.getYear() == date.getYear()) {
                ((TextView) convertView).setTextColor(Color.parseColor("#ff0000"));
                ((CalendarDayTextview) convertView).isToday = true;
            }

            return convertView;
        }
    }

    public interface NewCalendarListener{
        void onItemLongPress(Date day);
    }
}
