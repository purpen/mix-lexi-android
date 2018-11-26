package com.lexivip.lexi.view.autoScrollViewpager;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.basemodule.tools.DimenUtil;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.CustomGridLayoutManager;
import com.lexivip.lexi.PageUtil;
import com.lexivip.lexi.R;
import com.lexivip.lexi.beans.ProductBean;
import com.lexivip.lexi.index.selection.HotPeopleGridSpaceDecoration;
import com.lexivip.lexi.index.selection.PeopleRecommendAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewPagerAdapter<T> extends RecyclingPagerAdapter {
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

    public RecyclerViewPagerAdapter(final Activity activity, List<T> list) {
        this.activity = activity;
        this.list = list;
        this.size = (list.size() % 5 == 0 ? list.size() / 5 : list.size() / 5 + 1);
        isInfiniteLoop = false;
    }

    public RecyclerViewPagerAdapter(final Activity activity, List<T> list, int imgW, int imageH) {
        this.activity = activity;
        this.list = list;
        this.size = (list.size() % 5 == 0 ? list.size() / 5 : list.size() / 5 + 1);
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
        RecyclerViewPagerAdapter.ViewHolder holder;
        if (view == null) {
            holder = new RecyclerViewPagerAdapter.ViewHolder();
            view = holder.recyclerView = new RecyclerView(container.getContext());
            view.setTag(R.id.glide_image_tag, holder);
        } else {
            holder = (RecyclerViewPagerAdapter.ViewHolder) view.getTag(R.id.glide_image_tag);
        }

        CustomGridLayoutManager manager = new CustomGridLayoutManager(AppApplication.getContext(), 6);
        manager.setScrollEnabled(false);
        holder.recyclerView.setLayoutManager(manager);
        holder.recyclerView.setPadding(DimenUtil.dp2px(15.0), 0, 0, 0);
        int position1 = getPosition(position);
        setHotRecommendData(list.subList(position1 * 5, 5 * (position1 + 1)), holder.recyclerView);
        return view;
    }

    /**
     * 设置人气推荐数据
     */
    private void setHotRecommendData(List<T> products, RecyclerView recyclerView) {
        final List<PeopleRecommendAdapter.MultipleItem> list = new ArrayList<>();
        int size = products.size();
        for (int i = 0; i < size; i++) {
            if (i == 0 || i == 1) {//占3列宽
                list.add(new PeopleRecommendAdapter.MultipleItem((ProductBean) products.get(i), PeopleRecommendAdapter.MultipleItem.ITEM_TYPE_SPAN2, PeopleRecommendAdapter.MultipleItem.ITEM_SPAN3_SIZE));
            } else {//占两列
                list.add(new PeopleRecommendAdapter.MultipleItem((ProductBean) products.get(i), PeopleRecommendAdapter.MultipleItem.ITEM_TYPE_SPAN3, PeopleRecommendAdapter.MultipleItem.ITEM_SPAN2_SIZE));
            }
        }

        PeopleRecommendAdapter adapter = new PeopleRecommendAdapter(list);

        adapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return list.get(position).getSpanSize();
            }
        });

        recyclerView.setAdapter(adapter);

        if (recyclerView.getItemDecorationCount() == 0)
            recyclerView.addItemDecoration(new HotPeopleGridSpaceDecoration(DimenUtil.getDimensionPixelSize(R.dimen.dp10)));

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PeopleRecommendAdapter.MultipleItem item = (PeopleRecommendAdapter.MultipleItem) adapter.getItem(position);
                if (item != null) {
                    PageUtil.jump2GoodsDetailActivity(item.getProduct().rid);
                }
            }
        });
    }

    private static class ViewHolder {
        RecyclerView recyclerView;
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
    public RecyclerViewPagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }
}
