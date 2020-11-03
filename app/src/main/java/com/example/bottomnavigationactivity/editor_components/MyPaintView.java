package com.example.bottomnavigationactivity.editor_components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class MyPaintView extends View {
    public MyPaintView(Context context) {
        super(context);
        GlobalSetting.paintView = this;
    }

    public MyPaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        GlobalSetting.paintView = this;
    }

    Canvas canvas;

    public ArrayList<Shape> shapes = new ArrayList<>();

    @Override
    protected void onDraw(Canvas canvas) {
        if (backgroundWithShapes!=null)
        {
            canvas.drawBitmap(backgroundWithShapes, 0, 0, null);
            if (bDraw)
                shapes.get(shapes.size()-1).draw(canvas);
        }
        else {
            canvas.drawARGB(0, 255, 255, 255);
            for (int i = 0; i < shapes.size(); i++)
                shapes.get(i).draw(canvas);
        }
//        super.onDraw(canvas);
    }

    public boolean isBDraw() {
        return bDraw;
    }

    private boolean bDraw = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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

                beginDraw(x, y);
                break;
            }
            case MotionEvent.ACTION_MOVE: { // a pointer was moved
                // TODO use data
                if (bDraw)
                    processDraw(x, y);
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP: {
                if (bDraw)
                    endDraw(x, y);
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                // TODO use data
                break;
            }
        }
        return true;

    }

    private void endDraw(float x, float y) {
        bDraw = false;
        processDraw(x,y);
        createBitmapOfCurrentShapes();
    }

    private void processDraw(float x, float y) {
        Shape curShape = shapes.get(shapes.size()-1);
        curShape.process((int)x,(int)y);
        invalidate();
    }

    private int iTool = 1;

    private void beginDraw(float x, float y) {
        bDraw = true;

        createBitmapOfCurrentShapes();

        Shape newShape = null;
        switch (iTool) {
            case 0:
                newShape = new MyLine();
                break;
            case 1:
                //newShape = new MyRectangle();
                break;
            case 2:
                //newShape = new MyEllipse();
                break;
            case 3:
                // newShape = new MyPath();
                break;
            case 4:
                newShape = new MyEraser();
                break;
        }
        newShape.P1 = new Point((int) x, (int) y);
        newShape.P2 = new Point((int) x, (int) y);
        newShape.penColor = GlobalSetting.SelectedColor;
        shapes.add(newShape);
        invalidate();
    }

     Bitmap backgroundWithShapes;

    private void createBitmapOfCurrentShapes() {
        backgroundWithShapes = Bitmap.createBitmap(getCurrentScreenWidth(), getCurrentScreenHeight(), Bitmap.Config.ARGB_8888);

        canvas = new Canvas(backgroundWithShapes);
        canvas.drawARGB(0, 255, 255, 255);
        for (int i=0; i<shapes.size(); i++)
            shapes.get(i).draw(canvas);
    }



    public void clear()
    {
        backgroundWithShapes = null;
        shapes.clear();
        createBitmapOfCurrentShapes();
        invalidate();
    }


    private int getCurrentScreenHeight() { return this.getMeasuredHeight(); }
    private int getCurrentScreenWidth() { return this.getMeasuredWidth();}

    public void selectShape(int shapeID) {
        iTool = shapeID;
    }

}
