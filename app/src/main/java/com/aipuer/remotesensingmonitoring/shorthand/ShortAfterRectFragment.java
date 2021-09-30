package com.aipuer.remotesensingmonitoring.shorthand;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aipuer.remotesensingmonitoring.R;

public class ShortAfterRectFragment extends Fragment {  //整改后

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.afterfragment, null);
        return view;
    }
}
