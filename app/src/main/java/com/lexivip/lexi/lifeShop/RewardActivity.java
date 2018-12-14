package com.lexivip.lexi.lifeShop;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.basemodule.tools.Util;
import com.basemodule.ui.BaseActivity;
import com.lexivip.lexi.R;
import com.lexivip.lexi.view.CustomHeadView;

public class RewardActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_reward;
    private TextView tv_already_cash;
    private TextView tv_loading_cash;
    private TextView tv_cash;
    private AdapterReward adapterReward;

    @Override
    protected int getLayout() {
        return R.layout.activity_reward;
    }

    @Override
    public void initView() {
        super.initView();
        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true,R.string.text_my_reward);
        customHeadView.setRightTxt(getString(R.string.text_cash_record),Util.getColor(R.color.color_666));
        TextView right=customHeadView.getHeadRightTV();
        right.setOnClickListener(this);
        ImageView iv_money_show=findViewById(R.id.iv_money_show);
        iv_money_show.setOnClickListener(this);
        tv_reward = findViewById(R.id.tv_reward);
        tv_already_cash = findViewById(R.id.tv_already_cash);
        tv_loading_cash = findViewById(R.id.tv_loading_cash);
        ImageView iv_problem=findViewById(R.id.iv_problem);
        iv_problem.setOnClickListener(this);
        tv_cash = findViewById(R.id.tv_cash);
        Button button=findViewById(R.id.button);
        button.setOnClickListener(this);
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterReward = new AdapterReward(R.layout.item_reward);
        recyclerView.setAdapter(adapterReward);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                break;
            case R.id.iv_money_show:
                break;
            case R.id.iv_problem:
                break;
            case R.id.tv_head_right:
                break;
        }
    }
}
