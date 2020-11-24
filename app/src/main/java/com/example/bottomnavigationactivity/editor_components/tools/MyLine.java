package com.example.bottomnavigationactivity.editor_components.tools;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.example.bottomnavigationactivity.editor_components.tools.Shape;

public class MyLine extends Shape {
    Paint.Style style = Paint.Style.FILL;
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
//        paint.setARGB(255, 255, 0, 0);
        paint.setColor(penColor) ;
        paint.setStrokeWidth(15);
        paint.setStyle(style);
        canvas.drawLine(P1.x, P1.y, P2.x, P2.y, paint);
        //super.draw(canvas);
    }

    public MyLine()
    {
        super();
        type = "Line";
    }

    @Override
    public void process(int x, int y) {
        P2 = new Point(x,y);
    }
}
