package com.aipuer.remotesensingmonitoring.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aipuer.remotesensingmonitoring.MainActivity;
import com.aipuer.remotesensingmonitoring.R;

import java.util.ArrayList;

public class PopuwinBottonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<String> list;
    private final MainActivity context;

    public PopuwinBottonAdapter(ArrayList<String> objects, MainActivity mainActivity) {
        this.list = objects;
        this.context = mainActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_pop, null);
        return new PoponHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PoponHolder poponHolder = (PoponHolder) holder;
        poponHolder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class PoponHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public PoponHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_pop);


        }
    }
}
