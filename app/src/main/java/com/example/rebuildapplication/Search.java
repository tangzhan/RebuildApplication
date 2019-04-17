package com.example.rebuildapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.example.rebuildapplication.adapter.RecyclerViewSearchAdapter;
import com.example.rebuildapplication.model.Task;
import com.example.rebuildapplication.utils.SharePreUtil;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Search extends AppCompatActivity {

    @BindView(R.id.recycler_search)
    RecyclerView recyclerSearch;
    private List<Task> taskList, filteredDataList;
    RecyclerViewSearchAdapter adapter;
    @BindView(R.id.searchview)
    SearchView searchview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initData();
        initRecyclerView();
        searchTask();

    }

    public void initRecyclerView() {
        recyclerSearch.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter = new RecyclerViewSearchAdapter(taskList);
        adapter.setOnItemClickListener(new RecyclerViewSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, long position) {
                Intent intent = new Intent();
                intent.setClass(Search.this,TaskDetail.class);
                intent.putExtra("id", position);
                startActivity(intent);
            }
        });
        recyclerSearch.setAdapter(adapter);
        recyclerSearch.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    public void searchTask() {
        searchview.setIconified(false);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filteredDataList = filter(taskList, s);
                adapter.setFilter(filteredDataList);
                return true;
            }
        });
    }

    /*
     * 又通过数据库查询了一遍，很过分，应该从上一个fragment中获取到taskList就好了
     * */
    public void initData() {
        int number = SharePreUtil.GetShareInt(this, "keyMark");
        String s = String.valueOf(number);
        taskList = LitePal.where("mark = ?", s).find(Task.class);
    }

    /*
     * 模糊查询
     * */
    private List<Task> filter(List<Task> dataList, String newText) {
        newText = newText.toLowerCase();
        String text;
        filteredDataList = new ArrayList<>();
        for (Task dataFromDataList : dataList) {
            text = dataFromDataList.getTitle().toLowerCase();

            if (text.contains(newText)) {
                filteredDataList.add(dataFromDataList);
            }
        }

        return filteredDataList;
    }
}
