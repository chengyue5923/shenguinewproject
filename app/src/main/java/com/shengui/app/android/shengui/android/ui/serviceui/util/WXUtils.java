package com.shengui.app.android.shengui.android.ui.serviceui.util;




import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2017/4/15.
 */

public class WXUtils {

    /**
     * 产生随机字符串
     *
     * @param numberFlag 是否允许有字母
     * @param length     随机字符串长度
     * @return 随机字符串
     */
    public static String createRandomString(boolean numberFlag, int length) {
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }

    /**
     * 获取MD5加密后的字符串
     *
     * @param val 待加密字符串
     * @return 加密后字符串
     */
    public static String getMD5(String val) {
        byte[] m = new byte[0];
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(val.getBytes());
            m = md5.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return getString(m);
    }

    private static String getString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(b[i]);
        }
        return sb.toString();
    }

    /**
     * 获取10位长度的时间戳
     *
     * @return 10位时间戳
     */
    public static String timeStamp() {
        String time = String.valueOf(System.currentTimeMillis());
        return time.substring(0, 10);
    }


//    public static byte[] httpGet(final String url) {
//        if (url == null || url.length() == 0) {
//            return null;
//        }
//        HttpClient httpClient = getNewHttpClient();
//        HttpGet httpGet = new HttpGet(url);
//        try {
//            HttpResponse resp = httpClient.execute(httpGet);
//            if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
//                return null;
//            }
//            return EntityUtils.toByteArray(resp.getEntity());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//    public static byte[] httpPost(String url, String entity) {
//        if (url == null || url.length() == 0) {
//            return null;
//        }
//        HttpClient httpClient = getNewHttpClient();
//        HttpPost httpPost = new HttpPost(url);
//        try {
//            httpPost.setEntity(new StringEntity(entity));
//            httpPost.setHeader("Accept", "application/json");
//            httpPost.setHeader("Content-type", "application/json");
//            HttpResponse resp = httpClient.execute(httpPost);
//            if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
//                return null;
//            }
//            return EntityUtils.toByteArray(resp.getEntity());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//    private static HttpClient getNewHttpClient() {
//        try {
//            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            trustStore.load(null, null);
//
//            SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
//            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//
//            HttpParams params = new BasicHttpParams();
//            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
//            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
//
//            SchemeRegistry registry = new SchemeRegistry();
//            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//            registry.register(new Scheme("https", sf, 443));
//
//            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
//
//            return new DefaultHttpClient(ccm, params);
//        } catch (Exception e) {
//            return new DefaultHttpClient();
//        }
//    }

//    public static OrderResult parseXml(InputStream is) {
//        XmlPullParser parser = Xml.newPullParser();
//        OrderResult orderResult = null;
//        try {
//            parser.setInput(is, "UTF-8");
//            int type = parser.getEventType();
//            while (type != XmlPullParser.END_DOCUMENT) {
//                switch (type) {
//                    case XmlPullParser.START_DOCUMENT:
//                        break;
//                    case XmlPullParser.START_TAG:
//                        if (parser.getName().equals("xml")) {
//                            orderResult = new OrderResult();
//                        } else if (parser.getName().equals("return_code")) {
//                            orderResult.setReturnCode(parser.nextText());
//                        } else if (parser.getName().equals("return_msg")) {
//                            orderResult.setReturnMsg(parser.nextText());
//                        } else if (parser.getName().equals("result_code")) {
//                            orderResult.setResultCode(parser.nextText());
//                        } else if (parser.getName().equals("err_code_des")) {
//                            orderResult.setErrorDesc(parser.nextText());
//                        } else if (parser.getName().equals("prepay_id")) {
//                            orderResult.setPrepayId(parser.nextText());
//                        } else if (parser.getName().equals("sign")) {
//                            orderResult.setSign(parser.nextText());
//                        }
//                        break;
//                    case XmlPullParser.END_TAG:
//                        break;
//                }
//
//                type = parser.next();
//            }
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return orderResult;
//    }
}