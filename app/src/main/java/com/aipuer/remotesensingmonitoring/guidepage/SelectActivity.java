package com.aipuer.remotesensingmonitoring.guidepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.aipuer.remotesensingmonitoring.AnalysisActivity;
import com.aipuer.remotesensingmonitoring.MainActivity;
import com.aipuer.remotesensingmonitoring.R;
import com.aipuer.remotesensingmonitoring.mycenter.PersonalCenter;
import com.aipuer.remotesensingmonitoring.statistics.StatisticsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Package: com.aipuer.remotesensingmonitoring.statistics
 * @Class: StatisticsActivity
 * @Author: lx
 * @Time: 2021/9/14 14:55
 * @Remark: 选择模块
 */
public class SelectActivity extends AppCompatActivity {
//PersonalCenter 个人模块

    @BindView(R.id.iv_inspect)
    ImageView ivInspect;
    @BindView(R.id.iv_statistics)
    ImageView ivStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_inspect, R.id.iv_statistics})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_inspect:
                Intent intent = new Intent(SelectActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_statistics:
                Intent intent1 = new Intent(SelectActivity.this, AnalysisActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
