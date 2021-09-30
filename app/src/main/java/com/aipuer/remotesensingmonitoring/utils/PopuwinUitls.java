package com.aipuer.remotesensingmonitoring.utils;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aipuer.remotesensingmonitoring.MainActivity;
import com.aipuer.remotesensingmonitoring.Popuwin;
import com.aipuer.remotesensingmonitoring.R;
import com.aipuer.remotesensingmonitoring.adapter.PopuwinListAdapter;

import java.util.ArrayList;

public class PopuwinUitls extends PopupWindow {
    private RelativeLayout rl_toob;
    private ImageView iv_list;
    private PopuwinListAdapter popuwinListAdapter;
    private Context context;
    private PopupWindow popupWindow;
    private String city;
    private String area;
    private String protectname;
    private String protectlevel;
    private String functionname;
    private TextView patterntype;
    private String checkstatus;
    private String humanactivity;
    private View inflate;

    private ArrayList<String> strings = new ArrayList<>();
    private ImageView iv_cross;
    private View inflateView;


    public PopuwinUitls(MainActivity mainActivity, RelativeLayout rl_toob, ImageView iv_list, PopuwinListAdapter popuwinListAdapter) {
        this.context = mainActivity;
        this.rl_toob = rl_toob;
        this.iv_list = iv_list;
        this.popuwinListAdapter = popuwinListAdapter;

    }

    public PopuwinUitls(Context thisContext, TextView tv_patterntype) {
        this.context = thisContext;
        patterntype = tv_patterntype;
    }


  /*  public PopuwinUitls(Context thisContext, TextView tv_city, TextView tv_area, TextView tv_protectname, TextView tv_protectlevel, TextView tv_functionname, TextView tv_patterntype, TextView tv_checkstatus, TextView tv_humanactivity) {
        this.context = thisContext;
        city = tv_city.getText().toString();
        area = tv_area.getText().toString();
        protectname = tv_protectname.getText().toString();
        protectlevel = tv_protectlevel.getText().toString();
        functionname = tv_functionname.getText().toString();
        patterntype = tv_patterntype.getText().toString();
        checkstatus = tv_checkstatus.getText().toString();
        humanactivity = tv_humanactivity.getText().toString();
    }


    public PopuwinUitls(Context context) {
        this.context = context;
    }*/

    public void showPop(View view) {

        LayoutInflater ll = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("数字化格式----" + i);
        }
        inflate = ll.inflate(R.layout.lay_listpop, null);
        RecyclerView rly_listpop = inflate.findViewById(R.id.rly_listpop);
        iv_cross = inflate.findViewById(R.id.iv_listcross);
        ImageView iv_popnavigation = inflate.findViewById(R.id.iv_popnavigation);
        iv_popnavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils dialogUtils = new DialogUtils(context);
                dialogUtils.dialog(context);
            }
        });
        /*
         * 数据展示
         * */
        LinearLayoutManager layout = new LinearLayoutManager(context);
        rly_listpop.setLayoutManager(layout);
        popuwinListAdapter = new PopuwinListAdapter(strings, context);
        popuwinListAdapter.Click(new PopuwinListAdapter.OnClick() {
            @Override
            public void getOnClick(TextView textView) {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Popuwin popuwin = new Popuwin(context, rl_toob);
                        popuwin.Popuwin(v);


                    }
                });
            }
        });
        rly_listpop.setAdapter(popuwinListAdapter);

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

        //  popupWindow.showAsDropDown(rl_toob);
        // popupWindow.showAtLocation(rl_toob,Gravity.BOTTOM,x,y);

        // popupWindow.showAsDropDown(rl_toob, x, y);
        //这里就可自定义在上方和下方了 ，这种方式是为了确定在某个位置，某个控件的左边，右边，上边，下边都可以
        // popupWindow.showAtLocation(rl_search, Gravity.BOTTOM, 0,0);

//因为ppw提供了在某个控件下方的方法，所以有些时候需要直接定位在下方时并不用上面的这个方法
        //ppwfilter
        // popupWindow.showAsDropDown(rl_toob);
        //  ppwfilter.showAsDropDown(v);    // 以触发弹出窗的view为基准，出现在view的正下方，弹出的pop_view左上角正对view的左下角  偏移量默认为0,0
        //       ppwfilter.showAsDropDown(v, xoff, yoff);     // 有参数的话，就是一view的左下角进行偏移，xoff正的向左，负的向右. yoff没测，也应该是正的向下，负的向上
        //    ppwfilter.showAsDropDown(parent, xoff, yoff, gravity) //parent:传你当前Layout的id; gravity:Gravity.BOTTOM（以屏幕左下角为参照）... 偏移量会以它为基准点 当x y为0,0是出现在底部居中
        iv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


    }

    public void NoList() {
        if (patterntype.equals("绿盾行动")) {

        }
    }

    public void contextPop(View view) {


        LayoutInflater ll = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = ll.inflate(R.layout.item_basiciform, null);

        strings.notify();

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

        //  popupWindow.showAsDropDown(rl_toob);
        // popupWindow.showAtLocation(rl_toob,Gravity.BOTTOM,x,y);

        // popupWindow.showAsDropDown(rl_toob, x, y);
        //这里就可自定义在上方和下方了 ，这种方式是为了确定在某个位置，某个控件的左边，右边，上边，下边都可以
        // popupWindow.showAtLocation(rl_search, Gravity.BOTTOM, 0,0);

//因为ppw提供了在某个控件下方的方法，所以有些时候需要直接定位在下方时并不用上面的这个方法
        //ppwfilter
        // popupWindow.showAsDropDown(rl_toob);
        //  ppwfilter.showAsDropDown(v);    // 以触发弹出窗的view为基准，出现在view的正下方，弹出的pop_view左上角正对view的左下角  偏移量默认为0,0
        //       ppwfilter.showAsDropDown(v, xoff, yoff);     // 有参数的话，就是一view的左下角进行偏移，xoff正的向左，负的向右. yoff没测，也应该是正的向下，负的向上
        //    ppwfilter.showAsDropDown(parent, xoff, yoff, gravity) //parent:传你当前Layout的id; gravity:Gravity.BOTTOM（以屏幕左下角为参照）... 偏移量会以它为基准点 当x y为0,0是出现在底部居中


    }
  /*  @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            setHeight(height);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            setHeight(height);
        }
    }

*/
    /**
     *Android 7.0以上(包含8.0), popupWindow弹窗位置异常, 解决方案
     * @param pw     popupWindow
     * @param anchor v
     * @param xoff   x轴偏移
     * @param yoff   y轴偏移
     */

/*
    public static void showAsDropDown(final PopupWindow pw, final View anchor, final int xoff, final int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            pw.setHeight(height);
            pw.showAsDropDown(rl_toob, xoff, yoff);
        } else {
            pw.showAsDropDown(anchor, xoff, yoff);
        }
    }*/
}
