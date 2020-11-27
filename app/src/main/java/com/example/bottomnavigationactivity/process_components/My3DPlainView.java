package com.example.bottomnavigationactivity.process_components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class My3DPlainView extends View {
    private Context mActivity;
    private Paint paint;
    private Rect rect;
    private static String TAG = "My3DPlainView";
    public My3DPlainView(Context context) {
        super(context);
        mActivity = context;
    }

    public My3DPlainView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mActivity = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        paint = new Paint();
        paint.setARGB(0, 0, 255, 0);
        Paint.Style style = Paint.Style.FILL;
        paint.setStyle(style);
        paint.setStrokeWidth(15f);
        rect = new Rect(0, 0, getCurrentScreenWidth(), getCurrentScreenHeight());
        Log.d(TAG, "onDraw: " + String.valueOf(getCurrentScreenWidth()) + " " + String.valueOf(getCurrentScreenHeight()));
        canvas.drawRect(rect, paint);
    }
    private int getCurrentScreenHeight() { return this.getMeasuredHeight(); }
    private int getCurrentScreenWidth() { return this.getMeasuredWidth();}
}
