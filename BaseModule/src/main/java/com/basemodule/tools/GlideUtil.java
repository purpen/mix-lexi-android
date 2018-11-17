package com.basemodule.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.lexivip.basemodule.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.concurrent.ExecutionException;

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


    public static <T> void loadingImage(T t, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Context context = imageView.getContext();
        if (context == null) return;
        Glide.with(context).asDrawable().load(t).apply(requestOptions).into(imageView);
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
                .format(DecodeFormat.PREFER_RGB_565)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(DEFAULT_ERROR_HOLDER)
                .placeholder(DEFAULT_PLACE_HOLDER);
        Context context = imageView.getContext();
        if (context == null) return;
        Glide.with(context).asDrawable().load(t).transition(DrawableTransitionOptions.withCrossFade()).apply(requestOptions).into(imageView);
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
                .format(DecodeFormat.PREFER_RGB_565)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(DEFAULT_ERROR_HOLDER)
                .placeholder(DEFAULT_PLACE_HOLDER);

        Context context = imageView.getContext();
        if (context == null) return;
        Glide.with(context).asDrawable().load(t).transition(DrawableTransitionOptions.withCrossFade()).apply(requestOptions).into(imageView);
    }

    /**
     * 使加载图片带有圆角，不设置占位图
     *
     * @param t
     * @param imageView
     * @param radius
     * @param <T>
     */
    public static <T> void loadImageWithRadiusNotPlace(T t, ImageView imageView, int radius) {
        RequestOptions requestOptions = bitmapTransform(new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.ALL));
        requestOptions.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL).error(DEFAULT_ERROR_HOLDER);
        Context context = imageView.getContext();
        if (context == null) return;
        Glide.with(context).load(t).transition(DrawableTransitionOptions.withCrossFade()).apply(requestOptions).into(imageView);
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
        MultiTransformation multi = new MultiTransformation(
                new CenterCrop(),
                new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.ALL));
        RequestOptions requestOptions = bitmapTransform(multi)
                .override(width, height)
                .dontAnimate()
                .skipMemoryCache(false)
                .format(DecodeFormat.PREFER_RGB_565)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(DEFAULT_ERROR_HOLDER)
                .placeholder(DEFAULT_PLACE_HOLDER);
        Context context = imageView.getContext();
        if (context == null) return;
        Glide.with(context).asDrawable().load(t).transition(DrawableTransitionOptions.withCrossFade()).apply(requestOptions).into(imageView);
    }

    /**
     * 根据网络图大小设置ImageView大小
     *
     * @param t
     * @param imageView
     * @param radius
     * @param <T>
     */
    public static <T> void loadImageAdjustImageViewDimen(final T t, final ImageView imageView, int radius, final int width, final int height) {

        RequestOptions requestOptions = bitmapTransform(new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.ALL))
                .override(width, height)
                .format(DecodeFormat.PREFER_RGB_565)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .error(DEFAULT_ERROR_HOLDER)
                .placeholder(DEFAULT_PLACE_HOLDER);

        Context context = imageView.getContext();
        if (context == null) return;
        Glide.with(context).asDrawable().load(t).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }


            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                int resourceW = resource.getIntrinsicWidth();
                int resourceH = resource.getIntrinsicHeight();
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                if (params == null) params = new ViewGroup.LayoutParams(width, height);
                int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                float scale = (float) vw / (float) resourceW;
                int vh = Math.round(resourceH * scale);
                params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                imageView.setLayoutParams(params);
                return false;
            }
        }
        ).transition(DrawableTransitionOptions.withCrossFade()).apply(requestOptions).into(imageView);

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
        RequestOptions requestOptions = new RequestOptions()
                .format(DecodeFormat.PREFER_RGB_565)
                .skipMemoryCache(true)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(DEFAULT_ERROR_HOLDER).placeholder(DEFAULT_PLACE_HOLDER);
        Context context = imageView.getContext();
        if (context == null) return;
        Glide.with(context).asDrawable().load(t).transition(DrawableTransitionOptions.withCrossFade()).apply(requestOptions).into(imageView);
    }

    /**
     * @param t
     * @param imageView
     * @param <T>
     */
    public static <T> void loadLongImage(T t, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .format(DecodeFormat.PREFER_RGB_565)
                .skipMemoryCache(true)
                .override(990, 14714)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(DEFAULT_ERROR_HOLDER).placeholder(DEFAULT_PLACE_HOLDER);
        Context context = imageView.getContext();
        if (context == null) return;
        Glide.with(context).asDrawable().load(t).transition(DrawableTransitionOptions.withCrossFade()).apply(requestOptions).into(imageView);
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
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .format(DecodeFormat.PREFER_RGB_565)
                .centerCrop()
                .error(DEFAULT_ERROR_HOLDER)
                .placeholder(DEFAULT_PLACE_HOLDER);
        Context context = imageView.getContext();
        if (context == null) return;
        Glide.with(context).asBitmap().apply(requestOptions).load(t).into(imageView);
    }

    /**
     * 使用软件画布 (software Canvas) 渲染硬件位图时，glide不能使用硬件位图
     * @param t
     * @param imageView
     * @param <T>
     */
    public static <T> void loadImageUpBitmap(T t, ImageView imageView){
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .format(DecodeFormat.PREFER_RGB_565)
                .centerCrop()
                .error(DEFAULT_ERROR_HOLDER)
                .placeholder(DEFAULT_PLACE_HOLDER)
                .disallowHardwareConfig();
        Context context = imageView.getContext();
        if (context == null) return;
        Glide.with(context).asBitmap().apply(requestOptions).load(t).into(imageView);
    }


    public static void resumeRequests(Context context) {
        if (context == null) return;
        Glide.with(context).resumeRequests();
    }

    public static void pauseRequests(Context context) {
        if (context == null) return;
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
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .format(DecodeFormat.PREFER_RGB_565)
                .override(width, height)
                .centerCrop()
                .error(DEFAULT_ERROR_HOLDER)
                .placeholder(DEFAULT_PLACE_HOLDER);
        Context context = imageView.getContext();
        if (context == null) return;
        Glide.with(context).asDrawable().load(t).apply(requestOptions).into(imageView);
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
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(size)
                .skipMemoryCache(true)
                .centerCrop()
                .format(DecodeFormat.PREFER_RGB_565)
                .error(DEFAULT_ERROR_HOLDER)
                .placeholder(DEFAULT_PLACE_HOLDER);
        Context context = imageView.getContext();
        if (context == null) return;
        Glide.with(context).asDrawable().load(t).apply(requestOptions).into(imageView);
    }

    public static <T> void loadCircleImageWidthDimen(@NotNull T t, @NotNull ImageView imageView, int size) {
        RequestOptions requestOptions = bitmapTransform(new CircleCrop()).diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(size)
                .skipMemoryCache(false)
                .format(DecodeFormat.PREFER_RGB_565)
                .error(DEFAULT_ERROR_HOLDER)
                .placeholder(DEFAULT_PLACE_HOLDER);
        Context context = imageView.getContext();
        if (context == null) return;
        Glide.with(context).asDrawable().load(t).apply(requestOptions).into(imageView);
    }

    /**
     * @param t
     * @param imageView
     * @param radius
     * @param dimen
     * @param <T>
     */
    public static <T> void loadImageWithBlurAndRadius(T t, @NotNull ImageView imageView, int radius, int dimen) {
        loadImageWithBlurAndRadius(t, imageView, radius, dimen, dimen);
    }

    /**
     * 毛玻璃效果
     *
     * @param t
     * @param imageView
     * @param <T>
     */
    public static <T> void loadImageWithBlurAndRadius(T t, @NotNull ImageView imageView, int radius, int width, int height) {
        MultiTransformation multi = new MultiTransformation(
                new CenterCrop(),
                new BlurTransformation(65),
                new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.ALL));

        RequestOptions requestOptions = bitmapTransform(multi)
                .override(width, height)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .format(DecodeFormat.PREFER_RGB_565)
                .error(DEFAULT_ERROR_HOLDER)
                .placeholder(DEFAULT_PLACE_HOLDER);
        Context context = imageView.getContext();
        if (context == null) return;
        Glide.with(context).asDrawable().load(t).apply(requestOptions).into(imageView);
    }


    /**
     * 下载图片
     *
     * @param imgUrl
     */
    public static File downLoadOriginalImage(String imgUrl, Context context) throws ExecutionException, InterruptedException {
        return Glide.with(context).asFile().load(imgUrl).submit().get();
    }


    public static RequestManager getInstance(Context context) {
        return Glide.with(context);
    }
}
