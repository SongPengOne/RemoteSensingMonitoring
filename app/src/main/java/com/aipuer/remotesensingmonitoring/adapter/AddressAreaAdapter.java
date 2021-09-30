package com.aipuer.remotesensingmonitoring.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aipuer.remotesensingmonitoring.AddressActivity;
import com.aipuer.remotesensingmonitoring.R;

import java.util.ArrayList;

public class AddressAreaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<String> list;
    private final AddressActivity context;
    private OnListener onListener;


    public AddressAreaAdapter(ArrayList<String> objects, AddressActivity addressActivity) {
        this.list = objects;
        this.context = addressActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_addressarea, null);
        return new AddressHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AddressHolder addressHolder = (AddressHolder) holder;
        addressHolder.textView.setText(list.get(position));
        addressHolder.iv_choose.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (onListener != null) {
                    onListener.Click(list.get(position),addressHolder.iv_choose);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class AddressHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView iv_choose;

        public AddressHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_itemaddress);
            iv_choose = itemView.findViewById(R.id.iv_choose);


        }
    }

    public interface OnListener {
        void Click(String title, ImageView imageView);
    }

    public void onLongItem(OnListener onListener) {
        this.onListener = onListener;
    }

}
