package com.example.bottomnavigationactivity.editor_components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import com.example.bottomnavigationactivity.R;

public class MyMenuButton extends ViewGroup {

    private final ViewDragHelper mDragHelper;
    private Context context;
    private View myButton;
    //private View mDescView;

    private float mInitialMotionX;
    private float mInitialMotionY;

    private int mDragRange;
    private int mTop;
    private float mDragOffset;


    public MyMenuButton(Context context) {
        this(context, null);
    }

    public MyMenuButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


    }

    public MyMenuButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        mDragHelper = ViewDragHelper.create(this, 1f, new DragHelperCallback());
    }

    public void maximize() {
        smoothSlideTo(0f);
    }

    boolean smoothSlideTo(float slideOffset) {
        final int topBound = getPaddingTop();
        int y = (int) (topBound + slideOffset * mDragRange);

        if (mDragHelper.smoothSlideViewTo(myButton, myButton.getLeft(), y)) {
            ViewCompat.postInvalidateOnAnimation(this);
            return true;
        }
        return false;
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == myButton;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            mTop = top;

            mDragOffset = (float) top / mDragRange;

            myButton.setPivotX(myButton.getWidth());
            myButton.setPivotY(myButton.getHeight());
//            mHeaderView.setScaleX(1 - mDragOffset / 2);
//            mHeaderView.setScaleY(1 - mDragOffset / 2);

            //mDescView.setAlpha(1 - mDragOffset);

            requestLayout();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int top = getPaddingTop();
            if (yvel > 0 || (yvel == 0 && mDragOffset > 0.5f)) {
                top += mDragRange;
            }
            mDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mDragRange;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - myButton.getHeight() - myButton.getPaddingBottom();

            final int newTop = Math.min(Math.max(top, topBound), bottomBound);
            return newTop;
        }
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        final int action = MotionEventCompat.getActionMasked(ev);
//
//        if ((action != MotionEvent.ACTION_DOWN)) {
//            mDragHelper.cancel();
//            return super.onInterceptTouchEvent(ev);
//        }
//
//        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
//            mDragHelper.cancel();
//            return false;
//        }
//
//        final float x = ev.getX();
//        final float y = ev.getY();
//        boolean interceptTap = false;
//
//        switch (action) {
//            case MotionEvent.ACTION_DOWN: {
//                mInitialMotionX = x;
//                mInitialMotionY = y;
//                interceptTap = mDragHelper.isViewUnder(myButton, (int) x, (int) y);
//                break;
//            }
//
//            case MotionEvent.ACTION_MOVE: {
//                final float adx = Math.abs(x - mInitialMotionX);
//                final float ady = Math.abs(y - mInitialMotionY);
//                final int slop = mDragHelper.getTouchSlop();
//                if (ady > slop && adx > ady) {
//                    mDragHelper.cancel();
//                    return false;
//                }
//            }
//        }
//
//        return mDragHelper.shouldInterceptTouchEvent(ev) || interceptTap;
//    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        mDragHelper.processTouchEvent(ev);
//
//        final int action = ev.getAction();
//        final float x = ev.getX();
//        final float y = ev.getY();
//
//        boolean isHeaderViewUnder = mDragHelper.isViewUnder(myButton, (int) x, (int) y);
////        switch (action & MotionEventCompat.ACTION_MASK) {
////            case MotionEvent.ACTION_DOWN: {
////                mInitialMotionX = x;
////                mInitialMotionY = y;
////                break;
////            }
////
////            case MotionEvent.ACTION_UP: {
////                final float dx = x - mInitialMotionX;
////                final float dy = y - mInitialMotionY;
////                final int slop = mDragHelper.getTouchSlop();
////                if (dx * dx + dy * dy < slop * slop && isHeaderViewUnder) {
////                    if (mDragOffset == 0) {
////                        smoothSlideTo(1f);
////                    } else {
////                        smoothSlideTo(0f);
////                    }
////                }
////                break;
////            }
////        }
//        return isHeaderViewUnder && isViewHit(myButton, (int) x, (int) y); // || isViewHit(mDescView, (int) x, (int) y);
//    }


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
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mDragRange = getHeight() - myButton.getHeight();

        myButton.layout(
                0,
                mTop,
                r,
                mTop + myButton.getMeasuredHeight());

//        mDescView.layout(
//                0,
//                mTop + myButton.getMeasuredHeight(),
//                r,
//                mTop + b);
    }
}
