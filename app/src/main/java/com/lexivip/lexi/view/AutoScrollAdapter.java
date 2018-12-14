package com.lexivip.lexi.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basemodule.tools.DimenUtil;
import com.basemodule.tools.GlideUtil;
import com.lexivip.lexi.R;
import com.lexivip.lexi.index.selection.HeadLineBean;

import java.util.List;

public class AutoScrollAdapter extends RecyclerView.Adapter<AutoScrollAdapter.BaseViewHolder> {
    private int dp16 = DimenUtil.dp2px(16.0);
    private final List<HeadLineBean.DataBean.HeadlinesBean> mData;
    private RecyclerView recyclerView;

    public AutoScrollAdapter(List<HeadLineBean.DataBean.HeadlinesBean> list, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.mData = list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_notice_item, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
//        if (position == ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition()) {
//            holder.linearLayout.setBackgroundResource(R.drawable.bg_colorccff5f9ce6_round);
//        } else {
//            holder.linearLayout.setBackgroundResource(R.drawable.bg_colorcc000000_round);
//        }
        HeadLineBean.DataBean.HeadlinesBean data = mData.get(position % mData.size());
        setNoticeTextViewData(data, holder.textView, holder.linearLayout);
        GlideUtil.loadCircleImageWidthDimen(data.avatar, holder.imageView, dp16);
    }


    /**
     * 设置通知
     */
    private void setNoticeTextViewData(HeadLineBean.DataBean.HeadlinesBean bean, TextView textView, LinearLayout linearLayout) {

        String content;
        switch (bean.event) {
            case 1:  //开通生活馆 人名蓝色
                content = bean.time + bean.time_info + "开了生活馆";
                textView.setText(content);
                break;
            case 2://售出3单成为正式馆主
                content = "售出" + bean.quantity + "单成为正式馆主";
                textView.setText(content);
                break;
            case 3://刚刚售出1单
//                content = "「" + bean.username + "」的生活馆" + bean.time + bean.time_info + " 售出" + bean.quantity + "单";
                content = bean.time + bean.time_info + " 售出" + bean.quantity + "单";
                textView.setText(content);
            case 4://售出单数
                content = bean.time + bean.time_info + "售出" + bean.quantity + "单";
                textView.setText(content);
        }
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    static class BaseViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        LinearLayout linearLayout;

        public BaseViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            linearLayout = itemView.findViewById(R.id.linearLayoutChild);
        }
    }
}
