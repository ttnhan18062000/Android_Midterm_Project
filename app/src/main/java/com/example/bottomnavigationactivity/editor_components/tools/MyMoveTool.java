package com.example.bottomnavigationactivity.editor_components.tools;

import android.view.MotionEvent;

import com.example.bottomnavigationactivity.editor_components.shape.MyBitmap;

public class MyMoveTool extends MyTool {
    MyBitmap myBitmap = null;
    public MyMoveTool(String m, ToolType move) {

    }

    @Override
    public void OnActionDown(MotionEvent event) {
        super.OnActionDown(event);
        myBitmap = mListener.getIntersecBitmap(event);
        if(myBitmap != null)
            myBitmap.startDraw(event);
    }
    @Override
    public void OnActionPointerDown(MotionEvent event) {
        super.OnActionPointerDown(event);
        if(myBitmap!=null)
            myBitmap.startDraw(event);
    }

    @Override
    public void OnActionMove(MotionEvent event) {
        super.OnActionMove(event);
        if(myBitmap != null)
            myBitmap.process(event);
    }

    @Override
    public void OnActionUp(MotionEvent event) {
        super.OnActionUp(event);
        if(myBitmap != null)
            myBitmap.setMode(MyBitmap.NONE);
    }

    @Override
    public void OnActionPointerUp(MotionEvent event) {
        if(myBitmap != null)
            myBitmap.setMode(MyBitmap.DRAG);
    }

    public MyMoveTool(String name, ToolType toolID, String describtion) {
        super(name, toolID, describtion);
    }
}
