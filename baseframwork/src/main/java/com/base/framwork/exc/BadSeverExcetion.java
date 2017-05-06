package com.base.framwork.exc;

/**
 * 服务段的异常定义  如 404 等
 */
public class BadSeverExcetion extends BaseException {
    public BadSeverExcetion(String detailMessage) {
        super(detailMessage);
    }
}
