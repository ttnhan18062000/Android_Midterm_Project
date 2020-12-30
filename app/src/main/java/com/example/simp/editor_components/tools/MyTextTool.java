package com.example.simp.editor_components.tools;

import android.view.MotionEvent;

import com.example.simp.editor_components.MyPaintView;
import com.example.simp.editor_components.shape.MyText;

public class MyTextTool extends MyTool {
    MyText myText;
    public MyTextTool(String name, ToolType toolID) {
        super(name, toolID);
    }

    public MyTextTool(String name, ToolType toolID, String describtion) {
        super(name, toolID, describtion);
    }

    @Override
    public void OnActionDown(MotionEvent event) {
        mListener.setBDraw(true);
        myText = new MyText();
        myText.startDraw(event);
        mListener.addShape(myText);
    }

    @Override
    public void OnActionMove(MotionEvent event) {
        super.OnActionMove(event);
        myText.process(event);
    }

    @Override
    public void OnActionUp(MotionEvent event) {
        super.OnActionUp(event);
        mListener.onEndDraw(getToolID());
    }

    public MyTextTool() {

    }

    public MyTextTool(MyPaintView myPaintView) {
        super(myPaintView);
    }
}
