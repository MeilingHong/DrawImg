package com.meiling.drawimg.widget.mondrian;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 目前仅有四种颜色： 红色("r")、黄色("y")、蓝色("b")、白色("white")
 */
public enum MondrianColorType {

    RED("r"),
    YELLOW("y"),
    BLUE("b"),
    WHITE("white");

    public final String typeName;

    MondrianColorType(@NonNull String typeName) {
        this.typeName = typeName;
    }

    public static MondrianColorType valueOfTypeName(@Nullable String typeName) {
        for (MondrianColorType mondrianColorType : values()) {
            if (mondrianColorType.typeName.equals(typeName)) {
                return mondrianColorType;
            }
        }
        return WHITE;
    }
}
