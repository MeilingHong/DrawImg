package com.meiling.drawimg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private volatile boolean isFinish = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ImageView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void createImg(View v){
        //
        mHandler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}