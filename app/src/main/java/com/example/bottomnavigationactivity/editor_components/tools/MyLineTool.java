package com.example.bottomnavigationactivity.editor_components.tools;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

import com.example.bottomnavigationactivity.editor_components.MyPaintView;
import com.example.bottomnavigationactivity.editor_components.shape.MyLine;
import com.example.bottomnavigationactivity.utility.MyMath;

public class MyLineTool extends MyTool {
    MyLine myLine;

    public MyLineTool(String name, ToolType toolID) {
        super(name, toolID);
    }

    public MyLineTool(String name, ToolType toolID, String describtion) {
        super(name,toolID,describtion);
    }
    public MyLineTool(MyPaintView myPaintView) {
        super(myPaintView);
    }

    @Override
    public void OnActionDown(MotionEvent event) {
        super.OnActionDown(event);
        myLine = new MyLine();
        myLine.startDraw(event);
        mListener.addShape(myLine);
    }

    @Override
    public void OnActionMove(MotionEvent event) {
        myLine.process(event);
    }

    @Override
    public void OnActionUp(MotionEvent event) {
        mListener.setBDraw(false);
        mListener.onEndDraw(toolID);
        float length = MyMath.GetLength(myLine.P1, myLine.P2);
        float ratio = mListener.getRatio();
        Log.d(MyPaintView.TAG, "endDraw: pixel length: " + String.valueOf(length));
        Log.d(MyPaintView.TAG, "endDraw: estimate length: " + String.valueOf(length*ratio));
        myLine.setMyText(String.valueOf(ratio*length));
    }
}
