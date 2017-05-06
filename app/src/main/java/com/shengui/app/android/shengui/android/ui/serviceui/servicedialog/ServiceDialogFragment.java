package com.shengui.app.android.shengui.android.ui.serviceui.servicedialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui.SGHActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui.SGUActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/16.
 */

public class ServiceDialogFragment extends DialogFragment {

    @Bind(R.id.service_ask)
    ImageView serviceAsk;
    @Bind(R.id.service_see)
    ImageView serviceSee;
    @Bind(R.id.service_delete)
    ImageView serviceDelete;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.sguh_dialog, container);
        ButterKnife.bind(this, view);

        serviceAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SGHActivity.class));
            }
        });

        serviceSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SGUActivity.class));
            }
        });

        serviceDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
