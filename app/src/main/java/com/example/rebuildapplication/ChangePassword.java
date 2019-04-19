package com.example.rebuildapplication;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rebuildapplication.model.User;
import com.example.rebuildapplication.utils.SharePreUtil;

import org.litepal.LitePal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePassword extends AppCompatActivity {

    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.one_user_password)
    TextInputEditText oneUserPassword;
    @BindView(R.id.textInputLayout2)
    TextInputLayout textInputLayout2;
    @BindView(R.id.two_user_password)
    TextInputEditText twoUserPassword;
    @BindView(R.id.textInputLayout3)
    TextInputLayout textInputLayout3;
    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        User user = LitePal.find(User.class, SharePreUtil.GetShareInt(this, "keyMark"));
        tvUsername.setText(user.getUsername());
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        if (checkData()){
            changePassword();
            finish();
        }else {
        }
    }
    private boolean checkData(){
        if (TextUtils.isEmpty(oneUserPassword.getText())){
            textInputLayout2.setError("密码不能为空");
            return false;
        }else if (TextUtils.isEmpty(twoUserPassword.getText())
                &&oneUserPassword.getText().toString().trim()
                .equals(twoUserPassword.getText().toString().trim())){
            textInputLayout3.setError("请再次确认密码");
            return false;
        }
        return true;
    }
    private void changePassword(){
        User changePasswordUser = new User();
        changePasswordUser.setPassword(oneUserPassword.getText().toString().trim());
        if (changePasswordUser.update(SharePreUtil.GetShareInt(this, "keyMark")) == 1){
            Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"修改失败",Toast.LENGTH_SHORT).show();
        }

    }
}
