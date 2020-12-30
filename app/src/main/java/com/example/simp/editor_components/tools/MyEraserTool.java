package com.example.simp.editor_components.tools;

import android.view.MotionEvent;

import com.example.simp.editor_components.MyPaintView;
import com.example.simp.editor_components.shape.MyEraser;

public class MyEraserTool extends MyTool {

    MyEraser myEraser;
    public MyEraserTool(String name, ToolType toolID) {
        super(name, toolID);
    }

    public MyEraserTool(String name, ToolType toolID, String describtion) {
        super(name, toolID, describtion);
    }

    public MyEraserTool() {

    }

    public MyEraserTool(MyPaintView myPaintView) {
        super(myPaintView);
    }

    @Override
    public void OnActionDown(MotionEvent event) {
        super.OnActionDown(event);
        myEraser = new MyEraser(mContext);
        myEraser.startDraw(event);
        mListener.addShape(myEraser);
    }

    @Override
    public void OnActionMove(MotionEvent event) {
        super.OnActionMove(event);
        myEraser.process(event);
    }

    @Override
    public void OnActionUp(MotionEvent event) {
        super.OnActionUp(event);
        mListener.removeIfIntersect(myEraser.P1,myEraser.P2);
    }
}
