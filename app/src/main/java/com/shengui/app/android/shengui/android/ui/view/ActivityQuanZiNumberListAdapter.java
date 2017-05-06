package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.view.adapter.BasePlatAdapter;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.CircleMemberDetail;
import com.shengui.app.android.shengui.models.LoginResultModel;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.models.QuanziList;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityQuanZiNumberListAdapter extends BasePlatAdapter<CircleMemberDetail> {
    Context mContext;
    QuanziList Circlem;
    public ActivityQuanZiNumberListAdapter(Context context, QuanziList model) {
        super(context);
        mContext=context;
        Circlem=model;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        final ViewHolder vh;
        final CircleMemberDetail model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_quanzi_member_list_item, null);
            vh.personInfoIv=(CircleImageView)itemView.findViewById(R.id.personInfoIv);
            vh.typeImage=(ImageView)itemView.findViewById(R.id.typeImage);
            vh.nameTextView=(TextView)itemView.findViewById(R.id.nameTextView);
            vh.confirmText=(TextView)itemView.findViewById(R.id.confirmText);
            vh.addressTextView=(TextView)itemView.findViewById(R.id.addressTextView);


            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }
        vh.confirmText.setText("通过");
        Glide.with(mContext).load(model.getAvatar()).asBitmap().placeholder(R.drawable.default_image).into( vh.personInfoIv);
        vh.nameTextView.setText(model.getName());
        vh.addressTextView.setText(model.getLocation());
        if(model.getSex()!=null&&model.getSex().equals("0")){
            vh.typeImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.women));
        }else{
            vh.typeImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.male));
        }

        vh.confirmText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                        GuiMiController.getInstance().CircleAddMemberList(new ViewNetCallBack() {
                            @Override
                            public void onConnectStart() {

                            }

                            @Override
                            public void onConnectEnd() {

                            }

                            @Override
                            public void onFail(Exception e) {

                            }

                            @Override
                            public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
                                try {
                                    JSONObject object = new JSONObject(o.toString());
                                    if (object.getBoolean("status")) {
                                        ToastTool.show("审核成功");
                                        vh.confirmText.setText("通过审核");
                                    } else {
                                        ToastTool.show(object.getString("message"));
                                    }
                                } catch (Exception e) {
                                    ToastTool.show("审核失败");
                                }
                            }
                        },Circlem.getId(), model.getId(), UserPreference.getTOKEN());
                    }else{
                        ToastTool.show("您还没有登录");
                    }
                }catch (Exception e){
                    Logger.e("exception"+e.getMessage());
                }
            }
        });

        return itemView;
    }
    class ViewHolder {
        CircleImageView personInfoIv;
        ImageView typeImage;
        TextView confirmText,nameTextView,addressTextView;
    }
}
