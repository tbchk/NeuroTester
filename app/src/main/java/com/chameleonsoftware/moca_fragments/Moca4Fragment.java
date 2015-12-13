package com.chameleonsoftware.moca_fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.chameleonsoftware.neurotester.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by camaleon2 on 26/11/15.
 */




public class Moca4Fragment extends Fragment{

    ImageView visor;
    ImageButton recordButton;


    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    private static final int RESULT_OK = -1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.moca_fragment_moca4, container, false);

        visor = (ImageView) rootView.findViewById(R.id.moca4View);
        recordButton = (ImageButton) rootView.findViewById(R.id.moca4RecordButton);

        Bitmap originalBm = BitmapFactory.decodeResource(getResources(), R.drawable.leonmoca);
        Bitmap mutableBitmap = originalBm.copy(Bitmap.Config.ARGB_8888, true);

        visor.setImageBitmap(mutableBitmap);

        PackageManager pm = getActivity().getPackageManager();

        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        // In code listener to avoid new functions
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognitionActivity();
            }

        });



        return rootView;
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {

            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);


            final CharSequence[] items = new CharSequence[matches.size()];

            for(int i = 0; i < matches.size(); i++){

                items[i] = matches.get(i);

            }


            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Select Result");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                   // txtSearch.setText(items[item].toString());
                    Log.e("Reconocido",items[item].toString());
                }
            });

            AlertDialog alert = builder.create();

            alert.show();
        }


        super.onActivityResult(requestCode, resultCode, data);


    }

    private void startVoiceRecognitionActivity(){
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "");
            startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

}
