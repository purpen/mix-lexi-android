package com.thn.lexi;

import android.app.Application;
import android.content.Intent;

import com.basemodule.tools.ToastUtil;
import com.thn.lexi.beans.ProductBean;
import com.thn.lexi.brandHouse.BrandHouseActivity;
import com.thn.lexi.index.bean.BannerImageBean;
import com.thn.lexi.index.detail.GoodsDetailActivity;

/**
 * 界面跳转工具类
 */
public class PageUtil {

    public static void banner2Page(BannerImageBean bean) {
        Application context = AppApplication.getContext();
        Intent intent;
        switch (bean.type) {
            case "1": //链接
                ToastUtil.showInfo("链接");
                break;
            case "2": //商品详情
                jump2GoodsDetailActivity(bean.link);
                break;
            case "3": //分类
                ToastUtil.showInfo("分类");
                break;
            case "4": //品牌馆
                intent = new Intent(context, BrandHouseActivity.class);
                intent.putExtra("rid", bean.link);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            case "5": //专题
                ToastUtil.showInfo("专题");
                break;

            case "6"://生活志
                ToastUtil.showInfo("生活志");
                break;

            case "7": //生活志种草清单
                ToastUtil.showInfo("种草清单");
                break;
        }
    }

    /**
     * 商品rid
     * @param rid
     */
    public static void jump2GoodsDetailActivity(String rid){
        Intent intent = new Intent(AppApplication.getContext(), GoodsDetailActivity.class);
        ProductBean productBean = new ProductBean();
        productBean.rid = rid;
        intent.putExtra(GoodsDetailActivity.class.getSimpleName(), productBean);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppApplication.getContext().startActivity(intent);
    }
}
