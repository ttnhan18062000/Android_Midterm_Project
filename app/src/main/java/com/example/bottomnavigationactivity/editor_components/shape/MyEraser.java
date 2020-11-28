package com.example.bottomnavigationactivity.editor_components.shape;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

import com.example.bottomnavigationactivity.R;
import com.example.bottomnavigationactivity.editor_components.MyPaintView;
import com.example.bottomnavigationactivity.utility.MyMath;


public class MyEraser extends LinearShape {

    Context mContext;
    private Matrix mDrawMatrix = new Matrix();
    static Bitmap mBitmap;
    DisplayMetrics dm;

    public MyEraser(Context mContext) {
        this.mContext = mContext;
        if(mBitmap == null)
            mBitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.eraser_bitmap);
        dm = mContext.getResources().getDisplayMetrics();
    }

    @Override
    public void draw(Canvas canvas) {
        style = Paint.Style.STROKE;
        super.draw(canvas);
        Paint paint = new Paint();
//        paint.setARGB(255, 255, 0, 0);
        paint.setColor(penColor) ;
        paint.setStrokeWidth(15);
        paint.setStyle(style);
        long start = System.currentTimeMillis();
        createDrawMatrix();
        long end = System.currentTimeMillis();
        long createMatrixTime = end - start;
        start = end;
        canvas.drawBitmap(mBitmap,mDrawMatrix, paint);
        end = System.currentTimeMillis();
        long drawTime = end - start;
        Log.d("Timing","Create Matrix: " + String.valueOf(createMatrixTime) + ". Draw: " + String.valueOf(drawTime));
    }

    private void createDrawMatrix() {
        mDrawMatrix=new Matrix();
        float degree;

        mDrawMatrix.postScale( MyMath.GetLength(P1,P2)/mBitmap.getWidth(),dm.density*5f/mBitmap.getHeight());
        mDrawMatrix.postTranslate(P1.x,P1.y);
        if(P1.x-P2.x==0)
            if(P2.y>P1.y)
                degree = 270;
            else
                degree=90;
        else
            degree = (float)Math.toDegrees(Math.atan2(P2.y-P1.y,P2.x-P1.x));

        mDrawMatrix.postRotate(degree,P1.x,P1.y);
    }

    @Override
    public void process(MotionEvent event) {
        super.process(event);
    }
}
