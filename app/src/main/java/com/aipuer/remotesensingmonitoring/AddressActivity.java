package com.aipuer.remotesensingmonitoring;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.util.Log;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;


import com.aipuer.remotesensingmonitoring.adapter.ExAddressAdapter;
import com.aipuer.remotesensingmonitoring.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 */
public class AddressActivity extends BaseActivity {

    private static final String TAG = "AddressActivity";
   /* @BindView(R.id.rlv)
    RecyclerView recyclerView;

    @BindView(R.id.rlv_area)
    RecyclerView rlv_area;
*/

    @BindView(R.id.exlist_lol)
    ExpandableListView expandableListView;  //二级列表

    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.iv_addressback)
    ImageView iv_addressback;
    @BindView(R.id.tv_area)
    TextView tv_area;
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    @BindView(R.id.tv_shi)
    TextView tv_shi;
    @BindView(R.id.tv_xian)
    TextView tv_xian;

    private List<AddressBean> mBeanFirstItemBases = new ArrayList<>();//数据源
    private ExAddressAdapter exAddressAdapter;

    private boolean checked;
    private int tesp = -1;  //选中

    private ImageView item_img;
    private boolean check = false;
    private int flag = -1;

    @Override
    public int bindLayout() {
        return R.layout.activity_address;
    }

    @Override
    public void doBusiness(Context context) {
    }



    @Override
    protected void initData() {


        AddressBean beanFirstItemBase1 = new AddressBean();
        beanFirstItemBase1.setName("郑州市");
        List<AddressBean.BeanSecondItem> beanSecondItems1 = new ArrayList<>();
        beanSecondItems1.add(new AddressBean.BeanSecondItem("金水区"));
        beanSecondItems1.add(new AddressBean.BeanSecondItem("管城区"));
        beanSecondItems1.add(new AddressBean.BeanSecondItem("中原区"));
        beanFirstItemBase1.setBeanSecondItem(beanSecondItems1);


        AddressBean addressBean = new AddressBean();
        addressBean.setName("哈尔滨市");
        ArrayList<AddressBean.BeanSecondItem> beanSecondItems2 = new ArrayList<>();
        beanSecondItems2.add(new AddressBean.BeanSecondItem("道里区"));
        beanSecondItems2.add(new AddressBean.BeanSecondItem("南岗区"));
        beanSecondItems2.add(new AddressBean.BeanSecondItem("巴彦县"));
        addressBean.setBeanSecondItem(beanSecondItems2);


        AddressBean beanFirstItemBase3 = new AddressBean();
        beanFirstItemBase3.setName("北京市");
        ArrayList<AddressBean.BeanSecondItem> beanSecondItems3 = new ArrayList<>();
        beanSecondItems3.add(new AddressBean.BeanSecondItem("昌平区"));
        beanSecondItems3.add(new AddressBean.BeanSecondItem("海淀区"));
        beanSecondItems3.add(new AddressBean.BeanSecondItem("朝阳区"));
        beanFirstItemBase3.setBeanSecondItem(beanSecondItems3);
        mBeanFirstItemBases.add(beanFirstItemBase1);
        mBeanFirstItemBases.add(addressBean);
        mBeanFirstItemBases.add(beanFirstItemBase3);
        Log.d(TAG, "initDatamBeanFirstItemBases: " + mBeanFirstItemBases.toString());


        exAddressAdapter = new ExAddressAdapter(mBeanFirstItemBases, this);
        expandableListView.setAdapter(exAddressAdapter);

        //设置组项单击事件
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            private ImageView img;

            /**
             * @param groupPosition  父布局下标
             */
            @SuppressLint("ResourceAsColor")
            @Override
            public void onGroupExpand(int groupPosition) {

                for (int i = tesp, count = expandableListView.getExpandableListAdapter().getGroupCount(); i < count; i++) {
                    if (groupPosition != i) {// 关闭其他分组
                        expandableListView.collapseGroup(i);
                    }
                }

                for (AddressBean addressBean : mBeanFirstItemBases) { //遍历list集合中的数据
                    addressBean.setChecked(false);  //初始化都为flase
                }
                checked = mBeanFirstItemBases.get(groupPosition).isChecked();
                img = exAddressAdapter.getGroupView(groupPosition, checked, null, null).findViewById(R.id.img);//调用适配器父布局
                if (tesp == -1) {  //选中
                    mBeanFirstItemBases.get(groupPosition).setChecked(true);
                    img.setImageResource(R.drawable.ic_selected);
                    tesp = groupPosition;
                } else if (tesp == groupPosition) {  //同一个item选中变未选中
                    for (AddressBean addressBean : mBeanFirstItemBases) { //遍历list集合中的数据
                        addressBean.setChecked(false);  //初始化都为flase

                    }
                    img.setImageResource(R.drawable.ic_unselected);
                } else if (tesp != groupPosition) {   //不是同一个item选中当前的，去除上一个选中的
                    for (AddressBean addressBean : mBeanFirstItemBases) { //遍历list集合中的数据
                        addressBean.setChecked(false);  //初始化都为flase
                    }
                    mBeanFirstItemBases.get(groupPosition).setChecked(true);//选中当前的
                    img.setImageResource(R.drawable.ic_unselected);
                    tesp = groupPosition;
                }

                exAddressAdapter.notifyDataSetChanged();
                tv_address.setText(mBeanFirstItemBases.get(groupPosition).getName());
                tv_area.setBackground(getResources().getDrawable(R.drawable.area_bg));
                tv_area.setTextColor(R.color.event_dark_blue);

                tv_address.setTextColor(Color.WHITE);
                tv_address.setBackground(getResources().getDrawable(R.color.event_dark_blue));
                tv_sure.setVisibility(View.GONE);
            }
        });


        /**
         * @param parent
         * @param v
         * @param groupPosition 父布局下标
         * @param childPosition  子布局下标
         * @param id   子条目布局id
         * @return
         */
        //设置子项单击事件
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                item_img = exAddressAdapter.getChildView(groupPosition, childPosition, check, null, null).findViewById(R.id.img_item);
                for (int i = 0; i < mBeanFirstItemBases.size(); i++) {
                    if (mBeanFirstItemBases.get(i).isChecked()) {   //父布局被选中的

                        for (int j = 0; j < mBeanFirstItemBases.get(groupPosition).getBeanSecondItem().size(); j++) {
                            mBeanFirstItemBases.get(groupPosition).getBeanSecondItem().get(childPosition).setCheck(false); //第一次进入子布局默认是flase
                        }

                        if (flag == -1) { //选中
                            mBeanFirstItemBases.get(groupPosition).getBeanSecondItem().get(childPosition).setCheck(true);
                            item_img.setImageResource(R.drawable.ic_selected);
                        } else if (flag != childPosition) {
                            for (int j = 0; j < mBeanFirstItemBases.get(groupPosition).getBeanSecondItem().size(); j++) {
                                mBeanFirstItemBases.get(groupPosition).getBeanSecondItem().get(childPosition).setCheck(false);
                            }

                            mBeanFirstItemBases.get(groupPosition).getBeanSecondItem().get(childPosition).setCheck(true);
                            item_img.setImageResource(R.drawable.ic_unselected);
                        }
                    }
                }
                exAddressAdapter.notifyDataSetChanged();
                tv_address.setBackground(getResources().getDrawable(R.drawable.area_bg));
                tv_address.setTextColor(R.color.event_dark_blue);
                tv_area.setTextColor(Color.WHITE);
                String area = mBeanFirstItemBases.get(groupPosition).getBeanSecondItem().get(childPosition).getName();
                tv_area.setText(mBeanFirstItemBases.get(groupPosition).getBeanSecondItem().get(childPosition).getName());
                tv_area.setBackground(getResources().getDrawable(R.color.event_dark_blue));
                tv_sure.setVisibility(View.VISIBLE);
                tv_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = getIntent();
                        intent.putExtra("result", area);
                        setResult(1001, intent);
                        finish();
                    }
                });
                return true;
            }
        });


        iv_addressback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
