package com.chameleonsoftware.moca_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chameleonsoftware.neurotester.R;

/**
 * Created by camaleon2 on 26/11/15.
 */
public class Moca2Fragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_moca3,container,false);
        return rootView;

    }
}