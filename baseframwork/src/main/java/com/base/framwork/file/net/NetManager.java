package com.base.framwork.file.net;



import com.base.platform.utils.android.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetManager {
    /**
     * @param LocalPath 本地存的路径
     * @param url       需要下载的路径
     * @return 下载后的文件file
     */
    public static File downLoadFile(String LocalPath, String url) {
        File file = new File(LocalPath);
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn;
            conn = (HttpURLConnection) httpUrl.openConnection();

            InputStream is = conn.getInputStream();

            FileOutputStream fos = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            conn.connect();
            if (conn.getResponseCode() >= 400) {
                file = null;
            } else {
                int num = 0;
                while (true) {

                    if (is != null) {
                        int numRead = is.read(buf);
                        if (numRead <= 0) {
                            break;
                        } else {
                            num += numRead;
                            fos.write(buf, 0, numRead);
                        }
                    } else {
                        break;
                    }
                }
            }
            conn.disconnect();
            if (fos != null) {
                fos.close();
            }

            if (is != null) {
                is.close();
            }
        } catch (Exception e) {
            file = null;
            e.printStackTrace();
            Logger.e("异常捕获====" + e.getMessage());
        }


        return file;
    }

}
