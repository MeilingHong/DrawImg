package com.meiling.drawimg.widget.mondrian;

import android.graphics.RectF;

import androidx.annotation.NonNull;

public class DrawItemData {

    @NonNull
    final ColourData colourData;

    @NonNull
    final RectF rectF;

    public DrawItemData(@NonNull ColourData colourData, @NonNull RectF rectF) {
        this.colourData = colourData;
        this.rectF = rectF;
    }
}
