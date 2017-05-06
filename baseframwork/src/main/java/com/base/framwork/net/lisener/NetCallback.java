package com.base.framwork.net.lisener;
/**
 *
 */

/**
 * 网络的callback---回调
 *
 * @author Administrator
 */
public interface NetCallback {


    /**
     * 得到网络返回结果
     *
     * @param jsonstring
     */
    public void dealReslut(String jsonstring);

    /**
     * 网络返回异常 需要传到外面
     *
     * @param e
     */
    public void dealFailer(Exception e,String jsonstring);


    /**
     * 访问开始
     */
    public void onstart();

    /**
     * 访问结束
     */
    public void onEnd();
}
