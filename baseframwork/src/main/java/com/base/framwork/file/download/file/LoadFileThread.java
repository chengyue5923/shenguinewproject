package com.base.framwork.file.download.file;

import android.os.AsyncTask;

import com.base.framwork.file.download.file.lisener.FileDownListener;
import com.base.framwork.file.net.NetManager;

import java.io.File;

/**
 * 下载的线程  0 是url 1 是本地数据
 */
public class LoadFileThread extends AsyncTask {

    FileDownListener listener;



    public LoadFileThread() {
    }

    public LoadFileThread(FileDownListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (listener != null) {
            listener.starDownLoad();
        }
    }

    @Override
    protected File doInBackground(Object[] params) {
        File file = new File(params[1].toString());
        if (file.exists()) {
            return file;
        }
        File file1 = NetManager.downLoadFile(params[1].toString(), params[0].toString());
        return file1;

    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        File f = (File) o;
        boolean bool = false;
        if (f != null && f.exists()) {
            bool = true;
        }
        if (listener != null) {
            listener.endDownLoad(f, bool);
        }
    }


}
