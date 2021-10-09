package com.aipuer.remotesensingmonitoring;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Paint;
import android.os.Bundle;

import android.widget.TextView;



public class AnalysisActivity extends AppCompatActivity {


    //统计分析
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        initView();
    }



    private void initView() {
        TextView tv_analysis = findViewById(R.id.tv_analysis);
    }
}
