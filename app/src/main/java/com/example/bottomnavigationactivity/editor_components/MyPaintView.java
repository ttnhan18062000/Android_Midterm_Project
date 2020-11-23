package com.example.bottomnavigationactivity.editor_components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
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
    public boolean isBDraw() {
        return bDraw;
    }
    private boolean bDraw = false;
    private boolean bMove;
    private OnEndDrawListener mListener;
    Canvas canvas;
    public ArrayList<Shape> shapes = new ArrayList<>();
    private  Bitmap bitmap = null;
    private Bitmap savedBitmap = null;
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    private int mode = NONE;
    PointF start = new PointF();
    Rect savedRect = new Rect();
    PointF mid = new PointF();
    Float oldDist;
    int selectedBitmapIndex = -1;
    private MyTool.ToolType iTool = MyTool.ToolType.LINE;
    public void setTool(MyTool.ToolType toolID){
        iTool = toolID;
    }
    Bitmap backgroundWithShapes;
    ArrayList<MyBitmap> bitmaps = new ArrayList<>();
    Bitmap pickedBitmap = null;

    public interface OnEndDrawListener {
        public void onEndDraw();
    }


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


    @Override
    protected void onDraw(Canvas canvas) {
        if (backgroundWithShapes != null)
        {
//            for (MyBitmap bitmap : backgroundWithShapes)
//            {
//                canvas.drawBitmap(bitmap.bitmap,bitmap.left,bitmap.top,null);
//            }

            canvas.drawBitmap(backgroundWithShapes, 0,0, null);

            //canvas.drawBitmap(showmaker,0,0,null);
            if (bDraw)
                shapes.get(shapes.size()-1).draw(canvas);
        }
        else {
            drawShapesOnCanvas(canvas);
        }
//        super.onDraw(canvas);
    }

    private void drawShapesOnCanvas(Canvas canvas) {
        for (int i = 0; i < shapes.size(); i++)
            shapes.get(i).draw(canvas);
    }

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
                {
                    if(bMove)
                    {
                        selectedBitmapIndex = getIntersecBitmap(event);
                        if(selectedBitmapIndex == -1)
                            break;
                        start.set(event.getX(),event.getY());
                        mode = DRAG;
                    }
                    else beginDraw(x,y);
                    break;
                }
            case MotionEvent.ACTION_POINTER_DOWN:
                {
                    // TODO use data
                    if(bMove) {
                        oldDist = spacing(event);
                        if (oldDist > 5f && selectedBitmapIndex != -1)
                        {
                            savedRect.set(bitmaps.get(selectedBitmapIndex).getRect());
                            midPoint(mid, event);
                            mode = ZOOM;
                        }
                    }
                    else {
                        beginDraw(x, y);
                    }
                    break;
                }
            case MotionEvent.ACTION_MOVE:
                {// TODO use data
                    if(bMove)
                    {
                        int newX = (int)(event.getX() - start.x);
                        int newY = (int)(event.getY() - start.y);
                        if(selectedBitmapIndex == -1)
                            break;
                        MyBitmap mbm = bitmaps.get(selectedBitmapIndex);
                        if (mode == DRAG)
                        {
                            int left = mbm.left + newX;
                            int top = mbm.top + newY;
                            int right = mbm.right + newX;
                            int bottom = mbm.bottom + newY;
                            bitmaps.get(selectedBitmapIndex).setBitmapRect(left,top,right,bottom);
                            start.set(event.getX(),event.getY());
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
                                bitmaps.get(selectedBitmapIndex).setBitmapRect(left,top,right,bottom);
                            }
                        }
                        createBitmapOfCurrentShapes();
                        invalidate();
                    }
                    else if (bDraw)
                        processDraw(x, y);
                    break;
                }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                {
                    mode = NONE;
                    if (bDraw)
                        endDraw(x, y);
                    break;
                }
            case MotionEvent.ACTION_CANCEL: {
                // TODO use data
                break;
            }
        }
        createBitmapOfCurrentShapes();
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

    private void beginDraw(float x, float y) {
        bDraw = true;
        bMove = false;
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
            case MOVE:
                bMove = true;
                break;
        }
        newShape.P1 = new Point((int) x, (int) y);
        newShape.P2 = new Point((int) x, (int) y);
        newShape.penColor = GlobalSetting.SelectedColor;
        shapes.add(newShape);
        invalidate();
    }

    private void createBitmapOfCurrentShapes() {
        backgroundWithShapes = Bitmap.createBitmap(getCurrentScreenWidth(), getCurrentScreenHeight(), Bitmap.Config.ARGB_8888);

        canvas = new Canvas(backgroundWithShapes);
        if(bitmaps.size() != 0 )
            for(MyBitmap mb : bitmaps)
            {
                Rect source = new Rect(0,0,mb.bitmap.getWidth()-1,mb.bitmap.getHeight()-1);
                Rect target = new Rect(mb.left,mb.top,mb.right,mb.bottom);
                canvas.drawBitmap(mb.bitmap,source,target,null);
            }

        //canvas.drawBitmap(showmaker,0,0,null);
        //canvas.drawARGB(0, 255, 255, 255);
        drawShapesOnCanvas(canvas);
    }

    public void addBackgroundWithShapes(Bitmap bitmap){
        bitmaps.add(new MyBitmap(bitmap));
        createBitmapOfCurrentShapes();
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

    private float spacing(MotionEvent event)
    {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event)
    {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    private int getIntersecBitmap(MotionEvent event)
    {
        int x = (int) event.getX();
        int y = (int)event.getY();
        for(int i = bitmaps.size() -1 ; i>=0;i--)
        {
            if(intersec(bitmaps.get(i).getRect(),x,y))
                return i;
        }
        return -1;
    }

    private boolean intersec(Rect rect, int x, int y) {
        if(x<=rect.right && x >=rect.left && y >= rect.top && y<= rect.bottom)
            return true;
        return false;
    }

    public void setMoveMode(boolean b) {
        bMove = b;
    }
}
