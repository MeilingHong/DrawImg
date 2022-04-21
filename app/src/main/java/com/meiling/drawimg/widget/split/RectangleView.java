package com.meiling.drawimg.widget.split;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.meiling.drawimg.widget.split.box.SplitEntity;

import java.util.List;

public class RectangleView extends View {

    @Nullable
    private List<SplitEntity> mDrawItemDataArray;
    private final Paint mPaint;
    private Gson mGson = new Gson();

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
        for (SplitEntity temp : mDrawItemDataArray) {
            mPaint.setColor(temp.color);
            canvas.drawRect(temp.rectF, mPaint);
            Log.e("AndroidRuntime", "index:" + "<-->" + mGson.toJson(temp) + "<--->" + mPaint.getColor());
        }
    }

    public void setDrawItemDataArray(@Nullable List<SplitEntity> drawItemDataArray) {
        this.mDrawItemDataArray = drawItemDataArray;
        invalidate();
    }
}
