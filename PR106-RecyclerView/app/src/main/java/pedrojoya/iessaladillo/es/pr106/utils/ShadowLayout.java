package pedrojoya.iessaladillo.es.pr106.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import pedrojoya.iessaladillo.es.pr106.R;

public class ShadowLayout extends FrameLayout implements ViewGroup.OnHierarchyChangeListener {

    private int mBackgroundColor;
    private int mShadowColor;
    private float mShadowRadius;
    private float mCornerRadius;
    private float mDx;
    private float mDy;

    private Paint mPaint;
    private RectF mShadowRectF;

    public ShadowLayout(Context context) {
        super(context);
        initView(context, null);
    }

    public ShadowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(getShadowRect(), mCornerRadius, mCornerRadius, mPaint);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onChildViewAdded(View parent, View child) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            child.setElevation(mShadowRadius);
            int xMargin = (int) (mShadowRadius + mDx);
            int yMargin = (int) (mShadowRadius + mDy);
            int margin = xMargin > yMargin ? xMargin : yMargin;
            params.leftMargin = margin;
            params.rightMargin = margin;
            params.topMargin = margin;
            params.bottomMargin = margin;
            child.setLayoutParams(params);
        }
    }

    @Override
    public void onChildViewRemoved(View parent, View child) {

    }

    private void initView(Context context, AttributeSet attrs) {
        initAttributes(context, attrs);

        setWillNotDraw(false);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);

            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(mBackgroundColor);
            mPaint.setStyle(Paint.Style.FILL);

            if (!isInEditMode()) {
                mPaint.setShadowLayer(mShadowRadius, mDx, mDy, mShadowColor);
            }

            int xPadding = (int) (mShadowRadius + mDx);
            int yPadding = (int) (mShadowRadius + mDy);
            setPadding(xPadding, yPadding, xPadding, yPadding);
        } else {
            setOnHierarchyChangeListener(this);
        }

    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray attr = getTypedArray(context, attrs, R.styleable.ShadowLayout);
        if (attr == null) {
            return;
        }

        try {
            mCornerRadius = attr.getDimension(R.styleable.ShadowLayout_cornerRadius, 6);
            mShadowRadius = attr.getDimension(R.styleable.ShadowLayout_shadowRadius, 8);
            mDx = attr.getDimension(R.styleable.ShadowLayout_dx, 0);
            mDy = attr.getDimension(R.styleable.ShadowLayout_dy, 0);
            mShadowColor = attr.getColor(R.styleable.ShadowLayout_shadowColor, Color.parseColor("#88757575"));
            mBackgroundColor = attr.getColor(R.styleable.ShadowLayout_backgroundColor, Color.WHITE);
        } finally {
            attr.recycle();
        }
    }

    private TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    private RectF getShadowRect() {
        if (mShadowRectF == null) {
            mShadowRectF = new RectF(mShadowRadius + mDx, mShadowRadius + mDy,
                    getWidth() - mShadowRadius - mDx, getHeight() - mShadowRadius - mDy);
        }

        return mShadowRectF;
    }

}