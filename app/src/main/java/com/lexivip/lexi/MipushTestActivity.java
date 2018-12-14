package com.lexivip.lexi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.basemodule.tools.LogUtil;
import com.lexivip.lexi.brandHouse.BrandHouseActivity;
import com.lexivip.lexi.discoverLifeAesthetics.ShowWindowDetailActivity;
import com.lexivip.lexi.lifeShop.TransactionOrderActivity;
import com.lexivip.lexi.orderList.LogisticsActivity;
import com.lexivip.lexi.orderList.OrderListActivity;
import com.lexivip.lexi.user.login.UserProfileUtil;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.UmengNotifyClickActivity;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;

import java.util.Map;

/**
 * umeng推送，小米、华为、魅族配置
 */
public class MipushTestActivity extends UmengNotifyClickActivity {
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LogUtil.e("啦啦啦啦大的所发生的");
        setContentView(R.layout.activity_mipush);
    }

    @Override
    public void onMessage(final Intent intent) {
        super.onMessage(intent);
        final String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        LogUtil.e("华为的body："+body);
        if (!TextUtils.isEmpty(body)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MiPushTestBean msg=JsonUtil.fromJson(body,MiPushTestBean.class);
                    Map<String, String> map=msg.extra;
                    switch (map.get("type")){
                        case "1":
                            Intent intent1=new Intent(MipushTestActivity.this,LogisticsActivity.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent1.putExtra("logistic_code",map.get("express_no"));
                            intent1.putExtra("kdn_company_code",map.get("express_code"));
                            intent1.putExtra("order_rid",map.get("order_rid"));
                            startActivity(intent1);
                            finish();
                            break;
                        case "2":
                            Intent intent2=new Intent(MipushTestActivity.this,BrandHouseActivity.class);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent2.putExtra("rid",map.get("store_rid"));
                            startActivity(intent2);
                            finish();
                            break;
                        case "3":
                            Intent intent3=new Intent(MipushTestActivity.this,ShowWindowDetailActivity.class);
                            intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent3.putExtra(ShowWindowDetailActivity.class.getSimpleName(),map.get("shop_window_id"));
                            startActivity(intent3);
                            finish();
                            break;
                        case "4":
                            Intent intent4=new Intent(MipushTestActivity.this,TransactionOrderActivity.class);
                            intent4.putExtra("rid",UserProfileUtil.storeId());
                            startActivity(intent4);
                            finish();
                            break;
                        case "5":
                            Intent intent5=new Intent(MipushTestActivity.this,MainActivity.class);
                            startActivity(intent5);
                            finish();
                    }
                }
            });
        }
    }
}
