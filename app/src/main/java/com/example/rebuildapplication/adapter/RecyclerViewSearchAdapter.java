package com.example.rebuildapplication.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rebuildapplication.R;
import com.example.rebuildapplication.model.Task;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewSearchAdapter extends RecyclerView.Adapter<RecyclerViewSearchAdapter.ViewHolder>{
    private RecyclerViewSearchAdapter.OnItemClickListener onItemClickListener;
    List<Task> taskList = new ArrayList<Task>();
    public RecyclerViewSearchAdapter(List<Task> taskList){
        this.taskList = taskList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String string;
        if (taskList.get(i).getTask_state()==1){
            string = "已完成";
            viewHolder.state.setText(string);
            viewHolder.state.setTextColor(Color.parseColor("#008000"));
        }else {
            string = "未完成";
            viewHolder.state.setText(string);
            viewHolder.state.setTextColor(Color.parseColor("#FF0000"));
        }
        viewHolder.title.setText(taskList.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView state;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.search_title);
            state = (TextView)itemView.findViewById(R.id.search_state);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(v,taskList.get(getLayoutPosition()).getId());
                    }
                }
            });
        }
    }
    public void setFilter(List<Task> FilteredDataList) {
        taskList = FilteredDataList;
        notifyDataSetChanged();
    }
    public interface OnItemClickListener {
        void onItemClick(View view, long position);
    }
    public void setOnItemClickListener(RecyclerViewSearchAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
