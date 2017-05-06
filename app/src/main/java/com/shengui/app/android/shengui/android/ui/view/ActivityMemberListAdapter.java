package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.CircleMemberDetail;
import com.shengui.app.android.shengui.models.LoginResultModel;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityMemberListAdapter extends BasePlatAdapter<CircleMemberDetail> {
    Context mContext;
    public ActivityMemberListAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        final ViewHolder vh;
        final CircleMemberDetail model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_member_item, null);
            vh.personInfoIv=(CircleImageView)itemView.findViewById(R.id.personInfoIv);
            vh.typeImage=(ImageView)itemView.findViewById(R.id.typeImage);
            vh.nameTextView=(TextView)itemView.findViewById(R.id.nameTextView);
            vh.confirmText=(TextView)itemView.findViewById(R.id.confirmText);
            vh.addressTextView=(TextView)itemView.findViewById(R.id.addressTextView);


            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }


        try{
            Glide.with(mContext).load(model.getAvatar()).asBitmap().placeholder(R.drawable.default_image).into( vh.personInfoIv);
            vh.nameTextView.setText(model.getName());
            vh.addressTextView.setText(model.getLocation());
            if(model.getSex()!=null&&model.getSex().equals("0")){
                vh.typeImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.women));
            }else{
                vh.typeImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.male));
            }
            if(model.getIs_attention()!=null&&model.getIs_attention().equals("1"))
            {
                vh.confirmText.setText("已关注");
                vh.confirmText.setTextColor(mContext.getResources().getColor(R.color.shenguiappcolor));
                vh.confirmText.setBackground(mContext.getResources().getDrawable(R.drawable.activity_quanzi_un_confirm));

            }else{
                vh.confirmText.setText("关注");
                vh.confirmText.setTextColor(mContext.getResources().getColor(R.color.white));
                vh.confirmText.setBackground(mContext.getResources().getDrawable(R.drawable.activity_quanzi_confirm));
            }

            vh.confirmText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                        if(vh.confirmText.getText().equals("关注")){
//                        focusText.setText("取消关注");
                            MineInfoController.getInstance().Attentionadd(new ViewNetCallBack() {
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
//                                            ToastTool.show("关注成功");
                                            vh.confirmText.setText("已关注");
                                            model.setIs_attention("1");
                                            notifyDataSetChanged();
                                            vh.confirmText.setTextColor(mContext.getResources().getColor(R.color.shenguiappcolor));
                                            vh.confirmText.setBackground(mContext.getResources().getDrawable(R.drawable.activity_quanzi_un_confirm));
                                        } else {
                                            ToastTool.show(object.getString("message"));
                                        }

                                    } catch (Exception e) {
                                        ToastTool.show("关注失败");
                                    }
                                }

                            }, model.getId(), UserPreference.getTOKEN());
                        }else{
//                        focusText.setText("关注");
                            MineInfoController.getInstance().Attentiondel(new ViewNetCallBack() {
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
//                                            ToastTool.show("取消关成功");
                                            vh.confirmText.setText("关注");

                                            model.setIs_attention("0");
                                            vh.confirmText.setTextColor(mContext.getResources().getColor(R.color.white));
                                            vh.confirmText.setBackground(mContext.getResources().getDrawable(R.drawable.activity_quanzi_confirm));
                                        } else {
                                            ToastTool.show(object.getString("message"));
                                        }

                                    } catch (Exception e) {
                                        ToastTool.show("取消关注失败");
                                    }
                                }
                            },model.getId(), UserPreference.getTOKEN());
                        }
                    }else{
                        IntentTools.startLogin(mContext);
                    }
                }
            });
        }catch (Exception e){
            Logger.e("exceptioons"+e.getMessage());
        }


        return itemView;
    }
    class ViewHolder {
        CircleImageView personInfoIv;
        ImageView typeImage;
        TextView confirmText,nameTextView,addressTextView;
    }
}
