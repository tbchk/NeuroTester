package com.chameleonsoftware.neurotester;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chameleonsoftware.moca_fragments.Moca1Fragment;
import com.chameleonsoftware.moca_fragments.Moca2Fragment;
import com.chameleonsoftware.moca_fragments.Moca3Fragment;
import com.chameleonsoftware.moca_fragments.Moca4Fragment;
import com.chameleonsoftware.moca_fragments.MocaDialog;

public class MocaActivity extends AppCompatActivity {

    private Moca1Fragment moca1;
    private Moca4Fragment moca4;
    private MocaUser mocaUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moca_activity_moca);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mocaToolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MocaDialog exitDialog = new MocaDialog();
                exitDialog.show(getFragmentManager(), "tagAlerta");
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle("MOCA TEST");
        toolbar.setNavigationIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.custom_close));

        Intent intent = getIntent();
        mocaUser = new MocaUser(intent.getStringExtra("id"),intent.getStringExtra("date"),
                intent.getStringExtra("name"),intent.getStringExtra("lastname"),
                intent.getStringExtra("email"),intent.getStringExtra("gender"),
                intent.getStringExtra("phone"),intent.getStringExtra("study"));

        moca1 = new Moca1Fragment();
        moca4 = new Moca4Fragment();
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_mocaframe, moca4).commit();
    }

    public void moca1Create(){

    }

    public void moca2Create(View v){
        moca1.stopTimer();
        mocaUser.setMoca1Bitmap(moca1.getResultBitmap());
        mocaUser.setMoca1Time(moca1.getResultTime());


        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_mocaframe, new Moca2Fragment()).commit();
    }
    public void moca3Create(View v){

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_mocaframe, new Moca3Fragment()).commit();
    }
    public void moca4Create(View v){

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_mocaframe, new Moca4Fragment()).commit();
    }

    @Override
    public void onBackPressed() {
        MocaDialog exitDialog = new MocaDialog();
        exitDialog.show(getFragmentManager(),"tagAlerta");
        //super.onBackPressed();
    }
}
