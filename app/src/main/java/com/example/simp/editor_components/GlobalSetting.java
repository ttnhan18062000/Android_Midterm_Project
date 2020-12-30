package com.example.simp.editor_components;

import android.graphics.Point;

import com.example.simp.editor_components.shape.LinearShape;
import com.example.simp.editor_components.shape.MyBitmap;
import com.example.simp.editor_components.shape.MyShape;

public class GlobalSetting {
    public static int SelectedColor = 255 << 24;
    public static MyPaintView paintView;

    static boolean onSegment(Point p, Point q, Point r)
    {
        if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
                q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
            return true;

        return false;
    }

    static int orientation(Point p, Point q, Point r)
    {
        // See https://www.geeksforgeeks.org/orientation-3-ordered-points/
        // for details of below formula.
        int val = (q.y - p.y) * (r.x - q.x) -
                (q.x - p.x) * (r.y - q.y);

        if (val == 0) return 0; // colinear

        return (val > 0)? 1: 2; // clock or counterclock wise
    }

    public static boolean doIntersect(Point p1, Point q1, MyShape myShape)
    {
        // Find the four orientations needed for general and
        // special cases
        if(myShape instanceof LinearShape)
        {
            Point p2 = ((LinearShape) myShape).P1, q2 = ((LinearShape) myShape).P2;
            return doIntersect(p1,q1,p2,q2);
        }
        else
        {
            MyBitmap bitmap = (MyBitmap) myShape;
            int left = bitmap.left,top = bitmap.top,right = bitmap.right,bottom = bitmap.bottom;
            boolean rtn = false;
            Point[] corner = new Point[4];
            corner[0] = new Point(left,top);
            corner[1] = new Point(right,top);
            corner[2] = new Point(left,bottom);
            corner[3] = new Point(right,bottom);
            rtn = rtn || doIntersect(p1,q1,corner[0],corner[1])
                    || doIntersect(p1,q1,corner[0],corner[2])
                    || doIntersect(p1,q1,corner[1],corner[3])
                    || doIntersect(p1,q1,corner[2],corner[3]);
            rtn = rtn || (Math.min(p1.x,q1.x) > left
                    && Math.max(p1.x,q1.x) < right
                    && Math.min(p1.y,q1.y) > top
                    && Math.max(p1.y,q1.y) < bottom);
            return rtn;
        }
    }

    public static boolean doIntersect(Point p1, Point q1, Point p2,Point q2)
    {
        // Find the four orientations needed for general and
        // special cases

        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases
        // p1, q1 and p2 are colinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;

        // p1, q1 and q2 are colinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;

        // p2, q2 and p1 are colinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;

        // p2, q2 and q1 are colinear and q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;

        return false; // Doesn't fall in any of the above cases
    }


    static int min(int a,int b) { return a < b ? a: b;}
    static int max(int a,int b) { return a > b ? a: b;}
}
