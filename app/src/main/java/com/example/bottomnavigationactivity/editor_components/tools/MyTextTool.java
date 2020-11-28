package com.example.bottomnavigationactivity.editor_components.tools;

import com.example.bottomnavigationactivity.editor_components.MyPaintView;

public class MyTextTool extends MyTool {
    public MyTextTool(String name, ToolType toolID) {
        super(name, toolID);
    }

    public MyTextTool() {

    }

    public MyTextTool(MyPaintView myPaintView) {
        super(myPaintView);
    }
}
