package com.thn.lexi.orderList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.basemodule.tools.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.thn.lexi.R;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdapterLogistics extends BaseQuickAdapter<LogisticsBean.DataBean.TracesBean,BaseViewHolder> {
    private Context context;
    private Intent intent;
    private int count;

    public AdapterLogistics(int layoutResId, @Nullable List<LogisticsBean.DataBean.TracesBean> data,int count) {
        super(layoutResId, data);
        this.count=count;
    }

    @Override
    protected void convert(final BaseViewHolder helper, LogisticsBean.DataBean.TracesBean item) {
        TextView tv_acceptStation=helper.getView(R.id.tv_acceptStation);
        if (item.getAcceptStation().contains("签收")){
            tv_acceptStation.setTextColor(Util.getColor(R.color.color_555));
        }else{
            tv_acceptStation.setTextColor(Util.getColor(R.color.color_555));
        }
        final String express=item.getAcceptStation();
        SpannableString spanableInfo=new SpannableString(express);
        Pattern pattern = Pattern.compile("(\\(86\\))?\\d{13}");
        final Matcher matcher = pattern.matcher(item.getAcceptStation());
        if (matcher.find()) {
            spanableInfo.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    //拨打电话
                    intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + express.substring(matcher.start(), matcher.end())));
                    context.startActivity(intent);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置文本的颜色
                    ds.setColor(Util.getColor(R.color.color_6ed7af));
                    //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
                    ds.setUnderlineText(false);
                }
            }, matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv_acceptStation.setText(spanableInfo);
        tv_acceptStation.setMovementMethod(LinkMovementMethod.getInstance());

        helper.setText(R.id.tv_acceptTime,item.getAcceptTime());
        if (0==helper.getAdapterPosition()){
            helper.setVisible(R.id.line_top,false);
            helper.setVisible(R.id.line_bottom,true);
            helper.setBackgroundColor(R.id.line_bottom,Util.getColor(R.color.color_6ed7af));
            helper.setBackgroundRes(R.id.dot,R.drawable.bg_dot_color5fe4b1);
        }else if(1==helper.getAdapterPosition()){
            helper.setVisible(R.id.line_top,true);
            helper.setBackgroundColor(R.id.line_top,Util.getColor(R.color.color_6ed7af));
            helper.setVisible(R.id.line_bottom,true);
            helper.setBackgroundColor(R.id.line_bottom,Util.getColor(R.color.color_e9e9e9));
            helper.setBackgroundRes(R.id.dot,R.drawable.bg_dot_colordadada);
        }else if ((count-1)==helper.getAdapterPosition()){
            helper.setVisible(R.id.line_top,true);
            helper.setBackgroundColor(R.id.line_top,Util.getColor(R.color.color_e9e9e9));
            helper.setVisible(R.id.line_bottom,false);
            helper.setBackgroundRes(R.id.dot,R.drawable.bg_dot_colordadada);
        }else {
            helper.setVisible(R.id.line_top,true);
            helper.setBackgroundColor(R.id.line_top,Util.getColor(R.color.color_e9e9e9));
            helper.setVisible(R.id.line_bottom,true);
            helper.setBackgroundColor(R.id.line_bottom,Util.getColor(R.color.color_e9e9e9));
            helper.setBackgroundRes(R.id.dot,R.drawable.bg_dot_colordadada);
        }
    }
}
