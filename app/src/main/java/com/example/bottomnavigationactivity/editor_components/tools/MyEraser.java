package com.example.bottomnavigationactivity.editor_components.tools;

import android.graphics.Canvas;
import android.graphics.Paint;

public class MyEraser extends MyLine {
    @Override
    public void draw(Canvas canvas) {
        style = Paint.Style.STROKE;
        super.draw(canvas);
    }

    @Override
    public void process(int x, int y) {
        super.process(x, y);
        if(GlobalSetting.paintView.isBDraw() == false)
            {
                int i;
                for(i =0;i<GlobalSetting.paintView.shapes.size()-1;)
                {
                    Shape shape = GlobalSetting.paintView.shapes.get(i);
                    if(shape.type == "Line" && GlobalSetting.doIntersect(this.P1,this.P2,((MyLine)shape).P1,((MyLine)shape).P2))
                    {
                        GlobalSetting.paintView.shapes.remove(i);
                    }
                    else {i++;}
                }
                GlobalSetting.paintView.shapes.remove(i);
                GlobalSetting.paintView.invalidate();
            }
    }

    public void erase(){

    }
}
