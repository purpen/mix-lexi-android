package com.lexivip.lexi;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.lexivip.lexi.index.selection.applyForLifeHouse.OpenLifeHouseActivity;
import com.lexivip.lexi.net.WebUrl;
import com.lexivip.lexi.user.login.UserProfileUtil;
import com.smart.dialog.widget.base.BaseDialog;

public class InvitationDialog extends BaseDialog<InvitationDialog> {

    private View view;

    public InvitationDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        view = View.inflate(getContext(),R.layout.dialog_invitation,null);
        return view;
    }

    @Override
    public void setUiBeforShow() {
        Button button=view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent intent=new Intent(getContext(),OpenLifeHouseActivity.class);
                intent.putExtra("title",R.string.text_invitation_friend);
                intent.putExtra("url",WebUrl.INVITATION+UserProfileUtil.getUserId());
                getContext().startActivity(intent);
            }
        });
    }
}
