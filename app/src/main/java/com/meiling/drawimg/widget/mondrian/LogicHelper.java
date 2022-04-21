package com.meiling.drawimg.widget.mondrian;

import android.graphics.Color;
import android.graphics.RectF;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;

import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * 周年庆不限量盲盒，随机生成矩形集合工具类
 */
public class LogicHelper {

    //最终生成的图片中实际宽度
    public final static int FACT_PIC_WIDTH = 1024;

    //"蒙德里安"在最终生成的图片中的实际宽度
    public final static int FACT_MONDRIAN_AREA_WIDTH = 784;

    //个人链地址长度
    public final static int ADDRESS_COUNT = 42;

    //有颜色的色块总数
    public final static int COLOUR_COUNT = 40;

    /**
     * 基准画布坐标系
     */
    @Nullable
    private RectF mBaseCanvasRectF;

    /**
     * 线宽
     */
    private final int mLineWidth;

    /**
     * 黄色矩形总数
     */
    public final int yellowCount;

    /**
     * 红色矩形总数
     */
    public final int redCount;

    /**
     * 蓝色矩形总数
     */
    public final int blueCount;

    /**
     * 白色矩形总数
     */
    public final int whiteCount;

    /**
     * 矩形总数
     */
    private final int count;

    private final ArrayList<RectF> mAllFrames = new ArrayList<>();

    @Nullable
    private ArrayList<DrawItemData> mDrawItemDataArray;

    @Nullable
    private String mLogicJsonStr;

    @Nullable
    private String mAddress;

    private final int mOutlineSize;

    public LogicHelper() {

        mLineWidth = 8;

        //约定: 生成的蒙德里安的图片是不带外边框的
        mOutlineSize = 0;

        //产品约定：红黄蓝总数是 40 ，然后红黄蓝至少有一个，然后白色随机 1～9 个
        //红8-15随机，黄8-15个随机，绿 40-红-黄，白色1-9随机 (范围均包含两端的边界值)
        yellowCount = 8 + new Random().nextInt(8);
        redCount = 8 + new Random().nextInt(8);
        blueCount = COLOUR_COUNT - redCount - yellowCount;
        whiteCount = 1 + new Random().nextInt(9);
        count = yellowCount + redCount + blueCount + whiteCount;
    }

    public void generate(int width, int height, @Nullable String address) {

        if (width <= 0 || height <= 0) {
            return;
        }
        mBaseCanvasRectF = new RectF(mOutlineSize, mOutlineSize, (width - mOutlineSize), (height - mOutlineSize));
        mAllFrames.add(mBaseCanvasRectF);

        if (address == null || address.length() < COLOUR_COUNT) {
            return;
        }

        final ArrayList<ColourData> colourDataArray = getColorDataArray(address);

        final ArrayList<RectF> rectFS = rectangleGenerator();

        if (colourDataArray.size() != rectFS.size()) {
            return;
        }

        final int itemCount = rectFS.size();
        mDrawItemDataArray = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            mDrawItemDataArray.add(new DrawItemData(colourDataArray.get(i), rectFS.get(i)));
        }

