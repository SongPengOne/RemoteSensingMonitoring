package com.aipuer.remotesensingmonitoring.mycenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aipuer.remotesensingmonitoring.R;
import com.aipuer.remotesensingmonitoring.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Package: com.aipuer.remotesensingmonitoring.mycenter
 * @Class: PersonalCenter
 * @Author: lx
 * @Time: 2021/9/15 15:28
 * @Remark: 个人中心
 */
public class PersonalCenter extends BaseActivity {
    @BindView(R.id.tv_mine_logon)
    TextView tv_minelogon;
    @BindView(R.id.tv_mine_my_sign)
    TextView tv_minemysign;

    @Override
    public int bindLayout() {
        return R.layout.activity_personalcenter;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void doBusiness(Context context) {

    }

    @OnClick({R.id.tv_mine_logon, R.id.tv_mine_my_sign})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_mine_logon:  //登陆设置
                Intent intent = new Intent(this, LoginSettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_mine_my_sign:  //我的标记
                break;
        }
    }


}
