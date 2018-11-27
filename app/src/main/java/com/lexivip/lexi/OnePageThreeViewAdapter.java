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

public class OnePageThreeViewAdapter extends RecyclingPagerAdapter implements ViewPager.OnPageChangeListener {
    //    当前页面
    private int currentPosition = 0;
    private Activity context;
    private ViewPager mViewPager;
    private List<BannerImageBean> list;


    public OnePageThreeViewAdapter(Activity activity, ViewPager viewPager, List<BannerImageBean> urls) {
        this.context = activity;
        this.list = urls;
        int size = urls.size();
        if (size > 1) {
//            添加最后一页到第一页
            list.add(0, urls.get(size - 1));
//            添加第一页(经过上行的添加已经是第二页了)到最后一页
            list.add(urls.get(1));
        }
        viewPager.setOffscreenPageLimit(list.size());
        mViewPager = viewPager;
        viewPager.setAdapter(this);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(1,false);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        ViewHolder holder;
        if (convertView==null){
            holder = new ViewHolder();
            convertView = holder.view = View.inflate(context, R.layout.adapter_one_page_three_item, null);
            holder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(R.id.glide_image_tag,holder);
        }else {
            holder = (ViewHolder) convertView.getTag(R.id.glide_image_tag);
        }

        holder.view.setTag(position);
        if (position == 0) {
            holder.view.setPadding(DimenUtil.dp2px(15.0), 0, 0, 0);
        }

        int width = ScreenUtil.getScreenWidth() * 300 / 375;
        int height = width * 200 / 300;
        holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        GlideUtil.loadImageWithDimenAndRadius(list.get(position).image, holder.imageView, DimenUtil.dp2px(4), width, height, ImageSizeConfig.DEFAULT);
        convertView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PageUtil.banner2Page(list.get(currentPosition));
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
        return 320 / 375f;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int size = list.size();
        if (currentPosition == 0) {
            mViewPager.setCurrentItem(size - 2, false);
        } else if (currentPosition == size - 1) {
//        若当前为倒数第一张，设置页面为第二张
            mViewPager.setCurrentItem(1, false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        for (int i = 0; i < list.size(); i++) {
            View viewWithTag = mViewPager.findViewWithTag(i);
            if (viewWithTag==null) return;
            if (i == position) {
                viewWithTag.setPadding(DimenUtil.dp2px(15.0), 0, 0, 0);
            } else {
                viewWithTag.setPadding(0, 0, 0, 0);
            }
        }

    }
}
