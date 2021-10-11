package com.aipuer.remotesensingmonitoring;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aipuer.remotesensingmonitoring.adapter.PopuwinBottonAdapter;
import com.aipuer.remotesensingmonitoring.adapter.PopuwinListAdapter;
import com.aipuer.remotesensingmonitoring.utils.MapUtils;
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
import java.util.List;

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


    /**
     * 是否第一次定位
     */
    private boolean isFirstLocation = true;

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    private static final int PERMISSON_REQUESTCODE = 0;

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;


    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    //public AMapLocationListener mLocationListener = new AMapLocationListener();
    // public AMapLocationListener mLocationListener;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
    private AMap aMap;
    private MyLocationStyle myLocationStyle;


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
        initLocation();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //开始定位
        startLocation();
    }

    private void initLocation() {
        //初始化client
        mLocationClient = new AMapLocationClient(this.getApplicationContext());
        //设置定位参数
        mLocationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        mLocationClient.setLocationListener(mLocationListener);

    }

    private void startLocation() {
        //根据控件的选择，重新设置定位参数
        //resetOption();
        // 设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 启动定位
        mLocationClient.startLocation();
    }

    private void stopLocation() {
        // 停止定位
        mLocationClient.stopLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLocation();
    }

    private void destroyLocation() {
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
            mLocationClient = null;
            mLocationOption = null;


        }
    }


    /**
     * 默认的定位参数
     *
     * @author
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        //   mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mLocationOption.setOnceLocation(true);  //获取一次定位结果：
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }


    //----------以下动态获取权限---------
    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedCheck) {
            checkPermissions(needPermissions);
        }
    }


    /**
     * 检查权限
     *
     * @param
     * @since 2.5.0
     */
    private void checkPermissions(String... permissions) {
        //获取权限列表
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            //list.toarray将集合转化为数组
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }


    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        //for (循环变量类型 循环变量名称 : 要被遍历的对象)
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {      //没有授权
                showMissingPermissionDialog();              //显示提示信息
                isNeedCheck = false;
            }
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage(R.string.notifyMsg);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setPositiveButton(R.string.setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }


    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void initAmap() {
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        // 卫星地图模式
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        //去除左下角高德地图logo
        aMap.getUiSettings().setLogoBottomMargin(-100);
        //去除右下角缩放按钮
        aMap.getUiSettings().setZoomControlsEnabled(false);


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


    /**
     * @description 获取当前位置信息
     * @time 2020/11/19 17:12
     */
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
                String result = MapUtils.getLocationStr(amapLocation);
                Log.e("定位", "aMapLocation is null" + result);
                if (amapLocation.getErrorCode() == 0) {
                    if (isFirstLocation) {
                        isFirstLocation = false;
                        //定位成功回调信息，设置相关消息
                        double currentLat = amapLocation.getLatitude();
                        double currentLon = amapLocation.getLongitude();
//                        currLatLng = new LatLng(currentLat, currentLon);
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














  /*  private void initAmap() {
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

        *//*定位 当前位置*//*
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
    */

    /**
     * 定位回调监听器
     *//*
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

*/

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
    @OnClick({R.id.rly, R.id.rl_search, R.id.iv_layer, R.id.im_toolbox, R.id.tv_measure, R.id.iv_list, R.id.iv_shorthand, R.id.iv_position})
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

            case R.id.iv_position:  //地图定位
                initAmap();
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
            Log.d(TAG, "onActivityResult: " + res);
            tv_title.setText(res);
        }
    }
/*
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
    }*/


}
