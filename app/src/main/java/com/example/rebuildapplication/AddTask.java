package com.example.rebuildapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rebuildapplication.model.Task;
import com.example.rebuildapplication.utils.SharePreUtil;
import com.hjq.bar.TitleBar;
import com.manu.mdatepicker.MDatePickerDialog;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTask extends AppCompatActivity {//缺少数据检测，要比较deadline和开始时间的大小

    long dateBegin,dateEnd;
    @BindView(R.id.title_detail)
    TitleBar titleDetail;
    @BindView(R.id.simpleRatingBar)
    ScaleRatingBar simpleRatingBar;
    @BindView(R.id.tv_begin_time)
    TextView tvBeginTime;
    @BindView(R.id.begin_item)
    LinearLayout beginItem;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.end_ietm)
    LinearLayout endIetm;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.editText_input)
    EditText editTextInput;
    @BindView(R.id.et_task_title)
    EditText etTaskTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//使底部控件不会被软键盘弹起
        setContentView(R.layout.activity_task_add);
        ButterKnife.bind(this);
        setTvTime();
    }

    @OnClick({R.id.tv_begin_time, R.id.tv_end_time, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_begin_time:
                addBeginDatePicker();
                break;
            case R.id.tv_end_time:
                addEndDatePicker();
                break;
            case R.id.btn_submit:
                checkData();
                break;
        }
    }

    protected void setTvTime() {//设置text的显示日期
        Intent intent = getIntent();
        boolean setBeginTime = intent.getBooleanExtra("select",false);
        String dateString = intent.getStringExtra("date");
        if (setBeginTime){
            tvBeginTime.setText(dateString);
        }else {
        Calendar calendar;
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        tvBeginTime.setText(year + "-" + month + "-" + day + "-" + hour + "：" + minute);
        }
    }

    protected void addBeginDatePicker() {//时间日期选择器
        MDatePickerDialog dialog = new MDatePickerDialog.Builder(this)
                //附加设置(非必须,有默认值)
                .setCanceledTouchOutside(true)
                .setGravity(Gravity.BOTTOM)
                .setSupportTime(true)
                .setTwelveHour(true)
                .setCanceledTouchOutside(false)
                //结果回调(必须)
                .setOnDateResultListener(new MDatePickerDialog.OnDateResultListener() {
                    @Override
                    public void onDateResult(long date) {
                        dateBegin = date;
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String dateString = dateFormat.format(date);

                        //要修改为带时间的格式
                        tvBeginTime.setText(dateString);
                    }
                })
                .build();
        dialog.show();
    }
    protected void addEndDatePicker(){
        MDatePickerDialog dialog = new MDatePickerDialog.Builder(this)
                //附加设置(非必须,有默认值)
                .setCanceledTouchOutside(true)
                .setGravity(Gravity.BOTTOM)
                .setSupportTime(true)
                .setTwelveHour(true)
                .setCanceledTouchOutside(false)
                //结果回调(必须)
                .setOnDateResultListener(new MDatePickerDialog.OnDateResultListener() {
                    @Override
                    public void onDateResult(long date) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String dateString = dateFormat.format(date);
                        //要修改为带时间的格式
                        dateEnd = date;
                        tvEndTime.setText(dateString);
                    }
                })
                .build();
        dialog.show();
    }
    //String转Date
    public Date StringToDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            //sdf的格式要与dateString的格式相同，否者会报错
            e.printStackTrace();
        }
        return date;
    }
    private void checkData(){
        if (tvEndTime.getText().toString().equals("--------")){
            Toast.makeText(this,"未设置截止日期",Toast.LENGTH_SHORT).show();
        }
        else if (dateBegin>=dateEnd) {
            Toast.makeText(this,"截止日期不能小于开始日期",Toast.LENGTH_SHORT).show();
        }
        else {
            addTask();
            Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    protected void addTask() {
        Task task = new Task();
        //添加任务详细数据
        task.setTitle(etTaskTitle.getText().toString().trim());
        task.setTask_state(0);
        simpleRatingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar baseRatingBar, float v) {
                simpleRatingBar.setRating(v);
            }
        });//从RatingBar获取星级
        //System.out.println(simpleRatingBar.getRating());
        task.setDays((int)(dateEnd - dateBegin)/(1000 * 60 * 60 * 24));
        task.setImportant_level(simpleRatingBar.getRating());//设置重要程度
        task.setDetail(editTextInput.getText().toString().trim());
        task.setSet_time(StringToDate(tvBeginTime.getText().toString()));
        task.setDeadline(StringToDate(tvEndTime.getText().toString()));
        //少了消耗时间的设置
        task.setMark(SharePreUtil.GetShareInt(this,"keyMark"));//与用户id相关联
        //System.out.println(SharePreUtil.GetShareInt(this,"keyMark"));
        task.save();
    }
}
