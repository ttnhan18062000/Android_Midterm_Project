package com.example.bottomnavigationactivity.editor_components.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import com.example.bottomnavigationactivity.editor_components.GlobalSetting;

public class LinearShape extends MyShape {
    public Point P1, P2;
    protected Paint.Style style = Paint.Style.FILL;
    public int penColor = (255 << 24) | (255 <<16),    brushColor;
    static public int encodeColor(int a, int r, int g, int b)
    {                                                                                                          
        int c;
        c = (a << 24) | (r << 16) | (g << 8) | b;
        return c;
    }


    @Override
    public void process(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        P2 = new Point(x,y);
    }

    public LinearShape()
    {}

    @Override
    public boolean startDraw(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        P1 = new Point((int) x, (int) y);
        P2 = new Point((int) x, (int) y);
        penColor = GlobalSetting.SelectedColor;
        return true;
    }

    @Override
    public void draw(Canvas canvas) {

    }

    public void setMyText(String s) {
    }
}
