package com.aipuer.remotesensingmonitoring.utils;

import android.content.Context;
import android.graphics.Color;

import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package: com.aipuer.railwayemergencycommand.utils
 * @Class: RequestParamAndFlagUtils
 * @Author: lx
 * @Time: 2021/9/15 15:11
 * @Remark: 接口标识分类以及封装的请求参数map
 */
public class RequestParamAndFlagUtils {

    /**
     * @description 初始化加载弹框动画
     * @author syy
     * @time 2020/11/30 16:49
     */
    public static ZLoadingDialog getLoadingDialog(Context context, String hintText) {
        ZLoadingDialog dialog = new ZLoadingDialog(context);
        //设置类型
        dialog.setLoadingBuilder(Z_TYPE.SINGLE_CIRCLE)
                //颜色
                .setLoadingColor(Color.BLACK)
                .setHintText(hintText)
                .setHintTextSize(10)
                .show();
        return dialog;
    }


    /**
     * 用户登录
     */
    public static final String FLAG_USERINFO_LOGIN = "用户登录";

    /**
     * @param telephone 用户电话号码
     * @param pwd       密码
     */
    public static Map getLoginParamMap(String telephone, String pwd) {
        Map<String, String> map = new HashMap<>(2);
        map.put("telephone", telephone);
        map.put("pwd", pwd);
        return map;
    }



}
