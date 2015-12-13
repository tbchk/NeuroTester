package com.chameleonsoftware.main_fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

        View rootView = inflater.inflate(R.layout.main_fragment_mocanew,container,false);


        FloatingActionButton mocaHotBtn = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mocaHotBtn.setVisibility(View.GONE);


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
        String date = ((TextView) getActivity().findViewById(R.id.textDate)).getText().toString();
        String name = ((EditText) getActivity().findViewById(R.id.nameEdit)).getText().toString();
        String lastname = ((EditText) getActivity().findViewById(R.id.lastnameEdit)).getText().toString();
        String study = ((EditText) getActivity().findViewById(R.id.studyEdit)).getText().toString();
        String email = ((EditText) getActivity().findViewById(R.id.emailEdit)).getText().toString();
        String phone = ((EditText) getActivity().findViewById(R.id.phoneEdit)).getText().toString();
        String gender;

        boolean mSelected = getActivity().findViewById(R.id.radioButton).isSelected();

        if (mSelected){ gender = "M";}
        else gender="F";

        Intent i = new Intent(getActivity(), MocaActivity.class);
        i.putExtra("date",date);
        i.putExtra("name",name);
        i.putExtra("lastname",lastname);
        i.putExtra("study",study);
        i.putExtra("email",email);
        i.putExtra("phone",phone);
        i.putExtra("gender",gender);
        startActivity(i);
    }
}
