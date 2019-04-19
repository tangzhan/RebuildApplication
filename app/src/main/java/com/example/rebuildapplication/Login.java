package com.example.rebuildapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rebuildapplication.model.User;
import com.example.rebuildapplication.utils.SharePreUtil;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity {

    int keyMark;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_name_design)
    TextInputLayout etNameDesign;
    @BindView(R.id.et_psw)
    EditText etPsw;
    @BindView(R.id.et_psw_design)
    TextInputLayout etPswDesign;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_use)
    TextView tvUse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_login, R.id.tv_register, R.id.tv_use})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_register:
                startActivity(new Intent(Login.this, Register.class));
                break;
            case R.id.tv_use://直接使用这个功能怎么弄
                break;
        }
    }

    private boolean checkData(){
        if (TextUtils.isEmpty(etUserName.getText())){
            etNameDesign.setError("用户名不能为空");
            return false;
        }
        else if (TextUtils.isEmpty(etPsw.getText())){
            etPswDesign.setError("密码不能为空");
            return false;
        }
        else return true;
    }
    private void login(){
        if (checkData()){
            List<User> persons = LitePal.findAll(User.class);
            Boolean login = false;
            for (User ps:persons){
                if (etUserName.getText().toString().trim().equals(ps.getUsername())
                &&etPsw.getText().toString().trim().equals(ps.getPassword())){
                    keyMark = (int)ps.getId();
                    login = true;
                }
            }
            if (login){
                SharePreUtil.SetShareInt(this,"keyMark",keyMark);
                startActivity(new Intent(Login.this, MainActivity.class));
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(),"登陆失败，请重新输入用户名或密码",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
