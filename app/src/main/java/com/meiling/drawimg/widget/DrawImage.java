package com.meiling.drawimg.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.util.List;

/**
 * 2022-04-20 15:44
 *
 * @author marisareimu
 */
public class DrawImage extends androidx.appcompat.widget.AppCompatImageView {

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
        mPaint.setColor(Color.WHITE);// 默认白色画笔
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void setParameters(List<RectF> mList, List<Integer> mColorBg, RectF mBg, int bgColor) {
        this.mList = mList;
        this.mColorBg = mColorBg;
        this.mBg = mBg;
        this.bgColor = bgColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("AndroidRuntime", "---------------------------------");
        // 先整体绘制黑色背景
        if (mBg != null) {
            if (bgColor != 0) mPaint.setColor(bgColor);
            canvas.drawRect(mBg, mPaint);
        }

        if (mList != null && mList.size() > 0) {
            for (RectF temp : mList) {
                int i = mList.indexOf(temp);
                mPaint.setColor(mColorBg != null && mColorBg.size() > i && mColorBg.get(i) != 0 ? mColorBg.get(i) : Color.WHITE);
                canvas.drawRect(temp, mPaint);
                Log.e("AndroidRuntime", "index:" + i + "<-->" + mGson.toJson(mList.get(i)) + "<--->" + mPaint.getColor());
            }
        }
    }
}
