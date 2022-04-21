package com.meiling.drawimg.widget.split.box;

import android.graphics.RectF;

/**
 * 2022-04-21 16:39
 *
 * @author marisareimu
 */
public class SplitEntity {
    public SplitEntity() {
    }

    public SplitEntity(RectF rectF) {
        this.rectF = rectF;
    }

    public SplitEntity(RectF rectF, int color) {
        this.rectF = rectF;
        this.color = color;
    }

    // 用于分割的对象
    public RectF rectF;// 显示的矩形
    public int color;// 矩形需要填充的颜色
}
