package com.example.bottomnavigationactivity.editor_components;

import com.example.bottomnavigationactivity.ui.editor.EditorFragment;

public class MyTool {
    public enum ToolType {LINE, ERASER, TEXT, ZOOM, RATIO, MOVE};
    private String name;
    private ToolType toolID;
    private String describtion;
    private int icon;
    public MyTool(String name, ToolType toolID, String describtion){
        this.name = name;
        this.toolID = toolID;
        this.describtion = describtion;
    }

    public String getName() {
        return name;
    }
    public ToolType getToolID(){
        return toolID;
    }
    public String getDescribtion() {
        return describtion;
    }


}
