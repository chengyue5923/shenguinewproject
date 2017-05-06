package com.shengui.app.android.shengui.models;



import com.shengui.app.android.shengui.configer.enums.HttpMethod;
import com.shengui.app.android.shengui.configer.enums.NetNofityBean;

import java.io.Serializable;


/**
 * Created by Administrator on 2015/4/14.
 */
public class HttpConfigBean implements Serializable{


    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String action;
    long timeout;
    int method;
    boolean cach;
    boolean https;
    boolean header;
    boolean needLogin;
    boolean showToast;
    boolean showLoadDialog;
    boolean showNotify;
    String type;
    NetNofityBean nofity;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActioin() {
        return action.replace("?","/");
    }

    public void setActioin(String actioin) {
        this.action = actioin;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public int  getMethod() {
        return method;
    }


    public HttpMethod getShowEnumsMethod(){
       return HttpMethod.getMethodByType(method);
    }

    public void setMethod(int  method) {
        this.method = method;
    }

    public boolean isCach() {
        return cach;
    }

    public void setCach(boolean cach) {
        this.cach = cach;
    }

    public boolean isHttps() {
        return https;
    }

    public void setHttps(boolean https) {
        this.https = https;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public boolean isNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(boolean needLogin) {
        this.needLogin = needLogin;
    }

    public boolean isShowToast() {
        return showToast;
    }

    public void setShowToast(boolean showToast) {
        this.showToast = showToast;
    }

    public boolean isShowLoadDialog() {
        return showLoadDialog;
    }

    public void setShowLoadDialog(boolean showLoadDialog) {
        this.showLoadDialog = showLoadDialog;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public NetNofityBean getNofity() {
        return nofity;
    }

    public void setNofity(NetNofityBean nofity) {
        this.nofity = nofity;
    }
}
