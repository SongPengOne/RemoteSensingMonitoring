package com.aipuer.remotesensingmonitoring;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aipuer.remotesensingmonitoring.adapter.PopuwinBottonAdapter;
import com.aipuer.remotesensingmonitoring.adapter.PopuwinListAdapter;
import com.aipuer.remotesensingmonitoring.base.BaseActivity;
import com.aipuer.remotesensingmonitoring.utils.DialogUtils;
import com.aipuer.remotesensingmonitoring.utils.PopuwinUitls;
import com.aipuer.remotesensingmonitoring.utils.StatusBarUtils;
import com.aipuer.remotesensingmonitoring.utils.TopMenuHeader;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {

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


    @Override
    public int bindLayout() {
        StatusBarUtils.setColor(this, getResources().getColor(R.color.event_dark_blue));
        return R.layout.activity_main;

    }

    @Override
    protected void initData() {

    }

    @Override
    public void doBusiness(Context context) {

    }

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


}
