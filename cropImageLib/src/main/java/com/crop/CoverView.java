package com.crop;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.crop.jian.cropimagelib.R;

/**
 * jian
 * 这里是可剪切图片的浮层前景图
 */
public class CoverView extends ViewGroup {
    private View coverHeader;
    private View coverMiddle;
    private View coverBottom;

    private int mHVerticalPadding;

    public CoverView(Context context) {
        this(context, null);
    }

    public CoverView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoverView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(context);
    }

    public void setHVerticalPadding(int padding) {
        mHVerticalPadding = padding;
    }

    private void initViews(Context context) {
        LayoutInflater from = LayoutInflater.from(context);
        coverHeader = from.inflate(R.layout.crop_cover_view_top, null);
        coverMiddle = from.inflate(R.layout.crop_cover_view_middle, null);
        coverBottom = from.inflate(R.layout.crop_cover_view_bottom, null);
        addView(coverHeader);
        addView(coverMiddle);
        addView(coverBottom);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            int headViewHeight = mHVerticalPadding;
            int middleViewHeight = getWidth();

            coverHeader.layout(0, 0, getWidth(), headViewHeight);
            coverMiddle.layout(0, headViewHeight, getWidth(), headViewHeight + middleViewHeight);
            coverBottom.layout(0, headViewHeight + middleViewHeight, getWidth(), getHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
