package com.aipuer.remotesensingmonitoring.utils;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aipuer.remotesensingmonitoring.R;

public class TopMenuHeader {


        // 顶部菜单左边按钮
        public ImageView topMenuLeft;

        // 顶部菜单右边按钮
       // public Button topMenuRight;

        // 顶部菜单文字
        public TextView topMenuTitle;

        public TopMenuHeader(View v) {

            // 右边按钮
          //  topMenuRight = (Button) v.findViewById(R.id.top_menu_right);

            // 左边按钮
            topMenuLeft = (ImageView) v.findViewById(R.id.top_menu_left);

            // 顶部中间文字
            topMenuTitle = (TextView) v.findViewById(R.id.top_menu_title);

        }

}
