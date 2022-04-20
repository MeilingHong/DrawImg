package com.meiling.drawimg;

import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.GsonBuilder;
import com.meiling.drawimg.factory.impl.SplitImage423Impl;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private volatile boolean isFinish = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ImageView show;
    private SplitImage423Impl splitImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = findViewById(R.id.show);// 显示的View
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
        List<RectF> list = splitImage.generateSplitRectangle(1024, 1024, 2, 2);
        Log.e("AndroidRuntime", new GsonBuilder().disableHtmlEscaping().create().toJson(list));
    }
}