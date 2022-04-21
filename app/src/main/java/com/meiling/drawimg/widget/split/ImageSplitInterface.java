package com.meiling.drawimg.widget.split;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;

import java.io.File;
import java.util.List;

/**
 * 2022-04-20 10:29
 *
 * @author marisareimu
 */
public interface ImageSplitInterface {
    /**
     * @param width      指定进行分割的矩形宽度
     * @param height     指定进行分割矩形高度
     * @param lineWidth  分割线宽度【建议为2的倍数】
     * @param splitCount 需要进行分割的次数，即将矩形分割成多少份
     * @return 返回指定的分割后的矩形列表(相对于指定的宽高)
     */
    public abstract List<RectF> generateSplitRectangle(int width, int height, int lineWidth, int splitCount);

    /**
     * @param splitList 分割后的矩形列表
     * @return 返回分割后生成的Bitmap
     */
    public abstract Bitmap generateSplitRectangleBitmap(Context mContext,List<RectF> splitList);

    /**
     * @param bgBitmap 背景图
     * @param image    生成的分割后的图片
     * @return 返回合成后图片的文件
     */
    public abstract File generateSplitRectangleFile(Bitmap bgBitmap, Bitmap image);
}
