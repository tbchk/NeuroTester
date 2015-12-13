package com.chameleonsoftware.moca_fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
public class Moca1Fragment extends Fragment implements View.OnTouchListener{

    MocaUser mocaUser;

    //Botones
    private ImageButton startBtn;
    private ImageButton refreshBtn;
    private ImageButton nextButton;



    private ImageView imageVisor;
    private Canvas canvas;
    private Paint paint;

    private int[] posA = new int[2];
    private int[] posB = new int[2];

    //Dimensiones en pulgadas de la imágen estándar (Validación)
    private double[] realSizeArea = {2.76,2.56};

    //Dimensiones equivalente en pixels de la imágen real
    private int[] pixelSizeAreaeq = new int[2];


    private Bitmap mutableBitmap;
    private Bitmap scaledBitmap;
    private int resultTime=0;


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
            resultTime+=1;

            timerHandler.postDelayed(this, 1000);
        }
    };
    // END OF TIMER SETTINGS




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.moca_fragment_moca1, container, false);


        imageVisor = (ImageView) rootView.findViewById(R.id.moca1view);
        timerTextView = (TextView) rootView.findViewById(R.id.timerTextView1);
        startBtn = (ImageButton) rootView.findViewById(R.id.moca1Play);
        refreshBtn = (ImageButton) rootView.findViewById(R.id.moca1Refresh);
        nextButton = (ImageButton) rootView.findViewById(R.id.moca1Next);


        //User class







        //Bitmap ALG
        Bitmap originalImage = BitmapFactory.decodeResource(getResources(), R.drawable.custom_mocatest);
        mutableBitmap = originalImage.copy(Bitmap.Config.ARGB_8888, true);

        DisplayMetrics metrics = rootView.getResources().getDisplayMetrics();
        float totalDIP_X = metrics.xdpi;
        float totalDIP_Y = metrics.ydpi;
        pixelSizeAreaeq[0] = (int)(realSizeArea[0]*totalDIP_X);
        pixelSizeAreaeq[1] = (int)(realSizeArea[1]*totalDIP_Y);

        if(metrics.widthPixels < pixelSizeAreaeq[0]){
            pixelSizeAreaeq[0] = metrics.widthPixels-20;

            //Nuevas pulgadas de la imágen
            double newinchesL = pixelSizeAreaeq[0]/totalDIP_X;
            double newinchesA = 2.56*newinchesL/2.76;

            pixelSizeAreaeq[1] = (int)(newinchesA*totalDIP_Y);
        }

        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Visuoespacial/Ejecutiva 1");

    // metrics.widthPixels
        moca1refresh();

        imageVisor.setOnTouchListener(this);

        refreshBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                moca1refresh();
            }

        });

        // In code listener to avoid new functions
        startBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);

                    imageVisor.setEnabled(true);
                    refreshBtn.setEnabled(true);
                    nextButton.setEnabled(true);

                    startBtn.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.darker_gray));
                    refreshBtn.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.holo_green_dark));
                    nextButton.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.holo_green_dark));
                    startBtn.setEnabled(false);
                }

        });

        imageVisor.setEnabled(false);
        refreshBtn.setEnabled(false);
        nextButton.setEnabled(false);

        return rootView;
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
                imageVisor.invalidate();

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

    public void moca1refresh(){
        scaledBitmap = Bitmap.createScaledBitmap (mutableBitmap, pixelSizeAreaeq[0], pixelSizeAreaeq[1], false);
        canvas = new Canvas(scaledBitmap);

        //Painter
        paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.STROKE);

        //Creo el contorno del área de dibujo
        canvas.drawRect(0, 0, pixelSizeAreaeq[0], pixelSizeAreaeq[1], paint);

        //Cambio de paleta para dibujar
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(8);

        //scaledBitmap = moca1RefreshClick(rootView);

        //Visor
        imageVisor.setImageBitmap(scaledBitmap);
    }
    public void stopTimer(){
        timerHandler.removeCallbacks(timerRunnable);
    }

    public void setMocaUser(MocaUser mocaUser){
        this.mocaUser = mocaUser;
    }

    public Bitmap getResultBitmap(){
        return scaledBitmap;
    }
    public int getResultTime(){
        return resultTime;
    }

}
