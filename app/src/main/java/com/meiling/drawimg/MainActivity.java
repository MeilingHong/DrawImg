package com.meiling.drawimg;

import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.GsonBuilder;
import com.meiling.drawimg.factory.impl.SplitImage423Impl;
import com.meiling.drawimg.widget.DrawImage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private volatile boolean isFinish = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private DrawImage show;
    private EditText splitSize;
    private SplitImage423Impl splitImage;

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
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
        if (splitImage == null) {
            splitImage = new SplitImage423Impl();
        }
        final int width = 1024;
        final int height = 1024;
        RectF bg = new RectF(0, 0, width, height);
        List<RectF> list = splitImage.generateSplitRectangle(width, height, 2, Math.min(Integer.parseInt(splitSize != null && splitSize.getText() != null && !TextUtils.isEmpty(splitSize.getText().toString()) ? splitSize.getText().toString() : "1"),4));
        List<Integer> colorList = new ArrayList<>();
        colorList.add(Color.parseColor("#ff3296fa"));
        colorList.add(Color.parseColor("#bb3296fa"));
        colorList.add(Color.parseColor("#993296fa"));
        colorList.add(Color.parseColor("#773296fa"));
        colorList.add(Color.parseColor("#553296fa"));
        Log.e("AndroidRuntime", new GsonBuilder().disableHtmlEscaping().create().toJson(colorList));
        show.setParameters(list, colorList, bg, Color.parseColor("#FF935D"));
//        Log.e("AndroidRuntime", new GsonBuilder().disableHtmlEscaping().create().toJson(list));
    }
}