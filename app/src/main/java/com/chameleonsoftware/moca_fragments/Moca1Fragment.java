package com.chameleonsoftware.moca_fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chameleonsoftware.neurotester.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class Moca1Fragment extends Fragment {

    public Moca1Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_moca, container, false);
    }
}
