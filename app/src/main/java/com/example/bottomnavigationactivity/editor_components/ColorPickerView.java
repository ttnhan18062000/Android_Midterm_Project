package com.example.bottomnavigationactivity.editor_components;

import android.content.Context;
import android.content.pm.PackageItemInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.bottomnavigationactivity.R;

public class ColorPickerView extends View {

    private Bitmap bmp = null;
    ImageView ivColorPreview = null;
    public ColorPickerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorPickerView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (bmp == null)
        {
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.smooth_color_scale);
            Matrix matrix = new Matrix();
            float scaleY = (float)this.getMeasuredHeight()/(float)bmp.getHeight();
            float scaleX = (float)this.getMeasuredWidth()/(float)bmp.getWidth();
            matrix.postScale(scaleX,scaleY);
            bmp = Bitmap.createBitmap(bmp,0,0,bmp.getWidth(),bmp.getHeight(),matrix,false);
        }
        canvas.drawBitmap(bmp, 0, 0, null);
//        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        //return super.onTouchEvent(event);
        // get pointer index from the event object
        int pointerIndex = event.getActionIndex();

        // get pointer ID
        int pointerId = event.getPointerId(pointerIndex);

        // get masked (not specific to a pointer) action
        int maskedAction = event.getActionMasked();
        float x = event.getX();
        float y = event.getY();

        switch (maskedAction) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                // TODO use data
                getColorAtTouch(x, y);
                break;
            }
            case MotionEvent.ACTION_MOVE: { // a pointer was moved
                // TODO use data
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP: {
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                // TODO use data
                break;
            }
        }

        return true;

    }

    private void getColorAtTouch(float x, float y) {
        try {
            if(ivColorPreview == null) {
                ivColorPreview = findViewById(R.id.colorPickerPreview);
            }
            int selColor = bmp.getPixel((int)x, (int)y);
            GlobalSetting.SelectedColor = selColor;
            ivColorPreview.setBackgroundColor(selColor);
            ivColorPreview.postInvalidate();
        }
        catch (Exception e)
        {};
    }
}
