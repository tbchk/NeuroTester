package com.chameleonsoftware.moca_fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chameleonsoftware.neurotester.R;

/**
 * Created by camaleon2 on 26/11/15.
 */




public class Moca3Fragment extends Fragment implements View.OnTouchListener {

    ImageView visor;
    Bitmap bm;
    Canvas canvas;
    private ImageButton startBtn;
    private ImageButton refreshBtn;
    private ImageButton nextButton;


    Paint paint;

    private int[] posA = new int[2];
    private int[] posB = new int[2];

    private int[] pixelSizeAreaeq = new int[2];

    //TIMER SETTINGS
    TextView timerTextView;
    long startTime = 0;
    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timerTextView.setText(String.format("(%d:%02d)", minutes, seconds));

            timerHandler.postDelayed(this, 500);
        }
    };
    // END OF TIMER SETTINGS


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.moca_fragment_moca3, container, false);

        setDrawingArea();

        visor = (ImageView) rootView.findViewById(R.id.moca4View);
        startBtn = (ImageButton) rootView.findViewById(R.id.moca4StartButton);
        refreshBtn = (ImageButton) rootView.findViewById(R.id.moca2RefreshButton);
        nextButton = (ImageButton) rootView.findViewById(R.id.moca4NextButton);
        timerTextView = (TextView) rootView.findViewById(R.id.moca3TimerText);


        setDrawingArea();
        createdrawingarea();

        visor.setEnabled(false);
        refreshBtn.setEnabled(false);
        nextButton.setEnabled(false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Visuoespacial/Ejecutiva 3");

        visor.setOnTouchListener(this);
        startBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startTime = System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);

                visor.setEnabled(true);
                refreshBtn.setEnabled(true);
                nextButton.setEnabled(true);

                startBtn.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.darker_gray));
                refreshBtn.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.holo_green_dark));
                nextButton.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.holo_green_dark));
                startBtn.setEnabled(false);
            }

        });

        refreshBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                bm.recycle();
                createdrawingarea();
            }

        });


        return rootView;

    }
    private void setDrawingArea(){
        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        float totalDIP_X = metrics.xdpi;
        float totalDIP_Y = metrics.ydpi;
        pixelSizeAreaeq[0] = (int)(2.76*totalDIP_X);
        pixelSizeAreaeq[1] = (int)(2.56*totalDIP_Y);

        if(metrics.widthPixels < pixelSizeAreaeq[0]){
            pixelSizeAreaeq[0] = metrics.widthPixels-70;

            //Nuevas pulgadas de la imágen
            double newinchesL = pixelSizeAreaeq[0]/totalDIP_X;
            double newinchesA = 2.56*newinchesL/2.76;

            pixelSizeAreaeq[1] = (int)(newinchesA*totalDIP_Y);
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:

                posA[0] = (int)event.getX();
                posA[1] = (int)event.getY();

                posB[0] = posA[0];
                posB[1] = posA[1];


                break;

            case MotionEvent.ACTION_MOVE:
                posB[0]=(int)event.getX();
                posB[1] = (int)event.getY();

                canvas.drawLine(posA[0], posA[1],posB[0],posB[1], paint);
                visor.invalidate();

                posA[0] = posB[0];
                posA[1] = posB[1];

                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return true;
    }

    private void createdrawingarea(){
        bm = Bitmap.createBitmap(pixelSizeAreaeq[0],pixelSizeAreaeq[1], Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bm);
        visor.setImageBitmap(bm);

        paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(0, 0, pixelSizeAreaeq[0], pixelSizeAreaeq[1], paint);

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(8);
        visor.setImageBitmap(bm);
    }
}
