package com.example.rebuildapplication.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.rebuildapplication.R;
import com.example.rebuildapplication.model.Task;
import com.willy.ratingbar.ScaleRatingBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private RecyclerViewAdapter.OnItemClickListener onItemClickListener;
    List<Task> taskList = new ArrayList<Task>();
    public RecyclerViewAdapter(List<Task> taskList){
        this.taskList = taskList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ScaleRatingBar important;
        TextView deadline;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_title);
            important = (ScaleRatingBar)itemView.findViewById(R.id.importantRatingBar);
            deadline = (TextView)itemView.findViewById(R.id.deadline);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(v,taskList.get(getLayoutPosition()-1).getId());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.onItemLongClick(v,getLayoutPosition(),taskList.get(getLayoutPosition()-1).getId());
                    }
                    return false;
                }
            });
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//转换时间格式
        String dateString = formatter.format(taskList.get(i).getDeadline());
        viewHolder.title.setText(taskList.get(i).getTitle());
        viewHolder.important.setRating(taskList.get(i).getImportant_level());
        viewHolder.deadline.setText(dateString);
    }



    @Override
    public int getItemCount() {
        return taskList.size();
    }
  /*  点击和长按或者是左滑事件中应该有，删除，完成，置顶，查看详情（点击）。
    长按后下方上划一个菜单有置顶，删除，完成三项*/
// ① 定义点击回调接口
    public interface OnItemClickListener {
      void onItemClick(View view, long position);
      void onItemLongClick(View view, int position,long id);
  }

    // ② 定义一个设置点击监听器的方法
    public void setOnItemClickListener(RecyclerViewAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
