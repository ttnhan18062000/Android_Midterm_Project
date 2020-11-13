package com.example.bottomnavigationactivity.utility;

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

}
