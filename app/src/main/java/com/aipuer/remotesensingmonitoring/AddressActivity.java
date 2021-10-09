package com.aipuer.remotesensingmonitoring;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.aipuer.remotesensingmonitoring.adapter.AddressAdapter;
import com.aipuer.remotesensingmonitoring.adapter.AddressAreaAdapter;
import com.aipuer.remotesensingmonitoring.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class AddressActivity extends BaseActivity {

    private static final String TAG = "AddressActivity";
    @BindView(R.id.rlv)
    RecyclerView recyclerView;

    @BindView(R.id.rlv_area)
    RecyclerView rlv_area;


    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.iv_addressback)
    ImageView iv_addressback;
    @BindView(R.id.tv_area)
    TextView tv_area;
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    @BindView(R.id.tv_shi)
    TextView tv_shi;
    @BindView(R.id.tv_xian)
    TextView tv_xian;
    private String area;


    @Override
    public int bindLayout() {
        return R.layout.activity_address;
    }

    @Override
    public void doBusiness(Context context) {
    }


    @Override
    protected void initData() {


        ArrayList<String> objects = new ArrayList<>();
        objects.add("郑州市");
        objects.add("周口市");
        objects.add("洛阳市");
        objects.add("开封市");
        objects.add("平顶山市");
        objects.add("许昌市");


        ArrayList<String> strings = new ArrayList<>();
        strings.add("金水区");
        strings.add("管城区");
        strings.add("中原区");
        strings.add("会新区");
        strings.add("新政区");

        Log.d(TAG, "initData: " + objects);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        AddressAdapter addressAdapter = new AddressAdapter(objects, this);
        addressAdapter.onItenOnclick(new AddressAdapter.OnListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void Click(String title, ImageView imageView) {
                Log.d(TAG, "ClickTitle: "+title);
                tv_address.setText(title);
                tv_address.setBackground(getResources().getDrawable(R.drawable.area_bg));
                tv_address.setTextColor(R.color.event_dark_blue);

                tv_shi.setVisibility(View.GONE);
                tv_shi.setTextColor(Color.BLUE);
                tv_xian.setVisibility(View.VISIBLE);
                tv_area.setTextColor(Color.WHITE);
                tv_area.setBackground(getResources().getDrawable(R.color.event_dark_blue));
                rlv_area.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);

            }
        });
        recyclerView.setAdapter(addressAdapter);
        iv_addressback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rlv_area.setLayoutManager(layoutManager);
        AddressAreaAdapter addressAreaAdapter = new AddressAreaAdapter(strings, this);
        rlv_area.setAdapter(addressAreaAdapter);
        addressAreaAdapter.onLongItem(new AddressAreaAdapter.OnListener() {
            @Override
            public void Click(String til, ImageView imageView) {
                area = til;
                tv_area.setText(til);
                tv_sure.setVisibility(View.VISIBLE);
                tv_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = getIntent();
                        intent.putExtra("result", area);
                        setResult(1001, intent);
                        finish();
                    }
                });

            }

        });


    }

}
