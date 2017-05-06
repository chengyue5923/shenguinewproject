package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.ImageModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by admin on 2017/3/17.
 */

public class TestLoopAdapter extends LoopPagerAdapter {
    private List<ImageModel> sliderModels;

    public TestLoopAdapter(RollPagerView viewPager, Context con) {
        super(viewPager);
        this.con = con;
    }


    Context con;
    public TestLoopAdapter(Context context,RollPagerView viewPager, List<ImageModel> sliderModels) {
        super(viewPager);
        con=context;
        this.sliderModels = sliderModels;
    }

    public void refresh(Context context,List<ImageModel> sliderModels) {
        this.sliderModels = sliderModels;
        con=context;
        notifyDataSetChanged();
    }

    public void setData(List<ImageModel> sliderModels){
        this.sliderModels = sliderModels;
        notifyDataSetChanged();
    }
    OnClickListener listener;
    public void onClickLisnter( OnClickListener li){
        listener=li;
    }
    public interface  OnClickListener{
        void OnCLickListener( ImageModel sliderModel );
    }
    @Override
    public View getView(ViewGroup container, final int position) {
       final   ImageModel sliderModel = sliderModels.get(position);
        ImageView view = new ImageView(container.getContext());
        Picasso.with(con).load(sliderModel.getImg_1()).into(view);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int) (ViewGroup.LayoutParams.MATCH_PARENT/2.88) ));
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,400));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(listener!=null){
                        listener.OnCLickListener(sliderModel);
                    }
                }catch (Exception e){
                    Logger.e("exception"+e.getMessage());
                }
            }
        });
        return view;
    }
    @Override
    public int getRealCount() {
        return sliderModels.size();
    }
}
