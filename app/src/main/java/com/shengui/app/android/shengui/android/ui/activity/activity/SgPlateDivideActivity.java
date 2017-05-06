package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.view.ActivityPlateDividedAdapter;
import com.shengui.app.android.shengui.android.ui.view.PlateBanKuaiListAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.models.QuanziList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/12/27.
 */

public class SgPlateDivideActivity extends BaseActivity implements View.OnClickListener, PlateBanKuaiListAdapter.CountListener, ActivityPlateDividedAdapter.GridListener {
    @Bind(R.id.backImageView)
    TextView backImageView;
    @Bind(R.id.NameText)
    TextView NameText;
    @Bind(R.id.topLayout)
    TextView topLayout;
    @Bind(R.id.createInputTitle)
    EditText createInputTitle;
    @Bind(R.id.inputTitle)
    RelativeLayout inputTitle;
    //    @Bind(R.id.createText)
//    TextView createText;
    @Bind(R.id.allowCreateaaaText)
    TextView allowCreateaaaText;
    @Bind(R.id.gview)
    GridView gview;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    ActivityPlateDividedAdapter adapter;
    PlateBanKuaiListAdapter selectAdapter;
    //    @Bind(R.id.listView)
//    HorizontalListView listView;

    List<ProductModel> listmodel;  //gridview  modle
    List<ProductModel> listmodelee;  //创建的model
    @Bind(R.id.createTextView)
    TextView createTextView;
    @Bind(R.id.createtext)
    TextView createtext;
    @Bind(R.id.images)
    TextView images;
    @Bind(R.id.deleteItenms)
    TextView deleteItenms;
    @Bind(R.id.imagesTwo)
    TextView imagesTwo;
    @Bind(R.id.deleteItenmsTwo)
    TextView deleteItenmsTwo;
    @Bind(R.id.plateLayoutTwo)
    RelativeLayout plateLayoutTwo;
    @Bind(R.id.imagesThree)
    TextView imagesThree;
    @Bind(R.id.deleteItenmsThree)
    TextView deleteItenmsThree;
    @Bind(R.id.plateLayoutThree)
    RelativeLayout plateLayoutThree;
    @Bind(R.id.refrechImage)
    ImageView refrechImage;
    @Bind(R.id.plateLayout)
    RelativeLayout plateLayout;
    private int countNumber = 0;

    private static final int resultCode=12313;
    private static final int resultCodeCnancel=12314;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topLayout:
                if(!BackJson().equals("")){
                    Intent mIntent=new Intent();
                    mIntent.putExtra("modelist",BackJson());
                    setResult(resultCode, mIntent);
                }
                finish();
                break;
            case R.id.backImageView:
//                Intent Intent=new Intent();
//                Intent.putExtra("modelist","");
//                setResult(resultCodeCnancel, Intent);
                finish();
                break;
            case R.id.createTextView:
                if (IcCreatePlate()) {
                    String createInputTitlestr = createInputTitle.getText().toString();
                    if (createInputTitlestr.equals("")) {
                        ToastTool.show("请填写创建的模块内容");
                    } else {
                        //是否已添加
                        if(IshavePlate(createInputTitlestr)){
                            if (plateLayout.getVisibility()==View.GONE){
                                plateLayout.setVisibility(View.VISIBLE);
                                images.setText(createInputTitlestr);
                                createInputTitle.setText("");
                                UpDateNumberHint();
                                return ;
                            }
                            if(plateLayoutTwo.getVisibility()==View.GONE){
                                plateLayoutTwo.setVisibility(View.VISIBLE);
                                imagesTwo.setText(createInputTitlestr);
                                createInputTitle.setText("");
                                UpDateNumberHint();
                                return ;
                            }
                            if(plateLayoutThree.getVisibility()==View.GONE){
                                plateLayoutThree.setVisibility(View.VISIBLE);
                                imagesThree.setText(createInputTitlestr);
                                createInputTitle.setText("");
                                UpDateNumberHint();
                                return ;
                            }
                        }else{
                            ToastTool.show("您已添加该板块");
                        }

                    }
                } else {
                    ToastTool.show("您创建的板块已满");
                }
                break;

