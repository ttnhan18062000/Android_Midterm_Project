package com.example.bottomnavigationactivity.utility;

public class MyFileManager {
    public static String getExtension(String filePath)
    {
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }
}
