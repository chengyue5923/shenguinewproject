package com.base.framwork.file.download.image.lisener;

/**
 *
 */
public interface ImDLisener {



    public void startDown();
    public void complete();
    public void fail();
    public void  progress(int current,int all);



}
