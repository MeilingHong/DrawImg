package com.meiling.drawimg.widget.split;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.util.List;

/**
 * 2022-04-20 15:44
 *
 * @author marisareimu
 */
public class DrawImage extends View {

    private List<RectF> mList;
    private Paint mPaint;
    private RectF mBg;
    private int bgColor;
    private List<Integer> mColorBg;

    private Gson mGson = new Gson();

    public DrawImage(@NonNull Context context) {
        super(context);
        initPaint();
    }

    public DrawImage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public DrawImage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void setParameters(List<RectF> mList, List<Integer> mColorBg, RectF mBg, int bgColor) {
        this.mList = mList;
        this.mColorBg = mColorBg;
        this.mBg = mBg;
        this.bgColor = bgColor;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("AndroidRuntime", "---------------------------------");
        // 先整体绘制黑色背景
        if (mList == null || mList.size() < 1) {
            return;
        }
        for (RectF temp : mList) {
            mPaint.setColor(mColorBg != null && mColorBg.size() > 0 && mColorBg.get(0) != 0 ? mColorBg.get(0) : Color.WHITE);
            canvas.drawRect(temp, mPaint);
//            Log.e("AndroidRuntime", "index:" + "<-->" + mGson.toJson(temp) + "<--->" + mPaint.getColor());
        }
    }
}
