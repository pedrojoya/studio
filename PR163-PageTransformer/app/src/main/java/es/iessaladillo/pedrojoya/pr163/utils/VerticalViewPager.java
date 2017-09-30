package es.iessaladillo.pedrojoya.pr163.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import es.iessaladillo.pedrojoya.pr163.R;
import es.iessaladillo.pedrojoya.pr163.transformers.VerticalTransformer;

public class VerticalViewPager extends ViewPager {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private int mSwipeOrientation;

    public VerticalViewPager(Context context) {
        super(context);
        mSwipeOrientation = HORIZONTAL;
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSwipeOrientation(context, attrs);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(mSwipeOrientation == VERTICAL ? swapXY(event) : event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mSwipeOrientation == VERTICAL) {
            boolean intercepted = super.onInterceptHoverEvent(swapXY(event));
            swapXY(event);
            return intercepted;
        }
        return super.onInterceptTouchEvent(event);
    }

    public void setSwipeOrientation(int swipeOrientation) {
        if (swipeOrientation == HORIZONTAL || swipeOrientation == VERTICAL) {
            mSwipeOrientation = swipeOrientation;
        } else {
            throw new IllegalStateException("La orientación debe ser VerticalViewPager.HORIZONTAL"
                    + " o VerticalViewPager.VERTICAL");
        }
        initSwipeMethods();
    }

    private void setSwipeOrientation(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.VerticalViewPager);
        mSwipeOrientation = typedArray.getInteger(R.styleable.VerticalViewPager_swipe_orientation,
                0);
        typedArray.recycle();
        initSwipeMethods();
    }

    private void initSwipeMethods() {
        if (mSwipeOrientation == VERTICAL) {
            // The majority of the work is done over here
            setPageTransformer(true, new VerticalTransformer());
            // The easiest way to get rid of the overscroll drawing that happens on the left and
            // right
            setOverScrollMode(OVER_SCROLL_NEVER);
        }
    }

    private MotionEvent swapXY(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();

        float newX = (event.getY() / height) * width;
        float newY = (event.getX() / width) * height;

        event.setLocation(newX, newY);
        return event;
    }

}
