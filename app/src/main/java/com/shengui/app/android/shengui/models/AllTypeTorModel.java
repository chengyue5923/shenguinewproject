package com.shengui.app.android.shengui.models;

import java.util.List;

/**
 * Created by admin on 2017/2/15.
 */

public class AllTypeTorModel {
    String charac;
    List<CircleMemberDetail> modelList;

    public String getCharac() {
        return charac;
    }

    public void setCharac(String charac) {
        this.charac = charac;
    }

    public List<CircleMemberDetail> getModelList() {
        return modelList;
    }

    public void setModelList(List<CircleMemberDetail> modelList) {
        this.modelList = modelList;
    }
}
