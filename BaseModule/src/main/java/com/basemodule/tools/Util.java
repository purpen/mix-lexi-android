package com.basemodule.tools;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lilin on 2017/7/5.
 */

public class Util {
    /**
     * 检查手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^1((3[0-9])|(4[57])|(5[0-35-9])|(7[6780])|(8[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName() {
        try {
            PackageManager packageManager = BaseModuleContext.getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    BaseModuleContext.getContext().getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return BaseModuleContext.getContext().getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getColor(@ColorRes int color) {
        return ContextCompat.getColor(BaseModuleContext.getContext(), color);
    }


    public static String getString(@StringRes int resourceId) {
        return BaseModuleContext.getContext().getString(resourceId);
    }

    public static String[] getStringArray(@ArrayRes int resourceId) {
        return BaseModuleContext.getContext().getResources().getStringArray(resourceId);
    }

    /**
     * 箭头动画
     */
    public static void startViewRotateAnimation(View view, float startAngle, float endAngle) {
        RotateAnimation rotateAnimation = new RotateAnimation(startAngle, endAngle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setDuration(200);
        view.startAnimation(rotateAnimation);
    }

    /**
     * 获取指定大小的Drawable
     * @param imgId
     * @param width
     * @param height
     * @return
     */
    public static Drawable getDrawableWidthDimen(@DrawableRes int imgId, int width, int height) {
        Drawable drawable = ContextCompat.getDrawable(BaseModuleContext.getContext(), imgId);
        if (drawable != null) {
            drawable.setBounds(0, 0, DimenUtil.getDimensionPixelSize(width), DimenUtil.getDimensionPixelSize(height));
        }
        return drawable;
    }

    /**
     * 获取指定大小的Drawable
     * @param imgId
     * @return
     */
    public static Drawable getDrawableWidthPxDimen(@DrawableRes int imgId, int widthPx, int heightPx) {
        Drawable drawable = ContextCompat.getDrawable(BaseModuleContext.getContext(), imgId);
        if (drawable != null) {
            drawable.setBounds(0, 0, widthPx, heightPx);
        }
        return drawable;
    }

    /**
     * 获取指定大小的Drawable
     * @param imgId
     * @return
     */
    public static Drawable getDrawableWidthPxDimen(@DrawableRes int imgId, int size) {
        Drawable drawable = ContextCompat.getDrawable(BaseModuleContext.getContext(), imgId);
        if (drawable != null) {
            drawable.setBounds(0, 0, size, size);
        }
        return drawable;
    }
}
