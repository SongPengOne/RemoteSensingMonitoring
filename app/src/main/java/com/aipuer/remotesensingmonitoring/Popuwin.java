package com.aipuer.remotesensingmonitoring;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static com.aipuer.remotesensingmonitoring.R.drawable.switch_white_bg;

public class Popuwin extends PopupWindow {


    private final Context context;
    private final RelativeLayout toob;
    private PopupWindow popupWindow;

    public Popuwin(Context context, RelativeLayout rl_toob) {
        this.context = context;
        this.toob = rl_toob;
    }


    public void Popuwin(View view) {
        LayoutInflater ll = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = ll.inflate(R.layout.item_basiciform, null);
        TextView tv_Information = inflate.findViewById(R.id.tv_Information); //基本信息
        TextView tv_chcek = inflate.findViewById(R.id.tv_chcek); //核查信息
        LinearLayout ll_humantype = inflate.findViewById(R.id.ll_humantype);
        TextView tv_humantype = inflate.findViewById(R.id.tv_humantype);
        ScrollView scr1 = inflate.findViewById(R.id.scr1);   //绿盾基本信息
        ScrollView scr2 = inflate.findViewById(R.id.src2);   //绿盾整改前
        ScrollView src3 = inflate.findViewById(R.id.src3); //绿盾整改中
        ScrollView src4 = inflate.findViewById(R.id.src4); //绿盾整改后
        ImageView iv_cross = inflate.findViewById(R.id.iv_cross); //关闭按钮
        TextView tv_rectification = inflate.findViewById(R.id.tv_rectification);//整改前
        TextView tv_underrectification = inflate.findViewById(R.id.tv_underrectification); //整改中
        TextView tv_Afterrectification = inflate.findViewById(R.id.tv_Afterrectification); //整改后


        tv_Information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_chcek.setBackgroundResource(R.drawable.switch_bg);
                tv_Information.setBackgroundResource(R.drawable.switch_white_bg);
                scr1.setVisibility(View.VISIBLE);
                scr2.setVisibility(View.GONE);
                src3.setVisibility(View.GONE);
                src4.setVisibility(View.GONE);
            }
        });
        tv_chcek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                tv_chcek.setBackgroundResource(R.drawable.switch_white_bg);
                tv_Information.setBackgroundResource(R.drawable.switch_bg);
                scr1.setVisibility(View.GONE);
                scr2.setVisibility(View.VISIBLE);
            }
        });
        ll_humantype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickerView pickerView = new PickerView(context, tv_humantype);
                pickerView.Picker();
            }
        });

        tv_rectification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_underrectification.setTextColor(Color.GRAY);
                tv_rectification.setTextColor(Color.BLACK);
                tv_Afterrectification.setTextColor(Color.GRAY);
                scr1.setVisibility(View.GONE);
                scr2.setVisibility(View.VISIBLE);
                src3.setVisibility(View.GONE);
                src3.setVisibility(View.GONE);
            }
        });
        tv_underrectification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_underrectification.setTextColor(Color.BLACK);
                tv_rectification.setTextColor(Color.GRAY);
                tv_Afterrectification.setTextColor(Color.GRAY);
                scr1.setVisibility(View.GONE);
                scr2.setVisibility(View.GONE);
                src3.setVisibility(View.VISIBLE);
                src4.setVisibility(View.GONE);

            }

        });
        tv_Afterrectification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_underrectification.setTextColor(Color.GRAY);
                tv_rectification.setTextColor(Color.GRAY);
                tv_Afterrectification.setTextColor(Color.BLACK);
                scr1.setVisibility(View.GONE);
                scr2.setVisibility(View.GONE);
                src3.setVisibility(View.GONE);
                src4.setVisibility(View.VISIBLE);
            }
        });

        iv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);


        int x = 0;
        int y = 0;

        /*Android（6.0、7.0、8.0） popupWindow弹窗位置错乱解决方案*/
        if (Build.VERSION.SDK_INT < 24) {
            popupWindow.showAsDropDown(view);
        } else {
            Rect rect = new Rect();
            toob.getGlobalVisibleRect(rect);
            int height = view.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            popupWindow.setHeight(height);
            popupWindow.showAsDropDown(toob, x, y);
        }
    }

}
