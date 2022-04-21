package com.meiling.drawimg.widget.mondrian;

/**
 * 色块数据模型
 */
public class ColourData {

    /**
     * 颜色类型，目前仅有四种颜色： 红色("r")、黄色("y")、蓝色("b")、白色("white")
     */
    final MondrianColorType colorType;

    /**
     * 颜色对应区块链地址的位数
     * index 从 0 开始，当 type 为 white 时，不需要取 addressIndex, 值为 -1
     */
    final int addressIndex;

    final int colorValue;

    public ColourData(MondrianColorType colorType, int addressIndex, int colorValue) {
        this.colorType = colorType;
        this.addressIndex = addressIndex;
        this.colorValue = colorValue;
    }
}
