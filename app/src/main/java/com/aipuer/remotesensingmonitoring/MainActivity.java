package com.aipuer.remotesensingmonitoring;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aipuer.remotesensingmonitoring.adapter.PopuwinBottonAdapter;
import com.aipuer.remotesensingmonitoring.adapter.PopuwinListAdapter;
import com.aipuer.remotesensingmonitoring.base.BaseActivity;
import com.aipuer.remotesensingmonitoring.utils.PopuwinUitls;
import com.aipuer.remotesensingmonitoring.utils.StatusBarUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;


import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;


import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends Activity {


    private static final String TAG = "主页面";
    @BindView(R.id.rly)
    RelativeLayout relativeLayout;

    @BindView(R.id.rl_search)
    RelativeLayout rl_search;

    @BindView(R.id.ed_search)
    EditText ed_search;
    @BindView(R.id.na_view)
    NavigationView na_view;

    @BindView(R.id.draw_layout)
    DrawerLayout draw_layout;

    @BindView(R.id.iv_layer)
    ImageView iv_layer;

    @BindView(R.id.im_toolbox)
    ImageView im_toolbox;

    @BindView(R.id.ll_mark)
    LinearLayout ll_mark;

    @BindView(R.id.tv_measure)
    TextView tv_measure;
    @BindView(R.id.ll_specific)
    LinearLayout ll_specific;

    @BindView(R.id.iv_list)
    ImageView iv_list;

    @BindView(R.id.rl_toob)
    RelativeLayout rl_toob;
    @BindView(R.id.rl_mian)
    RelativeLayout rl_mian;
    @BindView(R.id.iv_shorthand)
    ImageView iv_shorthand;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private PopuwinListAdapter popuwinListAdapter;
    private MapView mMapView = null;
    private AMap aMap;
    public AMapLocationClientOption mLocationOption = null;

    AMapLocationClient mLocationClient = null;
    private MyLocationStyle myLocationStyle;
    /**
     * 是否第一次定位
     */
    private boolean isFirstLocation = true;

    /**
     * 当前定位城市
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        StatusBarUtils.setColor(this, getResources().getColor(R.color.event_dark_blue));
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
       initAmap();
    }


    private void initAmap() {
        if (aMap == null) {
            aMap = mMapView.getMap();
        }


        // 如果要设置定位的默认状态，可以在此处进行设置
        myLocationStyle = new MyLocationStyle();
        // 设置圆形的边框颜色
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        //aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE));

        // 设置默认定位按钮是否显示
        aMap.getUiSettings().setMyLocationButtonEnabled(false);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);

        /*定位 当前位置*/
        getCurrentLocation();
    }


    private void getCurrentLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(this);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        // 同时使用网络定位和GPS定位,优先返回最高精度的定位结果,以及对应的地址描述信息
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(1000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }
    /**
     * 定位回调监听器
     */
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    if (isFirstLocation) {
                        isFirstLocation = false;
                        //定位成功回调信息，设置相关消息
                        double currentLat = amapLocation.getLatitude();
                        double currentLon = amapLocation.getLongitude();
//                        currLatLng = new LatLng(currentLat, currentLon);
                   //     currCity = amapLocation.getCity();
                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLat, currentLon), 16));
                    }
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };



   /* @Override
    public int bindLayout() {

        return R.layout.activity_main;

    }

    @Override
    protected void initData() {

    }

    @Override
    public void doBusiness(Context context) {

    }*/

    @SuppressLint("WrongConstant")
    @OnClick({R.id.rly, R.id.rl_search, R.id.iv_layer, R.id.im_toolbox, R.id.tv_measure, R.id.iv_list, R.id.iv_shorthand})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rly:
                Intent intent = new Intent(MainActivity.this, AddressActivity.class);
                intent.putExtra("name", "金水区");
                startActivityForResult(intent, 1000);
                break;
            case R.id.rl_search:
                ForSearch();
                break;
            case R.id.iv_layer:
                draw_layout.openDrawer(Gravity.END);
                NavigationViewOnClick();
                break;
            case R.id.im_toolbox:
                if (ll_mark.getVisibility() == View.GONE) {
                    ll_mark.setVisibility(View.VISIBLE);
                } else {
                    ll_mark.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_measure:
                if (ll_specific.getVisibility() == View.GONE) {
                    ll_specific.setVisibility(View.VISIBLE);
                } else {
                    ll_specific.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_list:
                //  ListPopuwin();
                PopuwinUitls popuwinUitls = new PopuwinUitls(this, rl_toob, iv_list, popuwinListAdapter);
                popuwinUitls.showPop(iv_list);
                break;
            case R.id.iv_shorthand:
                Popushorthand popushorthand = new Popushorthand(this, rl_toob);
                popushorthand.showPop(iv_shorthand);
                break;
        }
    }

    private void ListPopuwin() {

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("撒89" + i);
        }


        View inflate = LayoutInflater.from(this).inflate(R.layout.lay_listpop, null);
        LinearLayout ll_listpop = inflate.findViewById(R.id.ll_listpop);
        RecyclerView rly_listpop = inflate.findViewById(R.id.rly_listpop);
        ImageView iv_listcross = inflate.findViewById(R.id.iv_listcross);


        LinearLayoutManager layout = new LinearLayoutManager(this);
        rly_listpop.setLayoutManager(layout);
        PopuwinListAdapter popuwinListAdapter = new PopuwinListAdapter(strings, this);
        rly_listpop.setAdapter(popuwinListAdapter);


        PopupWindow popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        iv_listcross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        popupWindow.showAtLocation(ll_listpop, Gravity.BOTTOM, 0, 0);

    }

    private void NavigationViewOnClick() {
        View headerView = na_view.getHeaderView(0);
        ImageView iv_menu_return = headerView.findViewById(R.id.iv_menu_return);
        iv_menu_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                draw_layout.closeDrawer(na_view);
                return;
            }
        });

        na_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.item1:
                        break;
                    case R.id.item2:
                        break;
                    case R.id.item3:
                        break;
                    case R.id.item4:
                        break;
                    case R.id.item5:
                        break;
                }


                return true;
            }
        });
    }

    private void ForSearch() {
        ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String title = ed_search.getText().toString();
                    Log.d(TAG, "onEditorAction: " + title);
                    if (title != null) {
                        //弹出popuwin
                        Popuwin();
                    }
                    return true;
                }

                return false;
            }
        });

    }

    private void Popuwin() {

        ArrayList<String> objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            objects.add("撒大大" + i);
        }


        View inflate = LayoutInflater.from(this).inflate(R.layout.lay_menupopwin, null);
        LinearLayout linearLayout = inflate.findViewById(R.id.ll_popuwin);
        ImageView iv_cross = inflate.findViewById(R.id.iv_cross);
        RecyclerView recyclerView = inflate.findViewById(R.id.rly_pop);


        LinearLayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);
        PopuwinBottonAdapter popuwinBottonAdapter = new PopuwinBottonAdapter(objects, this);
        recyclerView.setAdapter(popuwinBottonAdapter);


        PopupWindow popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        iv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        popupWindow.showAtLocation(linearLayout, Gravity.BOTTOM, 0, 0);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 1001) {
            String res = data.getStringExtra("result");
            tv_title.setText(res);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }


}
