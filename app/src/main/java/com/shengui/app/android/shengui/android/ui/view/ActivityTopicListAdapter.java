package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.view.adapter.BasePlatAdapter;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.models.TopicModel;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityTopicListAdapter extends BasePlatAdapter<TopicModel> {
    Context mContext;
    public ActivityTopicListAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        TopicModel model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_topic_list_item, null);
            vh.topicname=(TextView)itemView.findViewById(R.id.topicname);
            vh.numberText=(TextView)itemView.findViewById(R.id.numberText);
            vh.detailText=(TextView)itemView.findViewById(R.id.detailText);
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }
        vh.topicname.setText(model.getTitle());
        vh.numberText.setText("参与讨论："+model.getMember_num()+"人");
        if(model.getContent()==null){
            vh.detailText.setText("暂无描述");
        }else{
            vh.detailText.setText(model.getContent());
        }


        return itemView;
    }
    class ViewHolder {
        TextView topicname,numberText,detailText;
    }
}
