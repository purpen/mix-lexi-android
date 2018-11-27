package com.lexivip.lexi;
import android.support.v4.view.ViewPager;
import android.view.View;


public class OnePageThreeViewTransformer implements ViewPager.PageTransformer {
    private static final float min_scale = 0.75f;
    @Override
    public void transformPage(View page, float position) {
        float scaleFactor = Math.max(min_scale, 1 - Math.abs(position));
        page.setScaleX(scaleFactor);
        page.setScaleY(scaleFactor);
    }
}
