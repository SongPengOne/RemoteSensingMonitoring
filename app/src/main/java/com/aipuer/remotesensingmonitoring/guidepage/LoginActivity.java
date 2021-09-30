package com.aipuer.remotesensingmonitoring.guidepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aipuer.remotesensingmonitoring.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Package: com.aipuer.remotesensingmonitoring.guidepage
 * @Class: LoginActivity
 * @Author: lx
 * @Time: 2021/9/14 11:28
 * @Remark: 备注描述
 */
public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.iv_pwdShow)
    ImageView ivPwdShow;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_login_version)
    TextView tvLoginVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btn_login)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn_login:
            Intent intent=new Intent(LoginActivity.this,SelectActivity.class);
            startActivity(intent);
            break;
            default:
                break;
        }


    }
}
