package com.chameleonsoftware.moca_fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.chameleonsoftware.neurotester.R;

/**
 * Created by camaleon2 on 26/11/15.
 */
public class Moca1Fragment extends Fragment implements View.OnTouchListener{

    private Chronometer mChrono;

    //Botones
    private ImageButton mStartBtn;
    private ImageButton mRefreshBtn;
    private ImageButton mNextBtn;

    //Dibujo
    private int[][] mTouchPos = new int[2][2];
    private ImageView mImageVisor;
    private Canvas mCanvas;
    private Paint mPaint;

    //Dimensiones en pulgadas de la imágen estándar (Validación)
    private double[] realSizeArea = {2.76,2.56};//Pulgadas
    private int[] pixelSizeAreaeq = new int[2];

    //Temporal and drawing Bitmaps
    private Bitmap mDrawingBitmap;
    private Bitmap mutableBitmap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.moca_fragment1, container, false);
        //noinspection ConstantConditions
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Visuoespacial/Ejecutiva 1");

        //Widget get
        mImageVisor = (ImageView) rootView.findViewById(R.id.moca1View);
        mStartBtn = (ImageButton) rootView.findViewById(R.id.moca1Play);
        mRefreshBtn = (ImageButton) rootView.findViewById(R.id.moca1Refresh);
        mNextBtn = (ImageButton) rootView.findViewById(R.id.moca1Next);
        mChrono = (Chronometer) rootView.findViewById(R.id.moca1Chrono);

        //Bitmap setup
        mCanvas = new Canvas();
        mPaint = new Paint();
        bmSetup();
        bmReset();

        mImageVisor.setOnTouchListener(this);
        mRefreshBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                bmReset();
            }

        });

        // In code listener to avoid new Classes
        mStartBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mChrono.setBase(SystemClock.elapsedRealtime());
                mChrono.start();

                mImageVisor.setEnabled(true);
                mRefreshBtn.setEnabled(true);
                mNextBtn.setEnabled(true);
                mStartBtn.setEnabled(false);

                mStartBtn.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.darker_gray));
                mRefreshBtn.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.holo_green_dark));
                mNextBtn.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.holo_green_dark));

            }

        });

        //Disable Buttons while Start
        mImageVisor.setEnabled(false);
        mRefreshBtn.setEnabled(false);
        mNextBtn.setEnabled(false);

        return rootView;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //P1 Coordinates
                mTouchPos[0][0] = (int)event.getX();
                mTouchPos[1][0] = (int)event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                //P2 Coordinates
                mTouchPos[0][1] = (int)event.getX();
                mTouchPos[1][1] = (int)event.getY();

                //Line Drawing
                mCanvas.drawLine(mTouchPos[0][0], mTouchPos[1][0], mTouchPos[0][1], mTouchPos[1][1], mPaint);
                //Force Redrawing
                mImageVisor.invalidate();

                //P1 = P2
                mTouchPos[0][0] = mTouchPos[0][1];
                mTouchPos[1][0] = mTouchPos[1][1];

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

    //Bitmap Initial Setup and Metrics set
    private void bmSetup(){
        Bitmap originalImage = BitmapFactory.decodeResource(getResources(), R.drawable.custom_mocatest);
        mutableBitmap = originalImage.copy(Bitmap.Config.ARGB_8888, true);

        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        float totalDIP_X = metrics.xdpi;
        float totalDIP_Y = metrics.ydpi;
        pixelSizeAreaeq[0] = (int)(realSizeArea[0]*totalDIP_X);
        pixelSizeAreaeq[1] = (int)(realSizeArea[1]*totalDIP_Y);

        if(metrics.widthPixels < pixelSizeAreaeq[0]){
            pixelSizeAreaeq[0] = metrics.widthPixels-20;

            //Nuevas pulgadas de la imágen
            double newinchesL = pixelSizeAreaeq[0]/totalDIP_X;
            double newinchesA = realSizeArea[1]*newinchesL/realSizeArea[0];

            pixelSizeAreaeq[1] = (int)(newinchesA*totalDIP_Y);
        }
    }

    public void bmReset(){
        mDrawingBitmap = Bitmap.createScaledBitmap (mutableBitmap, pixelSizeAreaeq[0], pixelSizeAreaeq[1], false);
        mCanvas.setBitmap(mDrawingBitmap);

        //Painter
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(15);
        mPaint.setStyle(Paint.Style.STROKE);

        //Creo el contorno del área de dibujo
        mCanvas.drawRect(0, 0, pixelSizeAreaeq[0], pixelSizeAreaeq[1], mPaint);

        //Cambio de paleta para dibujar
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);

        //Visor
        mImageVisor.setImageBitmap(mDrawingBitmap);
    }
    public void stopTimer(){
        mChrono.stop();
    }

    public Bitmap getResultBitmap(){
        return mDrawingBitmap;
    }
    public int getResultTime(){
        long elapsedMillis = SystemClock.elapsedRealtime() - mChrono.getBase();
        return (int)elapsedMillis/1000;
    }

}
