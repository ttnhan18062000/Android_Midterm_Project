package com.example.simp.utility;

public class MyFileManager {
    public static String getExtension(String filePath)
    {
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }
}
