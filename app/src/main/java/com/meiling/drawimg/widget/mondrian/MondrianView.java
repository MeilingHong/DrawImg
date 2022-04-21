package com.meiling.drawimg.widget.mondrian;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MondrianView extends View {

    @Nullable
    private ArrayList<DrawItemData> mDrawItemDataArray;

    private final Paint mPaint;

    public MondrianView(Context context, @Nullable AttributeSet attrs) {
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
        for (DrawItemData drawItemData : mDrawItemDataArray) {
            mPaint.setColor(drawItemData.colourData.colorValue);
            canvas.drawRect(drawItemData.rectF, mPaint);
        }
    }

    public void setDrawItemDataArray(@Nullable ArrayList<DrawItemData> drawItemDataArray) {
        this.mDrawItemDataArray = drawItemDataArray;
        invalidate();
    }
}
