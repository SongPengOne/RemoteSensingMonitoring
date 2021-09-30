package com.aipuer.remotesensingmonitoring;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;

public class PickerView {

    private final Context context;
    private final TextView str;
    private ArrayList<String> options1Items = new ArrayList<>();

    public PickerView(Context context, TextView str) {
        this.context = context;
        this.str = str;
    }

    public void Picker() {

        options1Items.add("工况用地");
        options1Items.add("水泥用地");
        options1Items.add("他们用地");


        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                String string = options1Items.get(options1);
                str.setText(string);
            }

        })

                .isDialog(true)
                .setContentTextSize(18)//滚轮文字大小
                .setSelectOptions(0)//设置选择第一个
                .setOutSideCancelable(false)//点击背的地方不消失
                .build();
        pvOptions.setPicker(options1Items);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        params.leftMargin = 0;
        params.rightMargin = 0;
        ViewGroup contentContainer = pvOptions.getDialogContainerLayout();
        contentContainer.setLayoutParams(params);
        pvOptions.getDialog().getWindow().setGravity(Gravity.BOTTOM);//可以改成Bottom
        pvOptions.getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        pvOptions.show();

/*





        pvOptions = new  OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()

            }
        })
               *//* .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("城市选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
                .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
                .setLinkage(false)//设置是否联动，默认true
                .setLabels("省", "市", "区")//设置选择的三级单位
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(1, 1, 1)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true*//*
                .setContentTextSize(18)//滚轮文字大小
                .setSelectOptions(1)//设置默认选中项
                .build();

        pvOptions.setPicker(options1Items);//添加数据源*/

    }


}
