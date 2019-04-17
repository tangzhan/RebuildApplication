package com.example.rebuildapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rebuildapplication.model.Task;
import com.manu.mdatepicker.MDatePickerDialog;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

import org.litepal.LitePal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskEdit extends AppCompatActivity {
    long dateBegin,dateEnd,id;
    @BindView(R.id.et_task_title)
    EditText etTaskTitle;
    @BindView(R.id.simpleRatingBar)
    ScaleRatingBar simpleRatingBar;
    @BindView(R.id.tv_begin_time)
    TextView tvBeginTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.editText_input)
    EditText editTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);
        ButterKnife.bind(this);
        initData();
    }



    public void initData() {
        Intent intent = getIntent();
        id = intent.getLongExtra("id", 0);
        Task task = LitePal.find(Task.class, id);
        etTaskTitle.setText(task.getTitle());
        simpleRatingBar.setRating(task.getImportant_level());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//转换时间格式
        String beginString = formatter.format(task.getSet_time());
        String endString = formatter.format(task.getDeadline());
        tvBeginTime.setText(beginString);
        tvEndTime.setText(endString);
        editTextInput.setText(task.getDetail());
    }



    @OnClick({R.id.tv_begin_time, R.id.tv_end_time, R.id.btn_submit })
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
            editTask();
            Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    public void editTask(){
        Task updateTask = new Task();
        updateTask.setTitle(etTaskTitle.getText().toString());
        simpleRatingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar baseRatingBar, float v) {
                simpleRatingBar.setRating(v);
            }
        });
        updateTask.setImportant_level(simpleRatingBar.getRating());
        updateTask.setSet_time(StringToDate(tvBeginTime.getText().toString()));
        updateTask.setDeadline(StringToDate(tvEndTime.getText().toString()));
        updateTask.setDetail(editTextInput.getText().toString());
        updateTask.update(id);
    }
}
