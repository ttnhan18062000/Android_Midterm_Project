package com.example.bottomnavigationactivity.editor_components;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class MyBitmap {
    public Bitmap bitmap;
    public int top = 0;
    public int left = 0;
    public int right = 0;
    public int bottom = 0;
    public MyBitmap(int width, int height)
    {
        bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
    }

    public void setBitmapRect(int left,int top,int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public MyBitmap(Bitmap bitmap) {
        this.bitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);
        right = this.bitmap.getWidth()-1;
        bottom = this.bitmap.getHeight()-1;
    }


    public Rect getRect() {
        return new Rect(left,top,right,bottom);
    }
}
