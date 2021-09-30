package com.aipuer.remotesensingmonitoring.shorthand;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aipuer.remotesensingmonitoring.R;

public class ShortUnderRectFragment extends Fragment {  //整改中

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.underfragment, null);
        return inflate;
    }
}
