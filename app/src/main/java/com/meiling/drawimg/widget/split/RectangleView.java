package com.meiling.drawimg.widget.split;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RectangleView extends View {

    @Nullable
    private List<RectF> mDrawItemDataArray;

    private final Paint mPaint;

    public RectangleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mDrawItemDataArray == null || mDrawItemDataArray.isEmpty()) {
            return;
        }
        for (RectF drawItemData : mDrawItemDataArray) {
            mPaint.setColor(Color.GRAY);
            canvas.drawRect(drawItemData, mPaint);
        }
    }

    public void setDrawItemDataArray(@Nullable List<RectF> drawItemDataArray) {
        this.mDrawItemDataArray = drawItemDataArray;
        invalidate();
    }
}
