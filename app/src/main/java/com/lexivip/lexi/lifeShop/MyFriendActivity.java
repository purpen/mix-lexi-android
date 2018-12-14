package com.lexivip.lexi.lifeShop;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lexivip.lexi.PageUtil;
import com.lexivip.lexi.R;
import com.lexivip.lexi.view.CustomHeadView;

public class MyFriendActivity extends BaseActivity implements MyFriendContract.View{

    private AdapterMyFriend adapterMyFriend;
    private MyFriendPresenter presenter;
    private WaitingDialog dialog;
    private int page=1;


    @Override
    protected int getLayout() {
        return R.layout.activity_myfriend;
    }

    @Override
    public void initView() {
        super.initView();
        dialog = new WaitingDialog(this);
        presenter = new MyFriendPresenter(this);
        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true,R.string.text_my_friend);
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterMyFriend = new AdapterMyFriend(R.layout.item_myfriend);
        presenter.loadData(page);
        adapterMyFriend.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PageUtil.jump2OtherUserCenterActivity(adapterMyFriend.getData().get(position).user_sn);
            }
        });
        recyclerView.setAdapter(adapterMyFriend);
    }

    @Override
    public void showLoadingView() {
        dialog.show();
    }

    @Override
    public void dismissLoadingView() {
        dialog.dismiss();
    }

    @Override
    public void loadMoreEnd() {
        adapterMyFriend.loadMoreEnd();
    }

    @Override
    public void loadMoreComplete() {
        adapterMyFriend.loadMoreComplete();
    }

    @Override
    public void loadMoreFail() {
        adapterMyFriend.loadMoreFail();
    }

    @Override
    public void showError(String error) {
        if (dialog!=null){
            dismissLoadingView();
        }
        ToastUtil.showError(error);
    }

    @Override
    public void setData(MyFriendBean bean) {
        if (page==1){
            adapterMyFriend.setNewData(bean.data.friends);
        }else {
            adapterMyFriend.addData(bean.data.friends);
        }
    }

    @Override
    public void setPresenter(MyFriendContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
