package com.example.rebuildapplication.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.rebuildapplication.ChangePassword;
import com.example.rebuildapplication.R;
import com.maiml.library.BaseItemLayout;
import com.maiml.library.config.ConfigAttrs;
import com.maiml.library.config.Mode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SettingFragment extends Fragment {


    @BindView(R.id.setting_item)
    BaseItemLayout settingItem;
    Unbinder unbinder;
    @BindView(R.id.btn_quit)
    Button btnQuit;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        unbinder = ButterKnife.bind(this, view);
        itemLayout();
        return view;
    }

    protected void itemLayout() {
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        //
        List<String> valueList = new ArrayList<>();

        valueList.add("反馈");
        valueList.add("分享");
        valueList.add("清除缓存");
        valueList.add("修改密码");

        List<Integer> resIdList = new ArrayList<>();

        resIdList.add(R.drawable.ic_request);
        resIdList.add(R.drawable.ic_share);
        resIdList.add(R.drawable.ic_delete);
        resIdList.add(R.drawable.ic_about);
        ConfigAttrs attrs = new ConfigAttrs(); // 把全部参数的配置，委托给ConfigAttrs类处理。
        //参数 使用链式方式配置
        attrs.setValueList(valueList)  // 文字 list
                .setResIdList(resIdList) // icon list
                .setIconWidth(24)  //设置icon 的大小
                .setIconHeight(24)
                .setItemMode(Mode.NORMAL);
        settingItem.setConfigAttrs(attrs)
                .create(); //
        settingItem.setOnBaseItemClick(new BaseItemLayout.OnBaseItemClick() {
            @Override
            public void onItemClick(int position) {
                //Log.e(TAG,"----- position = " + position);
                switch (position) {
                    case 0:
                        showDialogOne();
                        break;
                    case 1:
                        showDialogTwo();
                        break;
                    case 2:
                        Toast.makeText(getActivity(), "缓存已清除", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        startActivity(new Intent(getContext(), ChangePassword.class));
                        break;
                }
            }
        });
    }

    protected void showDialogOne() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_safe)
                .setTitle("通知").setMessage("请等待项目上线后再反馈")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    protected void showDialogTwo() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_safe)
                .setTitle("通知").setMessage("暂时无法分享")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
