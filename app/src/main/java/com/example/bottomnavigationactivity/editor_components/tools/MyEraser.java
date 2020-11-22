package com.example.bottomnavigationactivity.editor_components.tools;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.example.bottomnavigationactivity.editor_components.tools.GlobalSetting;
import com.example.bottomnavigationactivity.editor_components.tools.MyLine;
import com.example.bottomnavigationactivity.editor_components.tools.Shape;

public class MyEraser extends MyLine {
    public MyEraser(MyPaintView myPaintView) {
        myEraserListener = (OnMyEraserListener)myPaintView;
    }

    @Override
    public void draw(Canvas canvas) {
        style = Paint.Style.STROKE;
        super.draw(canvas);
    }

    public interface OnMyEraserListener{
        public void removeIfIntersect(Point P1, Point P2);
    }

    OnMyEraserListener myEraserListener;

    @Override
    public void process(int x, int y) {
        super.process(x, y);
        myEraserListener.removeIfIntersect(P1,P2);
    }
}
