package com.example.smoothimagedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import io.codetail.widget.RevealFrameLayout;

public class RevealActivity extends AppCompatActivity {

    private View myView;
    private View topView;
    private RevealFrameLayout topPanel;
    private ImageView fakeView;
    private RevealAniManager revealAniManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal);
        myView = findViewById(R.id.ivmodel3);
        topView = findViewById(R.id.totalview2);
        topPanel = (RevealFrameLayout) findViewById(R.id.topPanel);
        fakeView = new ImageView(this);
        fakeView.setScaleType(ImageView.ScaleType.FIT_XY);
        RevealFrameLayout.LayoutParams layoutParams = new RevealFrameLayout.LayoutParams(RevealFrameLayout.LayoutParams.MATCH_PARENT,
                RevealFrameLayout.LayoutParams.MATCH_PARENT);
        fakeView.setImageResource(R.drawable.temp);
        topPanel.addView(fakeView, 0,layoutParams);
        revealAniManager = new RevealAniManager();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        //控制动画显示一次
        if (hasFocus && !revealAniManager.isHasAnimationStarted()) {
            revealAniManager.startCurcular(this,myView,topView, fakeView,topPanel);
        }
    }
}
