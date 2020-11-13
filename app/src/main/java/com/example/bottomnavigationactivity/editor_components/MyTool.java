package com.example.bottomnavigationactivity.editor_components;

import com.example.bottomnavigationactivity.ui.editor.EditorFragment;

public class MyTool {
    public enum ToolType {LINE, ERASER, TEXT, ZOOM, RATIO};
    private String name;
    private ToolType toolID;
    private int icon;
    public MyTool(String name, ToolType toolID){
        this.name = name;
        this.toolID = toolID;
    }

    public String getName() {
        return name;
    }

    public ToolType getToolID(){
        return toolID;
    }
}
