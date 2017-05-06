package com.base.framwork.file.download.file;

import com.base.framwork.file.download.file.lisener.FileDownListener;

/**
 *下载文件 的 manager
 */
public class FileDownManager {

    static FileDownManager instance;

    public FileDownManager() {

    }

    public static FileDownManager getInstance() {
        return instance;
    }


    /**
     *
     * @param url url
     * @param load  本地路径
     * @param listener 文件下载的监听
     */
    public void  downLoad(String url,String  load,FileDownListener listener){
        new  LoadFileThread(listener).execute(url,load);
    }

}
