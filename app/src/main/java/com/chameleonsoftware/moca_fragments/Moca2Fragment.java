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

import com.chameleonsoftware.neurotester.MocaUser;
import com.chameleonsoftware.neurotester.R;

/**
 * Created by camaleon2 on 26/11/15.
 */
public class Moca2Fragment extends Fragment implements View.OnTouchListener{


    private MocaUser mocaUser;

    private ImageView imageVisor1;
    private ImageView imageVisor2;

    private ImageButton refreshBtn;
    private ImageButton nextButton;

    Canvas canvas;
    Paint paint;
    Bitmap bmBuffer;

    private int[] posA = new int[2];
    private int[] posB = new int[2];

    private int[] pixelSizeAreaeq = new int[2];

    //TIMER SETTINGS
    private ImageButton startBtn;
    private TextView timerTextView;
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

        View rootView = inflater.inflate(R.layout.moca_fragment2,container,false);
        imageVisor1 = (ImageView) rootView.findViewById(R.id.moca2view1);
        imageVisor2 = (ImageView) rootView.findViewById(R.id.moca4View);

        startBtn = (ImageButton) rootView.findViewById(R.id.moca4StartButton);
        refreshBtn = (ImageButton) rootView.findViewById(R.id.moca2RefreshButton);
        nextButton = (ImageButton) rootView.findViewById(R.id.moca4NextButton);

        timerTextView = (TextView) rootView.findViewById(R.id.moca2TimerText);

        //Search for the best drawing area size
        setDrawingArea();
        //Cube Drawing
        createCubeSample();
        //Drawing area
        createdrawingarea();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Visuoespacial/Ejecutiva 2");


        refreshBtn.setEnabled(false);
        nextButton.setEnabled(false);
        imageVisor2.setEnabled(false);

        imageVisor2.setOnTouchListener(this);
        startBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startTime = System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);

                imageVisor2.setEnabled(true);
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


    private void createCubeSample(){
        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        float totalDIP_X = metrics.xdpi;
        float totalDIP_Y = metrics.ydpi;


        double[] p1 = {0.2,0};
        double[] p3 = {0.2+0.78,0.78};
        double[] p5 = {0,0.2};
        double[] p7 = {0.78, 0.2+0.78};

        int[] p1_pix = {(int)(p1[0]*totalDIP_X),(int)(p1[1]*totalDIP_Y)+3};
        int[] p3_pix = {(int)(p3[0]*totalDIP_X)-3,(int)(p3[1]*totalDIP_Y)};

        int[] p5_pix = {(int)(p5[0]*totalDIP_X)+3,(int)(p5[1]*totalDIP_Y)};
        int[] p7_pix = {(int)(p7[0]*totalDIP_X),(int)(p7[1]*totalDIP_Y)-5};


        int size = (int) ((0.2+0.78)*totalDIP_X);

        Bitmap Cube = Bitmap.createBitmap(size,size,Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(Cube);
        Paint painterc = new Paint();

        painterc.setColor(Color.BLACK);
        painterc.setStrokeWidth(8);
        painterc.setStyle(Paint.Style.STROKE);

        //Dibujando el cubo
        cv.drawRect(p1_pix[0], p1_pix[1], p3_pix[0], p3_pix[1], painterc);
        cv.drawRect(p5_pix[0], p5_pix[1], p7_pix[0], p7_pix[1], painterc);

        cv.drawLine(p1_pix[0], p1_pix[1], p5_pix[0], p5_pix[1], painterc);
        cv.drawLine(p3_pix[0], p3_pix[1], p7_pix[0], p7_pix[1], painterc);

        cv.drawLine(p3_pix[0], p1_pix[1], p7_pix[0], p5_pix[1], painterc);
        cv.drawLine(p5_pix[0], p7_pix[1], p1_pix[0], p3_pix[1], painterc);

        imageVisor1.setImageBitmap(Cube);

    }

        private void createdrawingarea(){
            bmBuffer = Bitmap.createBitmap(pixelSizeAreaeq[0],pixelSizeAreaeq[1], Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bmBuffer);
            imageVisor2.setImageBitmap(bmBuffer);

            paint = new Paint();
            paint.setColor(Color.GRAY);
            paint.setStrokeWidth(15);
            paint.setStyle(Paint.Style.STROKE);

            canvas.drawRect(0, 0, pixelSizeAreaeq[0], pixelSizeAreaeq[1], paint);

            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(8);
            imageVisor2.setImageBitmap(bmBuffer);
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
                imageVisor2.invalidate();

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




    public void setUser(MocaUser mocaUser){
        this.mocaUser = mocaUser;
    }
}
