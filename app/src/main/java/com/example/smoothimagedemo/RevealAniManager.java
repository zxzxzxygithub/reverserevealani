package com.example.smoothimagedemo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import io.codetail.animation.ViewAnimationUtils;

/**
 * Created by zhengyongxiang on 2017/3/10.
 */

public class RevealAniManager {

    private boolean hasAnimationStarted;

    public void startCurcular(Context context, View aniView, final View topView, final View fakeView, final ViewGroup topPanel) {
        int cx = (aniView.getLeft() + aniView.getRight()) / 2;
        int cy = (aniView.getTop() + aniView.getBottom()) / 2;

        float finalRadius = context.getResources().getDisplayMetrics().widthPixels;
        Animator animator =
                ViewAnimationUtils.createCircularReveal(aniView, cx, cy, 100, finalRadius);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                topPanel.removeView(fakeView);
                topView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
        setHasAnimationStarted(true);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(topView, "alpha", 1f, 0f);
        objectAnimator.setDuration(100);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }

    public boolean isHasAnimationStarted() {
        return hasAnimationStarted;
    }

    public void setHasAnimationStarted(boolean hasAnimationStarted) {
        this.hasAnimationStarted = hasAnimationStarted;
    }
}
