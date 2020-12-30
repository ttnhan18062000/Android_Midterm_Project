package com.example.simp.utility;

import android.graphics.Point;

public class MyMath {
    public static int CircularAddition(int num, int amount, int max)
    {
        amount = amount % max;
        num = num + amount;
        if (num > max)
            num = num - max;
        if (num < 0)
            num = num + max;
        return num;
    }
    public static float GetLength(Point start, Point end)
    {
        return (float)Math.sqrt((start.x - end.x)*(start.x - end.x) + (start.y - end.y)*(start.y - end.y));
    }
}
