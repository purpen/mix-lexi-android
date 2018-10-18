package com.basemodule.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.lexivip.basemodule.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * 图片加载工具类
 */
public class GlideUtil {
    //默认占位图
    public static final int DEFAULT_PLACE_HOLDER = R.drawable.default_load;
    public static final int DEFAULT_ERROR_HOLDER = R.drawable.default_load;
    //The duration of the cross fade animation in milliseconds.
    public static final int FADING_DURING = 300;


    public static <T> void loadImage(T t, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(imageView.getContext()).load(t).apply(requestOptions).into(imageView);
    }


    /**
     * 使加载图片带有上圆角
     *
     * @param t
     * @param imageView
     * @param radius
     * @param <T>
     */
    public static <T> void loadImageWithTopRadius(T t, ImageView imageView, int radius) {
        RequestOptions requestOptions = bitmapTransform(new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.TOP))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(DEFAULT_ERROR_HOLDER)
                .placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(imageView.getContext()).load(t).transition(DrawableTransitionOptions.withCrossFade()).apply(requestOptions).into(imageView);
    }

    /**
     * 使加载图片带有圆角
     *
     * @param t
     * @param imageView
     * @param radius
     * @param <T>
     */
    public static <T> void loadImageWithRadius(T t, ImageView imageView, int radius) {
        RequestOptions requestOptions = bitmapTransform(new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.ALL))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(DEFAULT_ERROR_HOLDER)
                .placeholder(DEFAULT_PLACE_HOLDER);

        Glide.with(imageView.getContext()).load(t).transition(DrawableTransitionOptions.withCrossFade()).apply(requestOptions).into(imageView);
    }

    /**
     * 使加载图片带有圆角
     *
     * @param t
     * @param imageView
     * @param radius
     * @param <T>
     */
    public static <T> void loadImageWithDimenAndRadius(T t, final ImageView imageView, int radius, final int width, final int height) {
        RequestOptions requestOptions = bitmapTransform(new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.ALL))
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .dontAnimate()
                .error(DEFAULT_ERROR_HOLDER)
                .placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(imageView.getContext()).load(t).transition(DrawableTransitionOptions.withCrossFade()).apply(requestOptions).into(imageView);
    }

    /**
     * 根据网络图大小设置ImageView大小
     * @param t
     * @param imageView
     * @param radius
     * @param <T>
     */
    public static <T> void loadImageAdjustImageViewDimen(T t, final ImageView imageView, int radius, final int width, final int height) {
        if (imageView==null) throw new IllegalArgumentException("imageView must not null");
        RequestOptions requestOptions = bitmapTransform(new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.ALL))
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .format(DecodeFormat.PREFER_RGB_565)
                .disallowHardwareConfig()
                .dontAnimate()
                .error(DEFAULT_ERROR_HOLDER)
                .placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(imageView.getContext()).load(t).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@android.support.annotation.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                if (params==null) params =new ViewGroup.LayoutParams(width,height);
                int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                float scale = (float) vw / (float) resource.getIntrinsicWidth();
                int vh = Math.round(resource.getIntrinsicHeight() * scale);
                params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                imageView.setLayoutParams(params);
                return false;
            }
        }).transition(DrawableTransitionOptions.withCrossFade()).apply(requestOptions).into(imageView);
    }

    /**
     * 使加载图片带有圆角
     *
     * @param t
     * @param imageView
     * @param radius
     * @param <T>
     */
    public static <T> void loadImageWithDimenAndRadius(T t, ImageView imageView, int radius, int size) {
        loadImageWithDimenAndRadius(t, imageView, radius, size, size);
    }

    /**
     * @param t
     * @param imageView
     * @param <T>
     */
    public static <T> void loadImageWithFading(T t, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).error(DEFAULT_ERROR_HOLDER).placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(imageView.getContext()).load(t).transition(DrawableTransitionOptions.withCrossFade()).apply(requestOptions).into(imageView);
    }


    /**
     * By default you get a Drawable RequestBuilder
     * if you call asBitmap() you will get a Bitmap RequestBuilder
     *
     * @param t
     * @param imageView
     * @param <T>
     */
    public static <T> void loadImageAsBitmap(T t, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(DEFAULT_ERROR_HOLDER)
                .placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(imageView.getContext()).asBitmap().apply(requestOptions).load(t).into(imageView);
    }


    public static void resumeRequests(Context context) {
        Glide.with(context).resumeRequests();
    }

    public static void pauseRequests(Context context) {
        Glide.with(context).pauseRequests();
    }


    /**
     * 加载指定宽高图片
     *
     * @param t
     * @param imageView
     * @param width
     * @param height
     * @param <T>
     */
    public static <T> void loadImageWithDimen(T t, ImageView imageView, int width, int height) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .override(width, height)
                .error(DEFAULT_ERROR_HOLDER)
                .placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(imageView.getContext()).load(t).apply(requestOptions).into(imageView);
    }

    /**
     * 加载指定宽高同size图片
     *
     * @param t
     * @param imageView
     * @param size
     * @param <T>
     */
    public static <T> void loadImageWithDimen(T t, ImageView imageView, int size) {
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .override(size)
                .error(DEFAULT_ERROR_HOLDER)
                .placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(imageView.getContext()).load(t).apply(requestOptions).into(imageView);
    }

    public static <T> void loadCircleImageWidthDimen(@NotNull T t, @Nullable ImageView imageView, int size) {
        RequestOptions requestOptions = bitmapTransform(new CircleCrop()).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .override(size)
                .error(DEFAULT_ERROR_HOLDER)
                .placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(imageView.getContext()).load(t).apply(requestOptions).into(imageView);
    }

    /**
     * 毛玻璃效果
     *
     * @param t
     * @param imageView
     * @param <T>
     */
    public static <T> void loadImageWithBlurAndRadius(T t, @Nullable ImageView imageView, int radius) {
        MultiTransformation multi = new MultiTransformation(
                new BlurTransformation(65, 3),
                new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.ALL));

        RequestOptions requestOptions = bitmapTransform(multi)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(DEFAULT_ERROR_HOLDER)
                .placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(imageView.getContext()).load(t).apply(requestOptions).into(imageView);
    }
}
