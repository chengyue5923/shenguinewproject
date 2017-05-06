package com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle;

/**
 * Created by Administrator on 2017/4/11.
 */

public class TucaoCallBackBean {

    UserQuestionListBean.DataBean dataBean;
    boolean aBoolean;
    int position;

    public TucaoCallBackBean(UserQuestionListBean.DataBean dataBean, int position, boolean aBoolean) {
        this.dataBean = dataBean;
        this.position = position;
        this.aBoolean = aBoolean;
    }

    public UserQuestionListBean.DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(UserQuestionListBean.DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public boolean isaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
