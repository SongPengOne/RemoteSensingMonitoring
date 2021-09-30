package com.aipuer.remotesensingmonitoring.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aipuer.remotesensingmonitoring.MainActivity;
import com.aipuer.remotesensingmonitoring.R;
import com.aipuer.remotesensingmonitoring.utils.DialogUtils;
import com.aipuer.remotesensingmonitoring.utils.PopuwinUitls;

import java.util.ArrayList;

public class PopuwinListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<String> list;
    private final Context context;
    private OnClick click;

    public PopuwinListAdapter(ArrayList<String> objects, Context context) {
        this.list = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_listpop, parent, false);

        return new PoponHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PoponHolder poponHolder = (PoponHolder) holder;
        poponHolder.textView.setText(list.get(position));click.getOnClick(poponHolder.tv_listcheck);
       /* poponHolder.tv_listcheck.setOnClickListener(new View.OnClickListener() {   //查看详情
            @Override
            public void onClick(View v) {

            }
        });*/
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PoponHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private TextView tv_listcheck;
        private TextView tv_listhoose;
        private LinearLayout ll_pop_item;

        public PoponHolder(@NonNull View itemView) {
            super(itemView);
            ll_pop_item = itemView.findViewById(R.id.ll_pop_item);
            textView = itemView.findViewById(R.id.tv_listpop);
            tv_listcheck = itemView.findViewById(R.id.tv_listcheck); //查看
            tv_listhoose = itemView.findViewById(R.id.tv_listhoose);

        }
    }

    public  interface OnClick{
        void  getOnClick(TextView textView);
    }
    public  void  Click(OnClick onClick){
        this.click=onClick;
    }
}
