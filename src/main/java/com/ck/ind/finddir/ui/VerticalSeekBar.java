package com.ck.ind.finddir.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.SeekBar;

/**
 * Created by KCSTATION on 2016/1/25.
 */
public class VerticalSeekBar extends SeekBar {

    private boolean mIsDragging;
    private float mTouchDownY;
    private int mScaledTouchSlop;
    private boolean isInScrollingContainer = false;

    public interface OnSeekBarChangeListener
    {
        void onProgressChanged(VerticalSeekBar VerticalBar, int progress, boolean fromUser);
        void onStartTrackingTouch(VerticalSeekBar VerticalBar);
        void onStopTrackingTouch(VerticalSeekBar VerticalBar);
    }

    private OnSeekBarChangeListener mOnSeekBarChangeListener;

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l)
    {
        mOnSeekBarChangeListener = l;
    }

    public boolean isInScrollingContainer()
    {
        return isInScrollingContainer;
    }

    public void setInScrollingContainer(boolean isInScrollingContainer)
    {
        this.isInScrollingContainer = isInScrollingContainer;
    }

    /**
     * On touch, this offset plus the scaled value from the position of the
     * touch will form the progress value. Usually 0.
     */
    float mTouchProgressOffset;

    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

    }

    public VerticalSeekBar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public VerticalSeekBar(Context context)
    {
        super(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {

        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec,
                                          int heightMeasureSpec)
    {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override
    protected synchronized void onDraw(Canvas canvas)
    {
        canvas.rotate(-90);
        canvas.translate(-getHeight(),0);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (!isEnabled())
        {
            return false;
        }

        switch (event.getAction())
        {
            case MotionEvent.ACTION_UP:
                if (mIsDragging)
                {
                    trackTouchEvent(event);
                    onStopTrackingTouch();
                    setPressed(false);

                }
                else
                {
                    // Touch up when we never crossed the touch slop threshold
                    // should
                    // be interpreted as a tap-seek to that location.
                    onStartTrackingTouch();
                    trackTouchEvent(event);
                    onStopTrackingTouch();

                }
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                if (mIsDragging)
                {
                    trackTouchEvent(event);

                }else
                {
                    final float y = event.getY();
                    if (Math.abs(y - mTouchDownY) > mScaledTouchSlop)
                    {
                        setPressed(true);
                        invalidate();
                        onStartTrackingTouch();
                        trackTouchEvent(event);
                        attemptClaimDrag();

                    }
                }
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                break;

            case MotionEvent.ACTION_DOWN:
                if (isInScrollingContainer())
                {

                    mTouchDownY = event.getY();
                }
                else
                {
                    setPressed(true);
                    invalidate();
                    onStartTrackingTouch();
                    trackTouchEvent(event);
                    attemptClaimDrag();
                    onSizeChanged(getWidth(), getHeight(), 0, 0);
                }

                break;
            case MotionEvent.ACTION_CANCEL:
                onStopTrackingTouch();
                setPressed(false);
                break;
        }
        return true;

    }

    private void trackTouchEvent(MotionEvent event)
    {
        final int height = getHeight();
        final int top = getPaddingTop();
        final int bottom = getPaddingBottom();
        final int available = height - top - bottom;

        int y = (int) event.getY();

        float scale;
        float progress = 0;

        if (y > height - bottom)
        {
            scale = 1.0f;
        }
        else if (y < top)
        {
            scale = 0.0f;
        }
        else
        {
            scale = (float) (y) / (float) available;
            progress = mTouchProgressOffset;
        }

        final int max = getMax();
        progress += scale * max;
        Log.i("vs","progress:"+progress+",scale:"+scale+",top:"+top);
        setProgress(Math.abs((int)progress-max));

    }

    /**
     * This is called when the user has started touching this widget.
     */
    public void onStartTrackingTouch()
    {
        mIsDragging = true;
        if (mOnSeekBarChangeListener != null) {
            mOnSeekBarChangeListener.onStartTrackingTouch(this);
        }
    }

    /**
     * This is called when the user either releases his touch or the touch is
     * canceled.
     */
    public void onStopTrackingTouch()
    {
        mIsDragging = false;
        if (mOnSeekBarChangeListener != null) {
            mOnSeekBarChangeListener.onStopTrackingTouch(this);
        }
    }

    private void attemptClaimDrag()
    {
        ViewParent p = getParent();
        if (p != null)
        {
            p.requestDisallowInterceptTouchEvent(true);
        }
    }

    @Override
    public synchronized void setProgress(int progress)
    {
        super.setProgress(progress);
        onSizeChanged(getWidth(), getHeight(), 0, 0);
        if (mOnSeekBarChangeListener != null) {
            mOnSeekBarChangeListener.onProgressChanged(this, getProgress(), isPressed());
        }
    }
}