        final ArrayList<MondrianJsonSourceData> jsonSourceData = getJsonArraySource();
        mLogicJsonStr = new GsonBuilder().disableHtmlEscaping().create().toJson(jsonSourceData);
    }

    @Nullable
    public ArrayList<DrawItemData> getDrawItemDataArray() {
        return mDrawItemDataArray;
    }

    public String getLogicJsonString() {
        return mLogicJsonStr;
    }

    @Nullable
    public ArrayList<DrawItemData> testJson() {

        if (mDrawItemDataArray == null) {
            return null;
        }

        try {
            final JSONArray jsonArray = new JSONArray(mLogicJsonStr);
            ArrayList<DrawItemData> testDataArray = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONObject jsonObject = jsonArray.getJSONObject(i);
                final MondrianColorType mondrianColorType = MondrianColorType.valueOfTypeName(
                        jsonObject.getString("type")
                );
                final int addressIndex = jsonObject.getInt("addressIndex");
                final int colorValue;
                if (mAddress == null) {
                    colorValue = Color.GRAY;
                } else if (addressIndex < 0 || addressIndex >= mAddress.length()) {
                    colorValue = Color.WHITE;
                } else {
                    colorValue = getColorValue(mAddress.charAt(addressIndex), mondrianColorType);
                }
                final DrawItemData drawItemData = new DrawItemData(
                        new ColourData(
                                mondrianColorType,
                                addressIndex,
                                colorValue
                        ),
                        new RectF(
                                jsonObject.getInt("x"),
                                jsonObject.getInt("y"),
                                jsonObject.getInt("x") + jsonObject.getInt("w"),
                                jsonObject.getInt("y") + jsonObject.getInt("h")
                        )
                );
                testDataArray.add(drawItemData);
            }

            if (testDataArray.size() != mDrawItemDataArray.size()) {
            }

            return testDataArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    private ArrayList<MondrianJsonSourceData> getJsonArraySource() {

        if (mDrawItemDataArray == null || mDrawItemDataArray.isEmpty() || mBaseCanvasRectF == null) {
            return null;
        }

        final float baseWidth = mBaseCanvasRectF.width();

        if (baseWidth <= 0) {
            return null;
        }

        final float factor = FACT_MONDRIAN_AREA_WIDTH / (baseWidth + mOutlineSize * 2);

        final ArrayList<MondrianJsonSourceData> sourceDataArray = new ArrayList<>();

        for (DrawItemData drawItemData : mDrawItemDataArray) {
            final MondrianJsonSourceData sourceData = new MondrianJsonSourceData(
                    (int) Math.round(drawItemData.rectF.left * factor),
                    (int) Math.round(drawItemData.rectF.top * factor),
                    (int) Math.round(drawItemData.rectF.width() * factor),
                    (int) Math.round(drawItemData.rectF.height() * factor),
                    drawItemData.colourData.colorType.typeName,
                    drawItemData.colourData.addressIndex
            );
            sourceDataArray.add(sourceData);
        }

        return sourceDataArray;
    }

    private ArrayList<ColourData> getColorDataArray(@NonNull String address) {

        final String validAddress;
        if (address.length() >= ADDRESS_COUNT) {
            validAddress = address.substring(2, ADDRESS_COUNT);
        } else {
            validAddress = address;
        }

        mAddress = validAddress;

        //依次取黄、红、蓝
        final int[] countArray = {yellowCount, redCount, blueCount};
        final MondrianColorType[] colourArray = {MondrianColorType.YELLOW, MondrianColorType.RED, MondrianColorType.BLUE};

        final ArrayList<ColourData> colourDataArray = new ArrayList<>();

        for (int i = 0; i < countArray[0]; i++) {
            final int colorValue = getColorValue(validAddress.charAt(i), colourArray[0]);
            final ColourData colourData = new ColourData(colourArray[0], i, colorValue);
            colourDataArray.add(colourData);
        }

        for (int i = countArray[0]; i < countArray[0] + countArray[1]; i++) {
            final int colorValue = getColorValue(validAddress.charAt(i), colourArray[1]);
            final ColourData colourData = new ColourData(colourArray[1], i, colorValue);
            colourDataArray.add(colourData);
        }

        for (int i = countArray[0] + countArray[1]; i < COLOUR_COUNT; i++) {
            final int colorValue = getColorValue(validAddress.charAt(i), colourArray[2]);
            final ColourData colourData = new ColourData(colourArray[2], i, colorValue);
            colourDataArray.add(colourData);
        }

        //白色
        for (int i = COLOUR_COUNT; i < COLOUR_COUNT + whiteCount; i++) {
            //index 从 0 开始，当 type 为 white 时，不需要取 addressIndex, 值为 -1
            final ColourData colourData = new ColourData(MondrianColorType.WHITE, -1, Color.WHITE);
            colourDataArray.add(colourData);
        }

        //打散颜色集合
        Collections.shuffle(colourDataArray);

        return colourDataArray;
    }

    @ColorInt
    private int getColorValue(char addressChar, @NonNull MondrianColorType colorType) {
        final Integer bit = Integer.valueOf(String.valueOf(addressChar), 16);
        final int y = 255 - bit;
        final int checkedY = Math.min(Math.max(0, y), 255);
        switch (colorType) {
            case RED:
                return Color.rgb(255, checkedY, checkedY);
            case YELLOW:
                return Color.rgb(255, 255, checkedY);
            case BLUE:
                return Color.rgb(checkedY, checkedY, 255);
            case WHITE:
            default:
                return Color.WHITE;
        }
    }

    @NonNull
    private ArrayList<RectF> rectangleGenerator() {

        for (int i = 0; i < count - 1; i++) {

            //获取当前最大面积矩形
            final int maxIndex = getMaxIndex();
            final RectF maxFrames = mAllFrames.remove(maxIndex);

            //Ulog.i("LogicHelper rectangleGenerator maxIndex: " + maxIndex);

            //把当前集合中最大的矩形，分割成2个
            @Size(2) final RectF[] partitionedFrames = rectangleSplit(maxFrames);
            mAllFrames.add(partitionedFrames[0]);
            mAllFrames.add(partitionedFrames[1]);
        }

        return mAllFrames;
    }

    @Size(2)
    private RectF[] rectangleSplit(RectF frames) {

        final float maxValue = Math.max(frames.width(), frames.height());

        final float point = random(maxValue);
        if (frames.width() >= frames.height()) {
            //垂直分割
            final float leftRectWidth = point - mLineWidth / 2f;
            final float rightRectStartX = frames.left + leftRectWidth + mLineWidth / 2f;
            final RectF leftRect = new RectF(
                    frames.left,
                    frames.top,
                    frames.left + leftRectWidth,
                    frames.bottom
            );
            final RectF rightRect = new RectF(
                    rightRectStartX,
                    frames.top,
                    frames.right,
                    frames.bottom
            );
            return new RectF[]{leftRect, rightRect};
        } else {

            //水平分割
            final float topRectHeight = point - mLineWidth / 2f;
            final float bottomRectStartY = frames.top + topRectHeight + mLineWidth / 2f;
            final RectF topRect = new RectF(
                    frames.left,
                    frames.top,
                    frames.right,
                    frames.top + topRectHeight
            );
            final RectF bottomRect = new RectF(
                    frames.left,
                    bottomRectStartY,
                    frames.right,
                    frames.bottom
            );
            return new RectF[]{topRect, bottomRect};
        }
    }

    // 根据最长边随机生成分割位置
    private float random(float src) {
        return (float) (src / 2f + Math.random() * (2f * src / 5f));
    }

    private int getMaxIndex() {
        int maxIndex = 0;
        float lastAreas = -1f;
        for (int i = 0; i < mAllFrames.size(); i++) {
            final RectF frames = mAllFrames.get(i);
            final float areas = frames.width() * frames.height();
            if (areas > lastAreas) {
                maxIndex = i;
                lastAreas = areas;
            }
        }
        return maxIndex;
    }
}


