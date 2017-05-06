package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.DateTools;
import com.base.view.adapter.BasePlatAdapter;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.FavoriteModel;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.utils.android.DateUtil;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityMineCollecttionListAdapter extends BasePlatAdapter<FavoriteModel> {
    Context mContext;
    boolean isShow=false;
    public ActivityMineCollecttionListAdapter(Context context) {
        super(context);
        mContext=context;

    }
    public void SetShow(boolean is){
        isShow=is;
    }

    @Override
    public View getView(final int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        final FavoriteModel model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_mine_collection_item, null);

            vh.detailTv=(TextView)itemView.findViewById(R.id.detailTv);
            vh.timeTv=(TextView)itemView.findViewById(R.id.timeTv);
            vh.nameTv=(TextView)itemView.findViewById(R.id.nameTv);
            vh.deleteTextView=(TextView)itemView.findViewById(R.id.deleteTextView);
            vh.personInfoIv=(CircleImageView)itemView.findViewById(R.id.personInfoIv);
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }

        if(isShow){
            vh.deleteTextView.setVisibility(View.VISIBLE);
        }else{
            vh.deleteTextView.setVisibility(View.GONE);
        }

        if(model.getFav_type().equals("1")){
            vh.nameTv.setText(model.getTitle());
            vh.detailTv.setText(model.getContents());
            Glide.with(mContext).load(model.getCover()).asBitmap().placeholder(R.drawable.default_pictures).into( vh.personInfoIv);
        }else{
            vh.nameTv.setText(model.getContent());
            vh.detailTv.setText(model.getContents());
            Glide.with(mContext).load(model.getCircle_detail().getAvatar()).asBitmap().placeholder(R.drawable.default_pictures).into( vh.personInfoIv);
        }

        vh.timeTv.setText(DateTools.getStrTime(model.getCreate_time()));

        vh.deleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type="";
                if(model.getFav_type()!=null&&model.getFav_type().equals("1")){
                    type="supply";
                }else{
                    type="post";
                }

                GuiMiController.getInstance().Favoritedel(new ViewNetCallBack() {
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
                                ToastTool.show("删除收藏成功");
                                getItems().remove(position);
                                notifyDataSetChanged();
                            } else {
                                ToastTool.show(object.getString("message"));
                            }

                        } catch (Exception e) {
                            ToastTool.show("删除收藏失败");
                        }

                    }
                }, UserPreference.getTOKEN(), model.getId(), type);


                listener.Remove(position,v);
            }
        });
        return itemView;
    }

    public interface  RemoveListener{
        void Remove(int position ,View v );

    }
    RemoveListener listener;
    public void SetRemoveListener(RemoveListener Relistener){
        listener=Relistener;
    }

    class ViewHolder {
        TextView deleteTextView,nameTv,timeTv,detailTv;
        CircleImageView personInfoIv;
    }
}
