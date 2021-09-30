package com.aipuer.remotesensingmonitoring;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.aipuer.remotesensingmonitoring.shorthand.ShortAfterRectFragment;
import com.aipuer.remotesensingmonitoring.shorthand.ShortBeforeRectFragment;
import com.aipuer.remotesensingmonitoring.shorthand.ShortUnderRectFragment;
import com.google.android.material.tabs.TabLayout;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;

public class Popushorthand extends AppCompatActivity {

    private final MainActivity context;
    private final RelativeLayout rl_toob;
    private View inflate;
    private PopupWindow popupWindow;

    private ArrayList<Fragment> fragments;
    private ArrayList<String> strings;



    private ViewPager vp_short;
    private MagicIndicator tab_short;

    public Popushorthand(MainActivity mainActivity, RelativeLayout rl_toob) {
        this.context = mainActivity;
        this.rl_toob = rl_toob;
    }

    public void showPop(View view) {

        LayoutInflater ll = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflate = ll.inflate(R.layout.shorthand, null);
        tab_short = inflate.findViewById(R.id.tab_short);
        vp_short = inflate.findViewById(R.id.vp_short);
        init();
        initMagicIndicator();
        initFragment();



        /*
         * 设置popuwin布局
         * ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT 覆盖布局
         * */
        popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);

        inflate.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWindow.setContentView(inflate);

        /*
         * showAtLocation相对于父布局控件的位置
         * showAsDropDown 相对于某个控件的位置
         * X 轴为正向右 为负向左
         * Y 轴为负 popuwin向上   为正向下
         * */
        int x = 0;
        int y = 0;

        /*Android（6.0、7.0、8.0） popupWindow弹窗位置错乱解决方案*/
        if (Build.VERSION.SDK_INT < 24) {
            popupWindow.showAsDropDown(view);
        } else {
            Rect rect = new Rect();
            rl_toob.getGlobalVisibleRect(rect);
            int height = view.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            popupWindow.setHeight(height);
            popupWindow.showAsDropDown(rl_toob, x, y);
        }

    }

private void init(){


    strings = new ArrayList<>();
        strings.add("整改前");
        strings.add("整改中");
        strings.add("整改后");

}

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new ShortBeforeRectFragment());
        fragments.add(new ShortUnderRectFragment());
        fragments.add(new ShortAfterRectFragment());




          PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),context,strings,fragments);
           vp_short.setAdapter(pagerAdapter);
        vp_short.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        vp_short.setCurrentItem(0);
                        break;
                    case 1:
                        vp_short.setCurrentItem(1);
                        break;
                    case 2:
                        vp_short.setCurrentItem(2);
                        break;
                        default:
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return strings.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setText(strings.get(index));
                colorTransitionPagerTitleView.setTextSize(15);
                colorTransitionPagerTitleView.getNormalColor();
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#5D7AB2"));

                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vp_short.setCurrentItem(index);
                        switch (index){
                            case 0:
                                vp_short.setCurrentItem(0);
                                break;
                            case 1:
                                vp_short.setCurrentItem(1);
                                break;
                            case 2:
                                vp_short.setCurrentItem(2);
                                break;

                        }
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                linePagerIndicator.setLineHeight(UIUtil.dip2px(context,4));
                linePagerIndicator.setLineWidth(UIUtil.dip2px(context,30));

                return linePagerIndicator;
            }
        });


        tab_short.setNavigator(commonNavigator);
        ViewPagerHelper.bind(tab_short, vp_short);
    }



    public class PagerAdapter extends FragmentPagerAdapter {

        private final MainActivity context;
        private final ArrayList<String> strings;
        private final ArrayList<Fragment> fragments;



        public PagerAdapter(androidx.fragment.app.FragmentManager supportFragmentManager, MainActivity context, ArrayList<String> strings, ArrayList<Fragment> fragments) {
            super(supportFragmentManager);
            this.context=context;
            this.strings=strings;
            this.fragments=fragments;
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
