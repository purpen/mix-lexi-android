package com.lexivip.lexi;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.basemodule.tools.DimenUtil;
import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.ScreenUtil;
import com.lexivip.lexi.index.bean.BannerImageBean;
import com.lexivip.lexi.view.autoScrollViewpager.RecyclingPagerAdapter;

import java.util.List;

public class OnePageTwoViewAdapter extends RecyclingPagerAdapter{
    //    当前页面
    private Activity context;
    private List<BannerImageBean> list;
    private int size;
    private boolean isInfiniteLoop;

    public OnePageTwoViewAdapter(Activity activity, ViewPager viewPager, List<BannerImageBean> urls) {
        this.context = activity;
        this.list = urls;
        this.size = (list == null ? 0 : list.size());
        isInfiniteLoop = false;
    }

    public OnePageTwoViewAdapter(Activity activity, ViewPager viewPager, List<BannerImageBean> urls, boolean isInfiniteLoop) {
        this.context = activity;
        this.list = urls;
        this.size = (list == null ? 0 : list.size());
        viewPager.setOffscreenPageLimit(size);
        this.isInfiniteLoop = isInfiniteLoop;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = holder.view = View.inflate(context, R.layout.adapter_one_page_three_item, null);
            holder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(R.id.glide_image_tag, holder);
        } else {
            holder = (ViewHolder) convertView.getTag(R.id.glide_image_tag);
        }

        position = getPosition(position);
        holder.view.setPadding(DimenUtil.dp2px(15.0), 0, 0, 0);
        int width = ScreenUtil.getScreenWidth() * 300 / 375;
        int height = width * 200 / 300;
        holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        GlideUtil.loadImageWithDimenAndRadius(list.get(position).image, holder.imageView, DimenUtil.dp2px(4), width, height, ImageSizeConfig.DEFAULT);
        final int finalPosition = position;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageUtil.banner2Page(list.get(finalPosition));
            }
        });
        return convertView;
    }


    private static class ViewHolder {
        View view;
        ImageView imageView;
    }

    @Override
    public float getPageWidth(int position) {
        return 315 / 375f;
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

    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }
}
