package com.meiling.drawimg.widget.split;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 2022-04-20 10:27
 *
 * @author marisareimu
 */
public class SplitImage423Impl implements ImageSplitInterface {
    @Override
    public List<RectF> generateSplitRectangle(int width, int height, int lineWidth, int splitCount) {
        // todo 实际实现的是一个特定的生成方式
        final RectF rectInit = new RectF(0, 0, width, height);
        final List<RectF> mList = new ArrayList<>();//
        mList.add(rectInit);
        //---------------------
        for (int i = 0; i < splitCount; i++) {
            int index = getMaxRectangle(mList, false);// 获取选中的矩形的下标
            List<RectF> tempList = doSplitRectangle(mList.get(index), lineWidth);
            mList.remove(index);// 删除分割后的矩形
            mList.addAll(tempList);// 将拆分后的两个矩形
        }
        return mList;
    }

    /**
     * 对于不同的算法，获取分割位置的方式不会相同，这里仅针对该特定算法
     *
     * @param rectFList 需要从中去矩形的列表
     * @param isSquare  是否是根据面积最大取矩形进行分割
     * @return 返回获取的矩形（需要进行分割的矩形）
     */
    private int getMaxRectangle(List<RectF> rectFList, boolean isSquare) {//
        int index = -1;
        // 计算面积，取最大
        double maxSquare = 0;
        // 计算宽高、取最大变--
        double maxLength = 0;
        int size = rectFList != null ? rectFList.size() : 0;
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                RectF temp = rectFList.get(i);
                if (isSquare) {
                    // 1、获取面积最大的一个矩形进行分割
                    if (temp != null && !temp.isEmpty() && (temp.width() * temp.height()) > maxSquare) {//
                        index = i;
                        maxSquare = temp.width() * temp.height();
                    }
                } else {
                    // 2、获取拥有最长边的矩形进行裁剪---对于相同长边
                    if (temp != null && !temp.isEmpty() && Math.max(temp.width(), temp.height()) > maxLength) {
                        index = i;
                        maxLength = Math.max(temp.width(), temp.height());
                    }
                }
            }
        }
        return index;
    }

    /**
     * @param rectF     需要进行分割的矩形
     * @param lineWidth 分割线的宽度
     * @return 分割后的后的
     */
    private List<RectF> doSplitRectangle(RectF rectF, int lineWidth) {
        boolean isWidth = false;
        isWidth = rectF.width() > rectF.height();// 如果
        float halfLine = lineWidth / 2F;
        //--------------------------------
        // 这里写取分割点的逻辑，不同的逻辑下，这个部分会不同，而不同取矩形的方法下，也不一定有这个方法
        float baseLine = (isWidth ? rectF.width() : rectF.height()) / 2F;
        float startPoint = isWidth ? rectF.left : rectF.top;// 起始点
        float random = baseLine + baseLine * 0.4F * new Random().nextFloat();
        RectF split1 = new RectF((int) rectF.left, (int) rectF.top, (int) (isWidth ? (startPoint + random - halfLine) : rectF.right), (int) (isWidth ? rectF.bottom : (startPoint + random - halfLine)));
        RectF split2 = new RectF((int) (isWidth ? (startPoint + random + halfLine) : rectF.right), (int) (isWidth ? rectF.bottom : (startPoint + random + halfLine)), (int) rectF.right, (int) rectF.bottom);
        List<RectF> arrayList = new ArrayList<>();
        arrayList.add(split1);
        arrayList.add(split2);
        //--------------------------------
        return arrayList;
    }

    @Override
    public Bitmap generateSplitRectangleBitmap(Context mContext, List<RectF> splitList) {
        ImageView imageView = new ImageView(mContext);
        Canvas canvas = new Canvas();
        Paint paint = new Paint();
        paint.setColor(0xff1c1c1e);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(0.000001F);
        paint.setStyle(Paint.Style.STROKE);
        for (RectF temp : splitList) {
            canvas.drawRect(temp, paint);
        }
        return null;
    }

    @Override
    public File generateSplitRectangleFile(Bitmap bgBitmap, Bitmap image) {
        return null;
    }
}
