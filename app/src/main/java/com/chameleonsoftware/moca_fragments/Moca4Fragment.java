package com.chameleonsoftware.moca_fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chameleonsoftware.neurotester.R;

/**
 * Created by camaleon2 on 26/11/15.
 */




public class Moca4Fragment extends Fragment implements View.OnTouchListener {

    ImageView visor;
    Bitmap bm;
    Canvas canvas;
    Paint paint;

    private int[] posA = new int[2];
    private int[] posB = new int[2];


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_moca3,container,false);

        visor = (ImageView) rootView.findViewById(R.id.moca3view);
        bm = Bitmap.createBitmap(800,600, Bitmap.Config.ARGB_8888);

        canvas = new Canvas(bm);
        paint = new Paint();

        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(0, 0, 800, 600, paint);

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(8);



        visor.setImageBitmap(bm);
        visor.setOnTouchListener(this);

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
}
