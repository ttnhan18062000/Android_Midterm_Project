package com.example.bottomnavigationactivity.editor_components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.bottomnavigationactivity.R;
import com.example.bottomnavigationactivity.utility.MyMath;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.getSystemService;


public class MyPaintView extends View {
    private final String TAG = "MyPaintView";
    private final Context mActivity;
    private float ratio;

    public interface OnEndDrawListener {
        public void onEndDraw();
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
        Shape curShape = shapes.get(shapes.size()-1);
        float pixelLength = MyMath.GetLength(curShape.P1, curShape.P2);
        ratio = lineLength/pixelLength;
        Log.d(TAG, "setRatio: " + String.valueOf(ratio));
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
        if(iTool == MyTool.ToolType.LINE)
        {
            Shape curShape = shapes.get(shapes.size()-1);
            float length = MyMath.GetLength(curShape.P1, curShape.P2);
            Log.d(TAG, "endDraw: pixel length: " + String.valueOf(length));
            Log.d(TAG, "endDraw: estimate length: " + String.valueOf(length*ratio));
        }
        else if(iTool == MyTool.ToolType.RATIO)
        {
            Shape curShape = shapes.get(shapes.size()-1);
            float length = MyMath.GetLength(curShape.P1, curShape.P2);
            mListener.onEndDraw();
        }
        createBitmapOfCurrentShapes();
    }

    private void processDraw(float x, float y) {
        Shape curShape = shapes.get(shapes.size()-1);
        curShape.process((int)x,(int)y);
        invalidate();
    }
    private MyTool.ToolType iTool = MyTool.ToolType.LINE;

    public void setTool(MyTool.ToolType toolID){
        iTool = toolID;
    }

    private void beginDraw(float x, float y) {
        bDraw = true;

        createBitmapOfCurrentShapes();

        Shape newShape = null;
        switch (iTool) {
            case RATIO:
            case LINE:
                newShape = new MyLine();
                break;
            case TEXT:
                //newShape = new MyEllipse();
                break;
            case ZOOM:
                // newShape = new MyPath();
                break;
            case ERASER:
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

}
