package com.lexivip.lexi;

import android.app.Application;
import android.content.Intent;

import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.lexivip.lexi.beans.LifeWillBean;
import com.lexivip.lexi.beans.ProductBean;
import com.lexivip.lexi.brandHouse.BrandHouseActivity;
import com.lexivip.lexi.index.bean.BannerImageBean;
import com.lexivip.lexi.index.detail.GoodsDetailActivity;
import com.lexivip.lexi.index.discover.ArticleDetailActivity;

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
                LifeWillBean willBean = new LifeWillBean();
                willBean.rid = bean.link;
                willBean.channel_name = Util.getString(R.string.text_life_records);
                jump2ArticleDetailActivity(willBean);
                break;

            case "7": //生活志种草清单
                LifeWillBean seedingBean = new LifeWillBean();
                seedingBean.rid = bean.link;
                seedingBean.channel_name = Util.getString(R.string.text_seeding_note);
                jump2ArticleDetailActivity(seedingBean);
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

    /**
     * 商品rid
     * @param item
     */
    public static void jump2ArticleDetailActivity(LifeWillBean item){
        Intent intent = new Intent(AppApplication.getContext(), ArticleDetailActivity.class);
        intent.putExtra(ArticleDetailActivity.class.getSimpleName(),item.rid);
        intent.putExtra(ArticleDetailActivity.class.getName(),item.channel_name);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppApplication.getContext().startActivity(intent);
    }

    /**
     * 跳转品牌馆主页
     * @param rid 品牌馆id
     */
    public static void jump2BrandPavilionActivity(String rid){
        Intent intent = new Intent(AppApplication.getContext(), BrandHouseActivity.class);
        intent.putExtra("rid",rid);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppApplication.getContext().startActivity(intent);
    }
}