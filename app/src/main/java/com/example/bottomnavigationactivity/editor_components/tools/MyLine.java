package com.example.bottomnavigationactivity.editor_components.tools;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.example.bottomnavigationactivity.editor_components.tools.Shape;

public class MyLine extends Shape {
    protected Paint.Style style = Paint.Style.FILL;
    String mText = null;
    Path mPath;
    Paint textDrawPaint;
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
//        paint.setARGB(255, 255, 0, 0);
        paint.setColor(penColor) ;
        paint.setStrokeWidth(15);
        paint.setStyle(style);
        canvas.drawLine(P1.x, P1.y, P2.x, P2.y, paint);
        if (mText != null)
            drawText(canvas);
        //super.draw(canvas);
    }

    private void drawText(Canvas canvas) {
        textDrawPaint = new Paint();
        textDrawPaint.setTextSize(40);
        mPath = new Path();
        Point start, end;
        if(P1.x < P2.x)
        {
            start = P1;
            end = P2;
        }
        else
        {
            start = P2;
            end = P1;
        }
        mPath.moveTo(start.x,start.y);
        mPath.lineTo(end.x,end.y);
        int hOffset = (int) Math.sqrt((P1.x - P2.x)*(P1.x - P2.x) + (P1.y - P2.y)* (P1.y - P2.y))/2 - mText.length()/2*30;
        if(hOffset < 0 )
            hOffset = 0;
        int vOffset = 50;
        canvas.drawTextOnPath(mText,mPath,hOffset,vOffset,textDrawPaint);
    }

    @Override
    public void setMyText(String s) {
        mText = s;
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
