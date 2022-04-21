package com.meiling.drawimg;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.meiling.drawimg.widget.split.RectangleView;
import com.meiling.drawimg.widget.split.SplitImage1Impl;

public class MainActivity extends AppCompatActivity {
    private RectangleView show;
    private EditText splitSize;
    private SplitImage1Impl splitImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = findViewById(R.id.show);// 显示的View
        show.getLayoutParams().width = 1024;
        show.getLayoutParams().height = 1024;

        splitSize = findViewById(R.id.splitSize);
    }

    public void createImg(View v) {
        //
        if (splitImage == null) {
            splitImage = new SplitImage1Impl();
        }
        final int width = 1024;
        final int height = 1024;
        show.setDrawItemDataArray(splitImage.generateSplitRectangle(
                width,
                height,
                10,
                Integer.parseInt(splitSize != null && splitSize.getText() != null && !TextUtils.isEmpty(splitSize.getText().toString()) ? splitSize.getText().toString() : "1"))
        );
    }
}