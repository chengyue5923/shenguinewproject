package com.shengui.app.android.shengui.utils.im;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.kdmobi.gui.R;


/**
 * 图片组件
 * Created by HW on 2015/10/5.
 */
public class UISquaredImageView extends SquaredImageView {

    private int WIDTH;
    private int HEIGHT;
    private int PAINT_ALPHA = 48;

    private int mPressedColor;
    private Paint mPaint;
    private int mShapeType;
    private int mRadius;

    public UISquaredImageView(Context context) {
        super(context);
    }

    public UISquaredImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public UISquaredImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(final Context context, final AttributeSet attrs) {
        if (isInEditMode())
            return;
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UIButton);
        mPressedColor = typedArray.getColor(R.styleable.UIButton_color_pressed, getResources().getColor(R.color.color_pressed));
        PAINT_ALPHA = typedArray.getInteger(R.styleable.UIButton_alpha_pressed, PAINT_ALPHA);
        mShapeType = typedArray.getInt(R.styleable.UIButton_shape_type, 1);
        mRadius = typedArray.getDimensionPixelSize(R.styleable.UIButton_ui_radius, getResources().getDimensionPixelSize(R.dimen.twoDp));
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mPressedColor);
        this.setWillNotDraw(false);
        mPaint.setAlpha(0);
        mPaint.setAntiAlias(true);
        this.setDrawingCacheEnabled(true);
//        this.setClickable(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        WIDTH = w;
        HEIGHT = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPaint == null) return;
        if (mShapeType == 0) {
            canvas.drawCircle(WIDTH/2, HEIGHT/2, WIDTH/2.1038f, mPaint);
        } else {
            RectF rectF = new RectF();
            rectF.set(0, 0, WIDTH, HEIGHT);
            canvas.drawRoundRect(rectF, mRadius, mRadius, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPaint.setAlpha(PAINT_ALPHA);
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mPaint.setAlpha(0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }


}
