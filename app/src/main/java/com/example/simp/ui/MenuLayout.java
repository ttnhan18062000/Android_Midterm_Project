package com.example.simp.ui;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

//import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.example.simp.FloatingMenu.FloatingActionMenu;

public class MenuLayout extends ConstraintLayout {

    private final ViewDragHelper mDragHelper;
    public FloatingActionMenu actionMenu;

    public View mHeaderView;
    private float mInitialMotionX;
    private float mInitialMotionY;

    private int mXDragRange;
    private int mTop;
    private int mLeft;
    private float mDragOffset;
    private int mTouchSlop;
    private int mYDragRange;


    public MenuLayout(Context context) {
        this(context,null,0);
    }

    public MenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }




    public MenuLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mDragHelper = ViewDragHelper.create(this, 1f, new DragHelperCallback());
    }

    public void maximize() {
        smoothSlideTo(0f);
    }

    boolean smoothSlideTo(float slideOffset) {
        final int topBound = getPaddingTop();
        final int leftBound = getPaddingLeft();
        int y = (int) (topBound + slideOffset * mXDragRange);
        int x = (int) (leftBound + slideOffset * mYDragRange);
        if (mDragHelper.smoothSlideViewTo(mHeaderView, x, y)) {
            ViewCompat.postInvalidateOnAnimation(this);
            return true;
        }
        return false;
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mHeaderView;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            mTop = top;
            mLeft = left;
            Resources r = getResources();
            int px = (int)TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    96,
                    r.getDisplayMetrics());
            int bottomBound = getMeasuredHeight() - changedView.getHeight();
            int rightBound = getMeasuredWidth() - changedView.getWidth();
            int[] location = new int[2];
            changedView.getLocationOnScreen(location);

            int pad = (int) (0.5*px);
            int mCase = (left > pad ? 1:0) + (left > rightBound - pad?1:0) + 3* ((top > pad ? 1:0) + (top > bottomBound - pad?1:0));
            int sPx = (int) (0.65*px);
            Log.d("DemoPosition","rBound :" + rightBound + ". bBound: " + bottomBound  + ". Left: " + left + ". Top: " + top + ". Case: " + mCase +". Pad: " + pad + ". px: " + px + ". sPx: " + sPx);

            switch (mCase)
            {
                case 0:
                    actionMenu.setRadius(px);
                    actionMenu.setAngle(0,90);
                    break;
                case 1:
                    actionMenu.setRadius(sPx);
                    actionMenu.setAngle(0,180);
                    break;
                case 2:
                    actionMenu.setRadius(px);
                    actionMenu.setAngle(90,180);
                    break;
                case 3:
                    actionMenu.setRadius(sPx);
                    actionMenu.setAngle(270,450);
                    break;
                case 4:
                    actionMenu.setRadius(sPx);
                    actionMenu.setAngle(0,360);
                    break;
                case 5:
                    actionMenu.setRadius(sPx);
                    actionMenu.setAngle(90,270);
                    break;
                case 6:
                    actionMenu.setRadius(px);
                    actionMenu.setAngle(270,360);
                    break;
                case 7:
                    actionMenu.setRadius(sPx);
                    actionMenu.setAngle(180,360);
                    break;
                case 8:
                    actionMenu.setRadius(px);
                    actionMenu.setAngle(180,270);
                    break;
            }

//            mHeaderView.setScaleX(1 - mDragOffset / 2);
//            mHeaderView.setScaleY(1 - mDragOffset / 2);

            //mDescView.setAlpha(1 - mDragOffset);

            requestLayout();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int top = getPaddingTop();
            int left = getPaddingLeft();
            if (yvel > 0) {
                top += mXDragRange;
            }
            if(xvel >0)
            {
                left += mYDragRange;
            }
            mDragHelper.settleCapturedViewAt(left, top);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mXDragRange;
        }

        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return mYDragRange;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - mHeaderView.getHeight() - mHeaderView.getPaddingBottom();

            final int newTop = Math.min(Math.max(top, topBound), bottomBound);
            return newTop;
        }
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.d("DragLayout", "clampViewPositionHorizontal " + left + "," + dx);

            final int leftBound = getPaddingLeft();
            final int rightBound = getWidth() - mHeaderView.getWidth() - mHeaderView.getPaddingRight();

            final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

            return newLeft;
        }

    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    boolean bDrag;
    public boolean bIntercept = false;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(actionMenu.isOpen())
            return false;
        final int action = MotionEventCompat.getActionMasked(ev);

