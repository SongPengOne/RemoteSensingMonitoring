package com.aipuer.remotesensingmonitoring.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aipuer.remotesensingmonitoring.AddressActivity;
import com.aipuer.remotesensingmonitoring.AddressBean;
import com.aipuer.remotesensingmonitoring.R;

import java.util.ArrayList;
import java.util.List;

public class ExAddressAdapter extends BaseExpandableListAdapter {
    private final AddressActivity context;
    private final List<AddressBean> mBeanFirstItemBases;


    public ExAddressAdapter(List<AddressBean> mBeanFirstItemBases, AddressActivity addressActivity) {
        this.mBeanFirstItemBases = mBeanFirstItemBases;
        Log.d("", "ExAddressAdapter: " + mBeanFirstItemBases);
        this.context = addressActivity;
    }

    //返回的是一级标签的数量
    @Override
    public int getGroupCount() {
        return mBeanFirstItemBases.size();
    }

    //返回的是子标签的数量
    @Override
    public int getChildrenCount(int groupPosition) {
        return mBeanFirstItemBases.get(groupPosition).getBeanSecondItem().size();
    }

    //获得某个一级标签的内容
    @Override
    public Object getGroup(int groupPosition) {
        return mBeanFirstItemBases.get(groupPosition);
    }

    //获得某个一级标签下的子标签
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mBeanFirstItemBases.get(groupPosition).getBeanSecondItem().get(childPosition);
    }

    //一级标签列表的Id
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //二级标签列表的Id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //显示一级标签列表
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParenHolder parenHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_address, null);
            parenHolder = new ParenHolder(convertView);
            convertView.setTag(parenHolder);
        } else {
            parenHolder = (ParenHolder) convertView.getTag();
        }
        parenHolder.tv_itemaddress.setText(mBeanFirstItemBases.get(groupPosition).getName());
        Log.d("str", "getGroupView: " + mBeanFirstItemBases.get(groupPosition).getName());


/**
 checkbox关键点，第一层checkbox的选中状态监听，
 如果勾选中，则把其下的第二层以及第三层都选上，否则相反。
 */

     /*   parenHolder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = parenHolder.cb.isChecked(); //获取单选框
                mBeanFirstItemBases.get(groupPosition).setChecked(checked);
                for (int i = 0; i < mBeanFirstItemBases.get(groupPosition).getBeanSecondItem().size(); i++) {
                    AddressBean.BeanSecondItem beanSecondItem = mBeanFirstItemBases.get(groupPosition).getBeanSecondItem().get(i);
                    beanSecondItem.setCheck(false);
                }
                notifyDataSetChanged();
            }
        });
        *//**通过isCheck属性控制checkbox
         的选中状态，2，3层同理*//*
        parenHolder.cb.setChecked(mBeanFirstItemBases.get(groupPosition).isChecked());*/
        return convertView;
    }

    //显示二级标签列表
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SubHolder subHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_addressarea, null);
            subHolder = new SubHolder(convertView);
            convertView.setTag(subHolder);
        } else {
            subHolder = (SubHolder) convertView.getTag();
        }
        subHolder.itemtv_address.setText(mBeanFirstItemBases.get(groupPosition).getBeanSecondItem().get(childPosition).getName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public static class ParenHolder {
        TextView tv_itemaddress;
        public ImageView img;
        RelativeLayout item_address_rl;

        ParenHolder(View view) {
            tv_itemaddress = view.findViewById(R.id.tv_itemaddress);
            img = view.findViewById(R.id.img);
            item_address_rl = view.findViewById(R.id.item_address_rl);
        }
    }

    static class SubHolder {

        private final TextView itemtv_address;
        private final CheckBox cb_itemadrs;

        SubHolder(View view) {

            itemtv_address = view.findViewById(R.id.tv_itemaddress);
            cb_itemadrs = view.findViewById(R.id.cb_itemadrs);
        }
    }
}
