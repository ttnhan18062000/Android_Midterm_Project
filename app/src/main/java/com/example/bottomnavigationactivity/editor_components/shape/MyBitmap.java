package com.example.bottomnavigationactivity.editor_components.shape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

public class MyBitmap extends MyShape {
    public Bitmap bitmap;
    public int top = 0;
    public int left = 0;
    public int right = 0;
    public int bottom = 0;
    Point start = new Point();
    Rect savedRect= new Rect();
    float oldDist;
    Point mid = new Point();

    public static final int NONE = 0;
    public static final int DRAG = 1;
    public static final int ZOOM = 2;
    int mode = NONE;
    public MyBitmap(int width, int height)
    {
        bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        right = width -1;
        bottom = height -1;
        type = "Bitmap";
    }

    public MyBitmap(Bitmap bitmap) {
        this.bitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);
        right = this.bitmap.getWidth()-1;
        bottom = this.bitmap.getHeight()-1;
        type = "Bitmap";
    }

    public void setBitmapRect(int left,int top,int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }


    @Override
    public boolean startDraw(MotionEvent event) {
        start.set((int)event.getX(),(int)event.getY());
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN)
        {
            mode = DRAG;
        }
        else if(event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN)
        {
            //action pointer down
            oldDist = spacing(event);
            if (oldDist > 5f)
            {
                savedRect.set(getRect());
                midPoint(mid, event);
                mode = ZOOM;
            }
        }
        return false;
    }

    public Rect getRect() {
        return new Rect(left,top,right,bottom);
    }

    @Override
    public void process(MotionEvent event) {
        int newX = (int)(event.getX() - start.x);
        int newY = (int)(event.getY() - start.y);
        if (mode == DRAG)
        {
            int left = this.left + newX;
            int top = this.top + newY;
            int right = this.right + newX;
            int bottom = this.bottom + newY;
            this.setBitmapRect(left,top,right,bottom);
            start.set((int)event.getX(),(int)event.getY());
        }
        else if (mode == ZOOM)
        {
            // pinch zooming
            float newDist = spacing(event);
            if (newDist > 5f)
            {
                float scale = newDist/oldDist;
                int left = (int)(mid.x + scale*(savedRect.left-mid.x));
                int top = (int)(mid.y + scale*(savedRect.top-mid.y));
                int right =  (int)(mid.x + scale*(savedRect.right-mid.x));
                int bottom = (int)(mid.y + scale*(savedRect.bottom-mid.y));
                this.setBitmapRect(left,top,right,bottom);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Rect source = new Rect(0,0,this.bitmap.getWidth()-1,this.bitmap.getHeight()-1);
        Rect target = new Rect(this.left,this.top,this.right,this.bottom);
        canvas.drawBitmap(this.bitmap,source,target,null);
    }

    private float spacing(MotionEvent event)
    {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void midPoint(Point point, MotionEvent event)
    {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set((int)(x / 2), (int)(y / 2));
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