//
//        if ((action != MotionEvent.ACTION_DOWN)) {
//            mDragHelper.cancel();
//            return super.onInterceptTouchEvent(ev);
//        }

        mDragHelper.processTouchEvent(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            bDrag = false;
            return false;
        }

        final float x = ev.getX();
        final float y = ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mInitialMotionX = x;
                mInitialMotionY = y;
                Log.d("Demo","on Intercept action down");
                //interceptTap = !mDragHelper.isViewUnder(mHeaderView, (int) x, (int) y);
            }

            case MotionEvent.ACTION_MOVE: {
//                if(bDrag)
//                    return true;
//                final float adx = Math.abs(x - mInitialMotionX);
//                final float ady = Math.abs(y - mInitialMotionY);
//                final int slop = mDragHelper.getTouchSlop();
//                if (ady > slop && adx > ady) {
//                    mDragHelper.cancel();
//                    bDrag = true;
//                    return true;
//                }
//                else bDrag = false;

                ViewConfiguration vc = ViewConfiguration.get(getContext());
                final int diff = calculateDistance(ev);
                mTouchSlop = mDragHelper.getTouchSlop();
                if (diff > mTouchSlop) {
                    // Start scrolling!
                    Log.d("Demo","Start Scrolling!");
                    bDrag = true;
                    return true;
                }
                else bDrag = false;
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mDragHelper.processTouchEvent(ev);

        final int action = ev.getAction();
        final float x = ev.getX();
        final float y = ev.getY();
//
//        boolean isHeaderViewUnder = mDragHelper.isViewUnder(mHeaderView, (int) x, (int) y);
//        switch (action & MotionEventCompat.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN: {
//                mInitialMotionX = x;
//                mInitialMotionY = y;
//                break;
//            }
//
//            case MotionEvent.ACTION_UP: {
//                final float dx = x - mInitialMotionX;
//                final float dy = y - mInitialMotionY;
//                final int slop = mDragHelper.getTouchSlop();
//                if (dx * dx + dy * dy < slop * slop && isHeaderViewUnder) {
//                    if (mDragOffset == 0) {
//                       smoothSlideTo(1f);
//                    } else {
//                        smoothSlideTo(0f);
//                    }
//                }
//                break;
//            }
//        }

        switch (action & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
            {
                bDrag = false;
                break;
            }
        }
        if(actionMenu.isOpen())
        {
            actionMenu.close(true);
            return true;
        }
        else
            return false;
    }


    private int calculateDistance(MotionEvent ev) {
        return (int) Math.sqrt((int)(ev.getY() - mInitialMotionY)*(ev.getY() - mInitialMotionY) + (ev.getX() - mInitialMotionX)*(ev.getX() - mInitialMotionX));
    }

    private boolean isViewHit(View view, int x, int y) {
        int[] viewLocation = new int[2];
        view.getLocationOnScreen(viewLocation);
        int[] parentLocation = new int[2];
        this.getLocationOnScreen(parentLocation);
        int screenX = parentLocation[0] + x;
        int screenY = parentLocation[1] + y;
        return screenX >= viewLocation[0] && screenX < viewLocation[0] + view.getWidth() &&
                screenY >= viewLocation[1] && screenY < viewLocation[1] + view.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {

        super.onLayout(changed,l,t,r,b);
        mXDragRange = getHeight() - mHeaderView.getMeasuredHeight();
        mYDragRange = getWidth() - mHeaderView.getMeasuredWidth();
        mHeaderView.layout(
                mLeft,
                mTop,
                mLeft + mHeaderView.getMeasuredWidth(),
                mTop + mHeaderView.getMeasuredHeight());


    }
}
