package com.lexivip.lexi.orderList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lexivip.lexi.R;
import com.lexivip.lexi.dialog.InquiryDialog;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdapterLogistics extends BaseQuickAdapter<LogisticsBean.DataBean.TracesBean,BaseViewHolder> {
    private Activity context;
    private Intent intent;
    private int count;
    private LayoutInflater inflater;

    public AdapterLogistics(int layoutResId, @Nullable List<LogisticsBean.DataBean.TracesBean> data,int count,Activity context) {
        super(layoutResId,data);
        inflater = LayoutInflater.from(context);
        this.count=count;
        this.context=context;
    }
    @Override
    protected void convert(BaseViewHolder helper, LogisticsBean.DataBean.TracesBean item) {
        if (item.getAcceptStation().contains("签收")){
            helper.setTextColor(R.id.tv_acceptStation,Util.getColor(R.color.color_555));
        }else{
            helper.setTextColor(R.id.tv_acceptStation,Util.getColor(R.color.color_999));
        }
        final String express=item.getAcceptStation();
        SpannableString spanableInfo=new SpannableString(express);
        Pattern pattern = Pattern.compile("\\d{11}");
        final Matcher matcher = pattern.matcher(item.getAcceptStation());
        if (matcher.find()) {
            spanableInfo.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    LogUtil.e("tel:" + express.substring(matcher.start(), matcher.end()));

                    InquiryDialog inquiryDialog = new InquiryDialog(context,express.substring(matcher.start(), matcher.end()),"取消","拨打", new InquiryDialog.InquiryInterface() {
                        @Override
                        public void getCheck(boolean isCheck) {
                            if (!isCheck) {
                                //拨打电话
                                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + express.substring(matcher.start(), matcher.end())));
                                context.startActivity(intent);
                            }
                        }
                    });
                    inquiryDialog.show();
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
        TextView tv_acceptStation=helper.getView(R.id.tv_acceptStation);
        tv_acceptStation.setText(spanableInfo);
        tv_acceptStation.setMovementMethod(LinkMovementMethod.getInstance());

        helper.setText(R.id.tv_acceptTime,item.getAcceptTime());
        LogUtil.e("dijige："+getLoadMoreViewPosition());
        LogUtil.e("sdfs:"+helper.getLayoutPosition());
        LogUtil.e("jige:"+helper.getAdapterPosition());
        if (0==helper.getAdapterPosition()){
            helper.setVisible(R.id.line_top,false);
            helper.setVisible(R.id.line_bottom,true);
            helper.setBackgroundColor(R.id.line_bottom,Util.getColor(R.color.color_6ed7af));
            helper.setBackgroundRes(R.id.dot,R.drawable.bg_dot_color5fe4b1);
            LogUtil.e("第一個");
        }else if(1==helper.getAdapterPosition()){
            helper.setVisible(R.id.line_top,true);
            helper.setBackgroundColor(R.id.line_top,Util.getColor(R.color.color_6ed7af));
            helper.setVisible(R.id.line_bottom,true);
            helper.setBackgroundColor(R.id.line_bottom,Util.getColor(R.color.color_e9e9e9));
            helper.setBackgroundRes(R.id.dot,R.drawable.bg_dot_colordadada);
            LogUtil.e("第二個");
        }else if ((getLoadMoreViewPosition()-1)==helper.getAdapterPosition()){
            helper.setVisible(R.id.line_top,true);
            helper.setBackgroundColor(R.id.line_top,Util.getColor(R.color.color_e9e9e9));
            helper.setVisible(R.id.line_bottom,false);
            helper.setBackgroundRes(R.id.dot,R.drawable.bg_dot_colordadada);
            LogUtil.e("最後一個");
        }else {
            helper.setVisible(R.id.line_top,true);
            helper.setBackgroundColor(R.id.line_top,Util.getColor(R.color.color_e9e9e9));
            helper.setVisible(R.id.line_bottom,true);
            helper.setBackgroundColor(R.id.line_bottom,Util.getColor(R.color.color_e9e9e9));
            helper.setBackgroundRes(R.id.dot,R.drawable.bg_dot_colordadada);
            LogUtil.e("普通的");
        }
    }
}
