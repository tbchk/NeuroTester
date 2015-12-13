package com.chameleonsoftware.main_fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chameleonsoftware.neurotester.MocaActivity;
import com.chameleonsoftware.neurotester.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by camaleon2 on 26/11/15.
 */
public class MocaNewFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_mocanew,container,false);

        //ESTABLEZCO LA FECHA
        TextView txtDate = (TextView) rootView.findViewById(R.id.textDate);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        txtDate.setText(sdf.format(date));

        //Establezco el listener del boton en el fragmento
        ImageButton b = (ImageButton) rootView.findViewById(R.id.startMocaBtn);
        b.setOnClickListener(this);

        return rootView;

    }

    //OnclickListener
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startMocaBtn:
                newMocaStart(v);
                break;
        }
    }

    //On CLick Method
    public void newMocaStart(View v){
        Intent i = new Intent(getActivity(), MocaActivity.class);
        startActivity(i);
    }
}
