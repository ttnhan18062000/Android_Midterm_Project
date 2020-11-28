package com.example.bottomnavigationactivity.editor_components.tools;

import android.content.Context;
import android.view.MotionEvent;

import com.example.bottomnavigationactivity.editor_components.MyPaintView;
import com.example.bottomnavigationactivity.editor_components.shape.MyLine;

public class MyLineTool extends MyTool {
    MyLine myLine;

    public MyLineTool(String name, ToolType toolID) {
        super(name, toolID);
    }

    public MyLineTool() {

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
    }
}
