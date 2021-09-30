package com.aipuer.remotesensingmonitoring.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aipuer.remotesensingmonitoring.R;
import com.aipuer.uploadlibrary.ui.pop.SpinerPopWindow;

import java.util.List;

/**
 * @Package: com.aipuer.dfjusticebureauoa.utils
 * @Class: SpinnerUtil
 * @Author: lx
 * @Remark: 下拉框
 */
public class SpinnerUtil {
    public static SpinerPopWindow<String> spinerPopWindow;

    /**
     * 下拉框选择
     */
    public static void SetSinnper(final List<String> list, final TextView textView,
                                  final Context context, final boolean states) {
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(list.get(position));
                spinerPopWindow.dismiss();
                if (states) {
                    SpinnerUtil.setTextImage(R.drawable.arrow, textView, context);
                }
            }
        };
        spinerPopWindow = new SpinerPopWindow<String>(context, list, itemClickListener);
        spinerPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (states) {
                    SpinnerUtil.setTextImage(R.drawable.arrow, textView, context);
                }
            }
        });
        spinerPopWindow.setWidth(textView.getWidth());
        spinerPopWindow.showAsDropDown(textView);
        if (states) {
            SpinnerUtil.setTextImage(R.drawable.arrow2, textView, context);
        }
    }
    public static void setTextImage(int resId, TextView textView, Context context) {
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
        textView.setCompoundDrawables(null, null, drawable, null);
    }

}
