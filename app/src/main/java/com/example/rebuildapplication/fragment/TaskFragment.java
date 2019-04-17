package com.example.rebuildapplication.fragment;


import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.adorkable.iosdialog.ActionSheetDialog;
import com.example.rebuildapplication.AddTask;
import com.example.rebuildapplication.R;
import com.example.rebuildapplication.Search;
import com.example.rebuildapplication.TaskDetail;
import com.example.rebuildapplication.adapter.RecyclerViewAdapter;
import com.example.rebuildapplication.model.Task;
import com.example.rebuildapplication.utils.ListUtils;
import com.example.rebuildapplication.utils.SharePreUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class TaskFragment extends Fragment {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private List<Task> taskList = new ArrayList<Task>();
    private View view;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_task, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initRecycler();
        initSearch();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fab_add)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), AddTask.class));
    }

    public void initData() {
        int number = SharePreUtil.GetShareInt(getActivity(), "keyMark");
        String s = String.valueOf(number);
        taskList = LitePal.where("mark = ?", s).find(Task.class);
        String [] sortNameArr = {"important_level","deadline"};
        boolean [] isAscArr = {false,true};//true升序,false降序
        ListUtils.sort(taskList,sortNameArr,isAscArr);
    }
    public void initSearch(){
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
            }

            @Override
            public void onTitleClick(View v) {
            }

            @Override
            public void onRightClick(View v) {
                startActivity(new Intent(getActivity(), Search.class));
            }
        });
    }

    public void initRecycler() {
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        recyclerView = (RecyclerView) view.findViewById(R.id.task_recyclerview);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerViewAdapter = new RecyclerViewAdapter(taskList);
        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, long position) {
                Intent intent = new Intent(getContext(), TaskDetail.class);
                intent.putExtra("id", position);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, long id) {
                iosDialog(position, id);
            }
        });
        recyclerView.setItemAnimator(defaultItemAnimator);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    public void iosDialog(final int position, final long id) {
        new ActionSheetDialog(getActivity())
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("删除", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                deleteTask(id);
                            }
                        })
                .addSheetItem("完成", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                changeTaskState(id);
                            }
                        }).show();
    }

    public void deleteTask(long id) {
        LitePal.delete(Task.class, id);
        Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
    }

    public void changeTaskState(long id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("task_state", 1);
        LitePal.update(Task.class, contentValues, id);
        Toast.makeText(getActivity(), "已完成", Toast.LENGTH_SHORT).show();
    }
}
