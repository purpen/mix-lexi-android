package com.basemodule.tools;
import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
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
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL).error(DEFAULT_ERROR_HOLDER).placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(BaseModuleContext.getContext()).load(t).apply(requestOptions).into(imageView);
    }


    /**
     * 使加载图片带有上圆角
     * @param t
     * @param imageView
     * @param radius
     * @param <T>
     */
    public static <T> void loadImageWithTopRadius(T t, ImageView imageView, int radius) {
        RequestOptions requestOptions = bitmapTransform(new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.TOP));
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL).error(DEFAULT_ERROR_HOLDER).placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(BaseModuleContext.getContext()).load(t).transition(DrawableTransitionOptions.withCrossFade()).apply(requestOptions).into(imageView);
    }

    /**
     * 使加载图片带有圆角
     * @param t
     * @param imageView
     * @param radius
     * @param <T>
     */
    public static <T> void loadImageWithRadius(T t, ImageView imageView, int radius) {
        RequestOptions requestOptions = bitmapTransform(new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.ALL));
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL).error(DEFAULT_ERROR_HOLDER).placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(BaseModuleContext.getContext()).load(t).transition(DrawableTransitionOptions.withCrossFade()).apply(requestOptions).into(imageView);
    }



    /**
     * @param t
     * @param imageView
     * @param <T>
     */
    public static <T> void loadImageWithFading(T t, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).error(DEFAULT_ERROR_HOLDER).placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(BaseModuleContext.getContext()).load(t).transition(DrawableTransitionOptions.withCrossFade()).apply(requestOptions).into(imageView);
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
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL).error(DEFAULT_ERROR_HOLDER).placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(BaseModuleContext.getContext()).asBitmap().apply(requestOptions).load(t).into(imageView);
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
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL).override(width, height).error(DEFAULT_ERROR_HOLDER).placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(BaseModuleContext.getContext()).load(t).apply(requestOptions).into(imageView);
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
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL).override(size).error(DEFAULT_ERROR_HOLDER).placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(BaseModuleContext.getContext()).load(t).apply(requestOptions).into(imageView);
    }

    public static <T> void loadCircleImageWidthDimen(@NotNull T t, @Nullable ImageView imageView,int size) {
        RequestOptions requestOptions = bitmapTransform(new CircleCrop());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL).override(size).error(DEFAULT_ERROR_HOLDER).placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(BaseModuleContext.getContext()).load(t).apply(requestOptions).into(imageView);
    }

    /**
     *  毛玻璃效果
     * @param t
     * @param imageView
     * @param <T>
     */
    public static <T> void loadImageWithBlurAndRadius(T t, @Nullable ImageView imageView,int radius) {
        MultiTransformation multi = new MultiTransformation(
                new BlurTransformation(65, 3),
                new RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.ALL));

        RequestOptions requestOptions = bitmapTransform(multi);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL).error(DEFAULT_ERROR_HOLDER).placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(BaseModuleContext.getContext()).load(t).apply(requestOptions).into(imageView);
    }
}
