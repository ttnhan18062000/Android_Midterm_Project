package com.example.bottomnavigationactivity.editor_components;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class MyText extends Shape {

    private Paint textDrawPaint;
    private String mText;
    private Path mPath;

    @Override
    public void process(int x, int y) {
        P2.x = x;
        P2.y = y;
    }

    @Override
    public void setMyText(String s) {
        mText = s;
    }

    public MyText() {
        mText = "Enter your Text here!";
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
//        paint.setARGB(255, 255, 0, 0);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(40);
        drawText(canvas);
    }
    
    private void drawText(Canvas canvas) {
        textDrawPaint = new Paint();
        int lineLength = (int) Math.sqrt((P1.x - P2.x)*(P1.x - P2.x) + (P1.y - P2.y)* (P1.y - P2.y));
        textDrawPaint.setTextSize(lineLength/10);
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
        int hOffset = (int) lineLength/2 - mText.length()/2*30;
        if(hOffset < 0 )
            hOffset = 0;
        int vOffset = 50;
        canvas.drawTextOnPath(mText,mPath,hOffset,vOffset,textDrawPaint);
    }


}
