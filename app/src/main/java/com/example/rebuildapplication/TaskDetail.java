package com.example.rebuildapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rebuildapplication.model.Task;
import com.willy.ratingbar.ScaleRatingBar;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskDetail extends AppCompatActivity {
    long id;
    @BindView(R.id.set_title)
    TextView setTitle;
    @BindView(R.id.set_begin_time)
    TextView setBeginTime;
    @BindView(R.id.set_deadline)
    TextView setDeadline;
    @BindView(R.id.set_task_state)
    TextView setTaskState;
    @BindView(R.id.set_task_detail)
    TextView setTaskDetail;
    @BindView(R.id.btn_edit_task)
    ImageButton btnEditTask;
    @BindView(R.id.btn_delete_task)
    ImageButton btnDeleteTask;
    @BindView(R.id.set_important_level)
    ScaleRatingBar setImportantLevel;
    @BindView(R.id.set_task_days)
    TextView setTaskDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        ButterKnife.bind(this);
        initData();
    }

    protected void initData() {
        Intent intent = getIntent();
        String task_state;
        id = intent.getLongExtra("id", 0);
        Task task = LitePal.find(Task.class, id);
        if (task.getTask_state() == 1) {
            task_state = "已完成";
        } else {
            task_state = "未完成";
        }
        setTitle.setText(task.getTitle());
        setImportantLevel.setRating(task.getImportant_level());
        setTaskState.setText(task_state);//需要转换
        setTaskDetail.setText(task.getDetail());
        setTaskDays.setText(task.getDays()+"天");
        //时间格式也转换一下吧
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//转换时间格式
        String beginString = formatter.format(task.getSet_time());
        String endString = formatter.format(task.getDeadline());
        setBeginTime.setText(beginString);
        setDeadline.setText(endString);
    }

    @OnClick({R.id.btn_edit_task, R.id.btn_delete_task})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_edit_task:
                Intent intent = new Intent(this, TaskEdit.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
            case R.id.btn_delete_task:
                deleteDialog();
                break;
        }
    }

    public void deleteDialog() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_delete)
                .setTitle("通知").setMessage("你确定要删除吗")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteTask();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    public void deleteTask() {
        Intent intent = getIntent();
        id = intent.getLongExtra("id", 0);
        LitePal.delete(Task.class, id);
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        finish();
    }
}
