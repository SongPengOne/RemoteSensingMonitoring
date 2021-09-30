package com.aipuer.remotesensingmonitoring.mycenter;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.aipuer.remotesensingmonitoring.R;
import com.aipuer.remotesensingmonitoring.utils.TopMenuHeader;

public class LoginSettingsActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_settings);
        initView();
    }


    private void initView() {
        // 顶部设置
        TopMenuHeader topMenu = new TopMenuHeader(getWindow().getDecorView());
        topMenu.topMenuTitle.setText("登录设置");
        topMenu.topMenuTitle.setTextColor(Color.parseColor("#555555"));
        //    topMenu.topMenuRight.setVisibility(View.GONE);
        topMenu.topMenuLeft.setVisibility(View.VISIBLE);
        topMenu.topMenuLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
