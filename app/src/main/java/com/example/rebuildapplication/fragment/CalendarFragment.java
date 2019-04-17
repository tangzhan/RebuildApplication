package com.example.rebuildapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rebuildapplication.AddTask;
import com.example.rebuildapplication.R;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class CalendarFragment extends Fragment {
    String dateString;
    boolean updateTextview;
    @BindView(R.id.tv_title)
    TitleBar tvTitle;
    @BindView(R.id.calendarView)
    MaterialCalendarView calendarView;
    Unbinder unbinder;

    public void initCalendar() {
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                //Toast.makeText(getContext(),"日期"+date.getDate(),Toast.LENGTH_SHORT).show();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                dateString = dateFormat.format(date.getDate());
                updateTextview = selected;
                //System.out.println(dateString);
            }
        });
        tvTitle.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
            }
            @Override
            public void onTitleClick(View v) {
            }

            @Override
            public void onRightClick(View v) {
                Intent intent = new Intent(getContext(),AddTask.class);
                intent.putExtra("date",dateString);
                intent.putExtra("select",updateTextview);
                startActivity(intent);
            }
        });


    }

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        unbinder = ButterKnife.bind(this, view);
        initCalendar();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
