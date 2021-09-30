package com.aipuer.remotesensingmonitoring.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aipuer.remotesensingmonitoring.R;
import com.aipuer.remotesensingmonitoring.utils.StatusBarUtils;

import butterknife.ButterKnife;

/**
 * @Package: com.aipuer.remotesensingmonitoring.base
 * @Class: BaseActivity
 * @Author: lx
 * @Time: 2021/9/15 15:31
 * @Remark: 备注描述
 */
public abstract class BaseActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());
        ButterKnife.bind(this);

        doBusiness(this);
        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        initData();
    }



    /**
     * 抽象方法：必须实现的方法  返回数据不一样 必须实现的方法
     *
     * @description 绑定布局
     * @return  布局id

     */
    public abstract int bindLayout();

   protected  abstract   void   initData();

    /**
     * acticity业务功能
     *
     * @description 做业务操作
     * @param  context 上下文

     */
    public abstract void doBusiness(Context context);
}
