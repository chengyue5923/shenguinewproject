package com.base.framwork.net.lisener;

import java.io.Serializable;
import java.util.Map;


/**
 * 从底层返回数据对页面层的做出的响应
 */
public interface ViewNetCallBack {

     void onConnectStart();

     void onConnectEnd();

     void onFail(Exception e);

     void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param);


}
