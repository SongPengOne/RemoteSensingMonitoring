package com.aipuer.remotesensingmonitoring;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import androidx.viewpager.widget.ViewPager;

import com.aipuer.remotesensingmonitoring.shorthand.ShortAfterRectFragment;
import com.aipuer.remotesensingmonitoring.shorthand.ShortBeforeRectFragment;
import com.aipuer.remotesensingmonitoring.shorthand.ShortUnderRectFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MyDialogFragment extends DialogFragment {


    private ArrayList<String> strings;
    private ArrayList<Fragment> fragments;
    private TabLayout tab_short;
    private ViewPager vp_short;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
        //去出标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View inflate = inflater.inflate(R.layout.shorthand, container, false);
        tab_short = inflate.findViewById(R.id.tab_short);
        vp_short = inflate.findViewById(R.id.vp_short);
        initData();
        return inflate;
    }

    private void initData() {
        strings = new ArrayList<>();
        strings.add("整改前");
        strings.add("整改中");
        strings.add("整改后");

        fragments = new ArrayList<>();
        fragments.add(new ShortBeforeRectFragment());
        fragments.add(new ShortUnderRectFragment());
        fragments.add(new ShortAfterRectFragment());

      PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager(), this, strings, fragments);
        vp_short.setAdapter(pagerAdapter);
        vp_short.setCurrentItem(0);
        //保留(如果不加这句话，默认只加载两个fragment)
        vp_short.setOffscreenPageLimit(3);
        tab_short.setupWithViewPager(vp_short);
    }


    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable( new ColorDrawable(getResources().getColor(R.color.black)));
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
        WindowManager.LayoutParams params = win.getAttributes();
        params.gravity = Gravity.BOTTOM;
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width =  ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        win.setAttributes(params);
    }
    public class PagerAdapter extends FragmentPagerAdapter {


        private final ArrayList<String> strings;
        private final ArrayList<Fragment> fragments;


        public PagerAdapter(FragmentManager supportFragmentManager, MyDialogFragment context, ArrayList<String> strings, ArrayList<Fragment> fragments) {
            super(supportFragmentManager);

            this.strings = strings;
            this.fragments = fragments;
        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return strings.get(position);
        }
    }
}