            case R.id.deleteItenms:
                images.setText("");
                plateLayout.setVisibility(View.GONE);
                UpDateNumberHint();
                break;
            case R.id.deleteItenmsTwo:
                imagesTwo.setText("");
                plateLayoutTwo.setVisibility(View.GONE);
                UpDateNumberHint();
                break;
            case R.id.deleteItenmsThree:
                imagesThree.setText("");
                plateLayoutThree.setVisibility(View.GONE);
                UpDateNumberHint();
                break;
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    //是否还可创建板块
    public boolean IcCreatePlate() {
        if (plateLayout.getVisibility()==View.GONE){
            return true;
        }
        if(plateLayoutTwo.getVisibility()==View.GONE){
            return  true;
        }
        if(plateLayoutThree.getVisibility()==View.GONE){
            return true;
        }
           return false;
    }
    //何以创建的板块判重
    public boolean IshavePlate(String plateName){
        if(plateLayout.getVisibility()==View.VISIBLE&&plateName.equals(images.getText().toString())){
            return false;
        }
        if(plateLayoutTwo.getVisibility()==View.VISIBLE&&plateName.equals(imagesTwo.getText().toString())){
            return false;
        }
        if(plateLayoutThree.getVisibility()==View.VISIBLE&&plateName.equals(imagesThree.getText().toString())){
            return false;
        }
        return true;
    }
    //更新剩余板块数量提示
    public void UpDateNumberHint(){
        int CreateNum=0;
        if (plateLayout.getVisibility()==View.GONE){
            CreateNum++;
        }
        if(plateLayoutTwo.getVisibility()==View.GONE){
            CreateNum++;
        }
        if(plateLayoutThree.getVisibility()==View.GONE){
            CreateNum++;
        }
        allowCreateaaaText.setText("还可以创建"+CreateNum+"个板块");
    }
    //组装返货数据JSON
    public String BackJson(){
        JSONArray jsonArray=new JSONArray();

        try{
            if (plateLayout.getVisibility()==View.VISIBLE){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("title",images.getText().toString());
                jsonObject.put("type","add");
                jsonArray.put(jsonObject);
            }
            if(plateLayoutTwo.getVisibility()==View.VISIBLE){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("title",imagesTwo.getText().toString());
                jsonObject.put("type","add");
                jsonArray.put(jsonObject);
            }
            if(plateLayoutThree.getVisibility()==View.VISIBLE){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("title",imagesThree.getText().toString());
                jsonObject.put("type","add");
                jsonArray.put(jsonObject);
            }

        }catch (Exception e){
            Logger.e("exce");
        }
        Logger.e("date"+jsonArray.toString());
        return jsonArray.toString();
    }

    @Override
    protected void initEvent() {
        createTextView.setOnClickListener(this);
        adapter.setDialogListener(this);
//        selectAdapter.setDialogListener(this);
        backImageView.setOnClickListener(this);
        topLayout.setOnClickListener(this);

        deleteItenms.setOnClickListener(this);
        deleteItenmsTwo.setOnClickListener(this);
        deleteItenmsThree.setOnClickListener(this);
    }
    QuanziList modelDetail;
    @Override
    protected void initData() {
        GuiMiController.getInstance().GethotPlatelist(this);
        adapter = new ActivityPlateDividedAdapter(this);
        plateLayout.setVisibility(View.GONE);
        plateLayoutTwo.setVisibility(View.GONE);
        plateLayoutThree.setVisibility(View.GONE);
        gview.setAdapter(adapter);
        if(getIntent().getSerializableExtra("QuanziList")!=null){
            modelDetail=(QuanziList)getIntent().getSerializableExtra("QuanziList");
            if (modelDetail.getSection().size() != 0) {
                switch (modelDetail.getSection().size()) {
                    case 1:
                        images.setText(modelDetail.getSection().get(0).getTitle());
                        plateLayout.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        images.setText(modelDetail.getSection().get(0).getTitle());
                        plateLayout.setVisibility(View.VISIBLE);
                        imagesTwo.setText(modelDetail.getSection().get(1).getTitle());
                        plateLayoutTwo.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        images.setText(modelDetail.getSection().get(0).getTitle());
                        plateLayout.setVisibility(View.VISIBLE);
                        imagesTwo.setText(modelDetail.getSection().get(1).getTitle());
                        plateLayoutTwo.setVisibility(View.VISIBLE);
                        imagesThree.setText(modelDetail.getSection().get(2).getTitle());
                        plateLayoutThree.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }


//        listmodel = new ArrayList<>();
//        for (int i = 0; i < 9; i++) {
//            listmodel.add(new ProductModel());
//        }
//        adapter.setRes(listmodel);

//        selectAdapter = new PlateBanKuaiListAdapter(this);
//        listmodelee = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            listmodelee.add(new ProductModel());
//        }
//        countNumber = listmodelee.size();
//        selectAdapter.setRes(listmodelee);
//        listView.setAdapter(selectAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_plate_divided_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        Logger.e("eeeddd" + result);
        if (flag == HttpConfig.hotPlateList.getType()) {
            List<String> hotPlate = (List<String>) result;
            for (String s : hotPlate) {
                Logger.e("ddd" + s);
            }
            adapter.setRes(hotPlate);
        }

    }

    @Override
    public void onclickCountItem(int flags) {
//        countNumber = flags;
    }

    @Override
    public void onclickCountItem(View v, int positon) {
        //是否可以添加
        if (IcCreatePlate()) {
//            if (listmodel.get(positon).isFlags()) {
//                ToastTool.show("您已添加该板块");
//            } else {
//                listmodel.get(positon).setFlags(true);
//                adapter.notifyDataSetChanged();
//            }

            if(IshavePlate(adapter.getItem(positon))){
                if (plateLayout.getVisibility()==View.GONE){
                    plateLayout.setVisibility(View.VISIBLE);
                    images.setText(adapter.getItem(positon));
                    UpDateNumberHint();
                    return ;
                }
                if(plateLayoutTwo.getVisibility()==View.GONE){
                    plateLayoutTwo.setVisibility(View.VISIBLE);
                    imagesTwo.setText(adapter.getItem(positon));
                    UpDateNumberHint();
                    return ;
                }
                if(plateLayoutThree.getVisibility()==View.GONE){
                    plateLayoutThree.setVisibility(View.VISIBLE);
                    imagesThree.setText(adapter.getItem(positon));
                    UpDateNumberHint();
                    return ;
                }
            }else{
                ToastTool.show("您已添加该板块");
            }
        } else {
            ToastTool.show("您创建的板块已满");
        }
    }

}
