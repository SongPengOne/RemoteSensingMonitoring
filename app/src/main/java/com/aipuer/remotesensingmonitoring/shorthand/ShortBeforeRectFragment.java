package com.aipuer.remotesensingmonitoring.shorthand;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aipuer.remotesensingmonitoring.R;

import java.util.ArrayList;

public class ShortBeforeRectFragment extends Fragment {
    private ArrayList<Fragment> fragments; //整改前
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.beforefragment,container,false);
        return view;
    }


}
