package com.example.simp.editor_components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;


import com.example.simp.editor_components.shape.MyBitmap;
import com.example.simp.editor_components.shape.MyShape;
import com.example.simp.editor_components.shape.MyLine;
import com.example.simp.editor_components.shape.MyText;
import com.example.simp.editor_components.tools.MyTool;
import com.example.simp.utility.MyMath;

import java.util.ArrayList;



public class MyPaintView extends View  implements MyTool.ToolListener{
  
    private Bitmap srcBitmap;
    private boolean notDrawTool;

    public static final String TAG = "MyPaintView";
    private final Context mActivity;
    private float ratio;
    private MyTool currentTool;

    @Override
    public boolean getBDraw() {
        return bDraw;
    }

    @Override
    public void setBDraw(boolean bDraw) {
        this.bDraw = bDraw;
    }

    @Override
    public void onEndDraw(MyTool.ToolType iTool) {
        mListener.onEndDraw(iTool);
    }

    @Override
    public void addShape(MyShape myLine) {
        drawables.add(myLine);
        currentDrawableIndex = drawables.size() - 1;
    }



    public interface OnEndDrawListener {
        public void onEndDraw(MyTool.ToolType iTool);
    }
    private OnEndDrawListener mListener;

    public void setOnEndDrawListener(OnEndDrawListener listener) {
            mListener = listener;
    }
  

    public MyPaintView(Context context) {
        super(context);
        mActivity = context;
        GlobalSetting.paintView = this;
    }
    public MyPaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mActivity = context;
        GlobalSetting.paintView = this;
    }

    public void setRatio(float lineLength)
    {
        if(currentDrawableIndex != -1)
        {
            MyLine curShape = (MyLine) drawables.get(currentDrawableIndex);
            float pixelLength = MyMath.GetLength(curShape.P1, curShape.P2);
            ratio = lineLength/pixelLength;
            Log.d(TAG, "setRatio: " + String.valueOf(ratio));
        }
    }

    public void setText(String text) {
        if (currentDrawableIndex != -1)
        {
            MyText curShape = (MyText) drawables.get(currentDrawableIndex);
            curShape.setMyText(text);
            createBitmapOfCurrentShapes();
            invalidate();
        }
    }

    Canvas canvas;

    public ArrayList<MyShape> drawables = new ArrayList<>();

    @Override
    protected void onDraw(Canvas canvas) {
        if (backgroundWithShapes != null)
        {
//            for (MyBitmap bitmap : backgroundWithShapes)
//            {
//                canvas.drawBitmap(bitmap.bitmap,bitmap.left,bitmap.top,null);
//            }
            canvas.drawBitmap(backgroundWithShapes, 0,0, null);
            if (bDraw)
                if(currentDrawableIndex != -1)
                    drawables.get(currentDrawableIndex).draw(canvas);
        }
        else {
            drawDrawablesOnCanvas(canvas);
        }
//        super.onDraw(canvas);
    }

    private void drawDrawablesOnCanvas(Canvas canvas) {
        for (int i = 0; i < drawables.size(); i++)
            drawables.get(i).draw(canvas);
    }

    private boolean bDraw = false;
    int currentDrawableIndex = -1;


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
                currentTool.OnActionDown(event);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                currentTool.OnActionPointerDown(event);
                break;
            case MotionEvent.ACTION_MOVE: { // a pointer was moved
                // TODO use data
                currentTool.OnActionMove(event);
                break;
            }
            case MotionEvent.ACTION_UP:
                currentTool.OnActionUp(event);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                currentTool.OnActionPointerUp(event);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;

        }
        createBitmapOfCurrentShapes();
        invalidate();
        return true;
    }

    public void setTool(MyTool selectedTool){
        currentTool = selectedTool;
        currentTool.setToolListener(this);
    }

    private void endDraw(MotionEvent event) {
        bDraw = false;
        processDraw(event);
        if(iTool == MyTool.ToolType.LINE)
        {
            MyLine curShape = (MyLine) drawables.get(currentDrawableIndex);
        }
        else if(iTool == MyTool.ToolType.RATIO)
        {
            MyLine curShape = (MyLine) drawables.get(currentDrawableIndex);
            float length = MyMath.GetLength(curShape.P1, curShape.P2);
        }
        mListener.onEndDraw(iTool);
        createBitmapOfCurrentShapes();
        invalidate();
    }

    private void processDraw(MotionEvent event) {
        if(currentDrawableIndex != -1)
        {
            MyShape curShape = drawables.get(currentDrawableIndex);
            curShape.process(event);
            invalidate();
        }
    }

    private MyTool.ToolType iTool = MyTool.ToolType.LINE;
    Bitmap backgroundWithShapes;

    private void createBitmapOfCurrentShapes() {
        try{
            backgroundWithShapes = Bitmap.createBitmap(getCurrentScreenWidth(), getCurrentScreenHeight(), Bitmap.Config.ARGB_8888);
        }
        catch (Exception e)
        {

        }

        canvas = new Canvas(backgroundWithShapes);
        //canvas.drawBitmap(showmaker,0,0,null);
        //canvas.drawARGB(0, 255, 255, 255);
        drawDrawablesOnCanvas(canvas);
    }

    public void addBackgroundWithShapes(Bitmap bitmap){
        MyBitmap myBitmap = new MyBitmap(bitmap);
        drawables.add(new MyBitmap(bitmap));
        try{
            createBitmapOfCurrentShapes();
        }
        catch(Exception e)
        {

        }
    }

    public void clear()
    {
        backgroundWithShapes = null;
        drawables.clear();
        createBitmapOfCurrentShapes();
        invalidate();
    }

    private int getCurrentScreenHeight() { return this.getMeasuredHeight();}
    private int getCurrentScreenWidth() { return this.getMeasuredWidth();}


    private boolean intersec(Rect rect, int x, int y) {
        if(x<=rect.right && x >=rect.left && y >= rect.top && y<= rect.bottom)
            return true;
        return false;
    }

    @Override
    public void removeIfIntersect(Point P1, Point P2) {
        if(bDraw == false)
        {
            int i;
            for(i =0;i< drawables.size()-1;)
            {
                MyShape shape = drawables.get(i);
                if(GlobalSetting.doIntersect(P1,P2,shape))
                {
                    drawables.remove(i);
                }
                else {i++;}
            }
            drawables.remove(i);
            invalidate();
        }
    }

    @Override
    public float getRatio() {
        return ratio;
    }

    @Override
    public MyBitmap getIntersecBitmap(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int)event.getY();
        for(int i = drawables.size() -1 ; i>=0;i--)
        {
            if(drawables.get(i) instanceof MyBitmap&&intersec(((MyBitmap)drawables.get(i)).getRect(),x,y))
                return (MyBitmap)drawables.get(i);
        }
        return null;
    }


}


