package com.lexivip.lexi.user.setting.address;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lexivip.lexi.R;
import com.lexivip.lexi.address.AddressActivity;
import com.lexivip.lexi.order.UserAddressListBean;
import com.lexivip.lexi.view.CustomHeadView;

public class AddressListActivity extends BaseActivity implements AddressListContract.View{

    private AdapterAddressList adapterList;
    private View footerView;
    private WaitingDialog dialog;
    private AddressListPresenter presenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_address_list;
    }

    @Override
    public void initView() {
        super.initView();
        dialog = new WaitingDialog(this);
        presenter = new AddressListPresenter(this);
        CustomHeadView customHeadView=findViewById(R.id.customHeadView);
        customHeadView.setHeadCenterTxtShow(true,R.string.text_order_address);
        SwipeRefreshLayout swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterList = new AdapterAddressList(R.layout.adapter_address_list,null);
        recyclerView.setAdapter(adapterList);
        footerView = View.inflate(this, R.layout.footer_add_new_address, null);
        adapterList.addFooterView(footerView);
        presenter.loadData();
    }

    @Override
    public void installListener() {
        super.installListener();
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AddressListActivity.this,AddressActivity.class),1);
            }
        });
        adapterList.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(AddressListActivity.this,AddressActivity.class);
                intent.putExtra("isNew", false);
                intent.putExtra(AddressActivity.class.getSimpleName(), adapterList.getItem(position).rid);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==1){
                if(data.getBooleanExtra("isRefresh",false)){
                    presenter.loadData();
                }

            }
        }
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
    public void showError(String error) {
        dialog.dismiss();
        ToastUtil.showError(error);
    }

    @Override
    public void getData(UserAddressListBean bean) {
        adapterList.setNewData(bean.data);
    }

    @Override
    public void setPresenter(AddressListContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
