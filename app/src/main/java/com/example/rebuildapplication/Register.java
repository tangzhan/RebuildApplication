package com.example.rebuildapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rebuildapplication.model.User;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Register extends AppCompatActivity {

    @BindView(R.id.textInputLayout)
    TextInputLayout textInputLayout;
    @BindView(R.id.textInputLayout2)
    TextInputLayout textInputLayout2;
    @BindView(R.id.textInputLayout3)
    TextInputLayout textInputLayout3;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.et_user_name)
    TextInputEditText etUserName;
    @BindView(R.id.one_user_password)
    TextInputEditText oneUserPassword;
    @BindView(R.id.two_user_password)
    TextInputEditText twoUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button2)
    public void onViewClicked() {
        register();
    }
    private boolean checkData(){
       if (TextUtils.isEmpty(etUserName.getText())){
           textInputLayout.setError("用户名不能为空");
           return false;
       }
       else if (TextUtils.isEmpty(oneUserPassword.getText())){
           textInputLayout2.setError("密码不能为空");
           return false;
       }
       else if (TextUtils.isEmpty(twoUserPassword.getText())
               &&oneUserPassword.getText().toString().trim()
               .equals(twoUserPassword.getText().toString().trim())){
           textInputLayout3.setError("请再次确认密码");
           return false;
       }
       return true;
    }
    private void register(){
        if (checkData()){
            Boolean exist = LitePal.isExist(User.class,"username = ?",etUserName.getText().toString());
            if (exist){
                Toast.makeText(getApplicationContext(),"用户名已存在，请更换",Toast.LENGTH_SHORT).show();
            }
            else {
                User user = new User();
                user.setUsername(etUserName.getText().toString());
                user.setPassword(oneUserPassword.getText().toString());
                user.save();
                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        }
    }
}
