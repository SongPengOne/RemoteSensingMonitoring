package com.aipuer.remotesensingmonitoring.utils;

import android.app.Dialog;
import android.content.Context;
import android.hardware.Camera;
import android.support.v4.app.INotificationSideChannel;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.aipuer.remotesensingmonitoring.MainActivity;
import com.aipuer.remotesensingmonitoring.R;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DialogUtils extends Dialog {

    private Context thisContext;
    private Dialog dialog;
    ;

    public DialogUtils(@NonNull Context context) {
        super(context);
        this.thisContext = context;
    }

    public DialogUtils(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogUtils(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    public void dialog(Context context) {

        dialog = new Dialog(context, R.style.dialog);

        dialog.show();
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_filter, null);
        dialog.setContentView(inflate);
        dialog.setCanceledOnTouchOutside(true);

        ImageView imageView = inflate.findViewById(R.id.iv_filter_cross);
        TextView tv_city = inflate.findViewById(R.id.tv_city);//城市
        TextView tv_area = inflate.findViewById(R.id.tv_area); //区县
        TextView tv_protectname = inflate.findViewById(R.id.tv_protectname);//保护区名称
        TextView tv_protectlevel = inflate.findViewById(R.id.tv_protectlevel);//保护区级别
        TextView tv_functionname = inflate.findViewById(R.id.tv_functionname);//功能区名称
        TextView tv_patterntype = inflate.findViewById(R.id.tv_patterntype);//图版类型
        TextView tv_checkstatus = inflate.findViewById(R.id.tv_checkstatus);//核查状态
        TextView tv_humanactivity = inflate.findViewById(R.id.humanactivity);//人类活动类型
        TextView tv_confirm = inflate.findViewById(R.id.tv_confirm);//确认按钮
        TextView tv_reset = inflate.findViewById(R.id.tv_reset); //重置按钮
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ArrayList<String> city = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            city.add("郑州");
            city.add("哈尔滨");
            city.add("洛阳");
            city.add("许昌");
            city.add("嵩山");
        }


        ArrayList<String> area = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            area.add("金水区");
            area.add("管城区");
            area.add("中原区");
        }

        ArrayList<String> haerb = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            haerb.add("南岗区");
            haerb.add("道外区");
            haerb.add("道里区");
        }


        tv_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpinnerUtil.SetSinnper(city, tv_city, thisContext, true);

            }
        });
        tv_protectname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpinnerUtil.SetSinnper(city, tv_protectname, thisContext, true);
            }
        });
        tv_protectlevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpinnerUtil.SetSinnper(city, tv_protectlevel, thisContext, true);
            }
        });

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            strings.add("郑州");
            strings.add("哈尔滨");
            strings.add("洛阳");
            strings.add("许昌");
            strings.add("嵩山");
        }


        String[] a = {"绿盾行动", "人类行动", "季节变化", "核查速记"};
        HashSet<String> set = new HashSet<>();   //去重集合
        ArrayList<String> leixing = new ArrayList<>(); //集合列表

        for (int i = 0; i < a.length; i++) {
            set.add(a[i]);
        }
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            leixing.add(iterator.next());
        }


        tv_patterntype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpinnerUtil.SetSinnper(leixing, tv_patterntype, thisContext, true);
            }
        });


        tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消按钮
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            //确认按钮

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                PopuwinUitls popuwinUitls = new PopuwinUitls(thisContext, tv_patterntype);
                popuwinUitls.NoList();


            }
        });


    }


}
