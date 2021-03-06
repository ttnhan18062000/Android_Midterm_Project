package com.example.simp.editor_components.tools;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;

import com.example.simp.editor_components.MyPaintView;
import com.example.simp.editor_components.shape.MyBitmap;
import com.example.simp.editor_components.shape.MyShape;

public class MyTool {

    Context mContext;
    protected ToolListener mListener;

    public void OnActionDown(MotionEvent event) {
        mListener.setBDraw(true);
    }

    public void OnActionPointerDown(MotionEvent event) {

    }

    public void OnActionMove(MotionEvent event) {
        if(!mListener.getBDraw())
            return;
    }

    public void OnActionUp(MotionEvent event) {
        if(!mListener.getBDraw())
            return;
        mListener.setBDraw(false);
    }

    public void OnActionPointerUp(MotionEvent event) {

    }

    public enum ToolType {LINE, ERASER, TEXT, ZOOM, RATIO, MOVE}

    ;
    protected String name;
    protected ToolType toolID;
    private int icon;
    private String description;

    public MyTool(String name, ToolType toolID) {
        this.name = name;
        this.toolID = toolID;
    }

     public MyTool(String name,
                   ToolType toolID, String description){
        this.name = name;
        this.toolID = toolID;
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public void setToolListener(MyPaintView toolListener)
    {
        mListener = toolListener;
        mContext = toolListener.getContext();
    }

    public MyTool()
    {

    }
    public MyTool(MyPaintView myPaintView)
    {
        mListener = (ToolListener)myPaintView;
    }


    public String getName() {
        return name;
    }

    public ToolType getToolID() {
        return toolID;
    }

    public interface ToolListener {
        boolean getBDraw();
        void setBDraw(boolean bDraw);
        void onEndDraw(MyTool.ToolType iTool);
        void addShape(MyShape myLine);
        void removeIfIntersect(Point P1, Point P2);
        float getRatio();
        MyBitmap getIntersecBitmap(MotionEvent event);
    }
}
