package com.lexivip.lexi.view.autoScrollViewpager;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.WaitingDialog;
import com.lexivip.lexi.ImageSizeConfig;
import com.lexivip.lexi.MainActivity;
import com.lexivip.lexi.R;
import com.lexivip.lexi.user.login.UserProfileUtil;
import com.lexivip.lexi.welcome.WelcomeActivity;

import java.util.List;

public class ViewPagerAdapter<T> extends RecyclingPagerAdapter {
    private final String TAG = getClass().getSimpleName();
    private Activity activity;
    private List<T> list;
    private int size;
    private boolean isInfiniteLoop;
    private String code;
    private WaitingDialog dialog;
    private int imgW;
    private int imgH;

    public int getSize() {
        return size;
    }

    public ViewPagerAdapter(final Activity activity, List<T> list) {
        this.activity = activity;
        this.list = list;
        this.size = (list == null ? 0 : list.size());
        isInfiniteLoop = false;
    }

    public ViewPagerAdapter(final Activity activity, List<T> list, int imgW, int imageH) {
        this.activity = activity;
        this.list = list;
        this.size = (list == null ? 0 : list.size());
        this.imgH = imageH;
        this.imgW = imgW;
        isInfiniteLoop = false;
    }

    @Override
    public int getCount() {
        if (size == 0) {
            return 0;
        }

        if (size == 1) {
            return 1;
        }
        return isInfiniteLoop ? Integer.MAX_VALUE : size;
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new ImageView(container.getContext());
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setTag(R.id.glide_image_tag,holder);
        } else {
            holder = (ViewHolder) view.getTag(R.id.glide_image_tag);
        }
        final T content = list.get(getPosition(position));

        if (content instanceof Integer) {
            holder.imageView.setImageResource((Integer) content);
        }

        if (content instanceof String) {
            if (imgW==0 || imgH==0){
                GlideUtil.loadImageWithFading(content, holder.imageView);
            }else {
                GlideUtil.loadImageWithDimen(content, holder.imageView,imgW,imgH,ImageSizeConfig.DEFAULT);
            }
        }

        if (position == size -1){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (UserProfileUtil.isLogin()) { //登录过且通过更新过来的
                        activity.startActivity(new Intent(activity, MainActivity.class));
                    } else {
                        activity.startActivity(new Intent(activity, WelcomeActivity.class));
                    }
                    activity.finish();
                }
            });
        }
//        if (activity instanceof UserGuideActivity) {
//            if (position == size - 1) {
//                view_selection_goods_center_recommend.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (TextUtils.isEmpty(UserGuideActivity.fromPage)) {
//                            String token = SPUtil.read(Constants.AUTHORIZATION);
//                            if (TextUtils.isEmpty(token)) {
//                                activity.startActivity(new Intent(activity, LoginRegisterActivity.class));
//                            } else {
//                                activity.startActivity(new Intent(activity, MainActivity.class));
//                            }
//                            activity.finish();
//                        } else {
//                            UserGuideActivity.fromPage = null;
//                            activity.finish();
//                        }
//                    }
//                });
//            }
//        }

//        if (activity instanceof MainActivity) {
//            view_selection_goods_center_recommend.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onTopBarClick(View v) {
//                    final BannerBean.RowsBean banner = (BannerBean.RowsBean) content;
//                    GoToNextUtils.goToIntent(activity, Integer.valueOf(banner.type), banner.web_url);
//
//                }
//            });
//        }
//        if (activity instanceof DetailsActivity) {
//            view_selection_goods_center_recommend.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onTopBarClick(View v) {
//
//                    // TODO: 2017/11/21 无须点击操作
////                    MainApplication.picList = (List<String>) list;
////                    Intent intent = new Intent(activity, BannerActivity.class);
////                    activity.startActivity(intent);
//                }
//            });
//        }

        return view;
    }

    private static class ViewHolder {
        ImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ViewPagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }
}