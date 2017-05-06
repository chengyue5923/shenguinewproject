/**
 *
 */
package com.base.platform.utils.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * @author Administrator
 *         文件操作的工具类
 */
public class FileTools {
    public static String separator = File.separator;


    /**
     * @param filename 文件名称
     * @return
     */
    public static boolean direxists(String filename) {
        boolean rvalue = false;
        File f = new File(filename);
        String filepath = f.getParent();
        if (filepath != null && !filepath.equals("")) {
            f = new File(filepath);
            if (f.exists()) {

                rvalue = true;
            } else {
                rvalue = false;
            }
        }
        return rvalue;
    }

    /**
     * 生成文件所在的文件夹
     *
     * @param filename
     * @return 是否生成成功
     */
    public static boolean creatDir(String filename) {
        if (StringTools.isNullOrEmpty(filename)) {
            return false;
        }
        File f = new File(filename);
        if (f.exists()) {
            return true;
        }
        return f.mkdirs();
    }

    public static void main(String[] str) {

        creatDir("D:/a/b/c.txt");

        System.out.println("D:/a/b/c.txt");
    }

    /**
     * 创建一个file 是否被覆盖
     *
     * @param filename
     * @param isCover
     * @return 是否创建成功
     */
    public static boolean createFile(String filename, boolean isCover) {
        boolean rvalue = false;
        if (direxists(filename)) {
            rvalue = true;

        } else {
            if (creatDir(filename)) {
                rvalue = true;
            }
        }
        if (rvalue) {
            File file = new File(filename);
            boolean isecist = file.exists();
            if (isecist && !isCover) {
                rvalue = false;
            } else {
                if (isecist) {
                    if (!file.delete()) {
                        rvalue = false;
                    }
                }
                if (rvalue) {
                    rvalue = createFile(file);
                }
            }
        }
        return rvalue;
    }

    public static boolean createFile(File file) {
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 流的复制
     *
     * @param is
     * @param os
     * @return
     * @throws java.io.IOException
     */
    public static OutputStream streamcopy(InputStream is, OutputStream os)
            throws IOException {
        byte[] buf = new byte[Runtime.getRuntime().freeMemory() >= 1048576L ? 1048576
                : 1024];
        while (true) {
            int count = is.read(buf, 0, buf.length);
            if (count == -1)
                break;
            os.write(buf, 0, count);
        }
        is.close();
        return os;
    }

    /**
     * @param filename 文件路径
     * @param message  需要写入的内容
     */
    public static void write(String filename, String message) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(filename));
            out.write(message.getBytes());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 向一个指定文件写内容
     *
     * @param filename
     * @param is       input流
     * @return
     */
    @SuppressWarnings("resource")
    public static boolean write(String filename, InputStream is) {
        boolean rvalue = false;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(filename));

            out = (FileOutputStream) streamcopy(is, out);
            rvalue = true;

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            rvalue = false;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            rvalue = false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return rvalue;
    }

    /**
     * 在已有文件基础上写data
     *
     * @param filename 文件名称
     * @param data     需要在原来基础上继续写入的内容
     * @return 是否写成功
     */
    public static boolean appendData(String filename, String data) {
        boolean rvalue = false;
        File f = new File(filename);
        if (f.canWrite()) {
            try {
                FileWriter fw = new FileWriter(f, true);
                if (fw != null) {
                    rvalue = true;
                    fw.write(data);
                    fw.close();
                }
                return rvalue;
            } catch (IOException e) {
                e.printStackTrace();
                return rvalue;
            }
        }
        return rvalue;
    }

    /**
     * 读取文件
     *
     * @param filename 文件名
     * @param row      行数
     * @return
     */
    public static ArrayList readTxtFile(String filename, int row) {
        File f = new File(filename);
        if ((f.exists()) && (f.canRead())) {
            try {
                FileInputStream fin = new FileInputStream(f);
                InputStreamReader isr = new InputStreamReader(fin);
                BufferedReader bd = new BufferedReader(isr);
                ArrayList al = new ArrayList();
                String tempString = null;
                int line = 0;
                while ((tempString = bd.readLine()) != null) {
                    al.add(tempString);
                    line++;
                    if (line == row) {
                        break;
                    }
                }

                bd.close();
                isr.close();
                fin.close();
                return al.size() == 0 ? null : al;
            } catch (IOException ie) {
                ie.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 读取一行文件
     *
     * @param filename
     * @return
     */
    public static String readTxtLine(String filename) {
        File f = new File(filename);
        if ((f.exists()) && (f.canRead())) {
            try {
                FileInputStream fin = new FileInputStream(f);
                InputStreamReader isr = new InputStreamReader(fin);
                BufferedReader bd = new BufferedReader(isr);
                String tempString = bd.readLine();
                bd.close();
                isr.close();
                fin.close();
                return tempString;
            } catch (IOException ie) {
                ie.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 从文件末行开始读取多行数据
     *
     * @param filename   文件名称
     * @param beginindex 文件开始行
     * @param row        读取行数
     * @return
     */
    public static ArrayList readTxtFile(String filename, int beginindex, int row) {
        File f = new File(filename);
        if ((f.exists()) && (f.canRead())) {
            try {
                FileInputStream fin = new FileInputStream(f);
                InputStreamReader isr = new InputStreamReader(fin);
                BufferedReader bd = new BufferedReader(isr);
                ArrayList al = new ArrayList();
                String tempString = null;
                int line = 0;
                int prow = 0;
                while ((tempString = bd.readLine()) != null) {
                    line++;
                    if (line > beginindex) {
                        al.add(tempString);
                        prow++;
                    }
                    if (prow == row) {
                        break;
                    }
                }
                bd.close();
                isr.close();
                fin.close();
                return al.size() == 0 ? null : al;
            } catch (IOException ie) {
                ie.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 判断文件是否存在
     *
     * @param filename 文件名
     * @return
     */
    public boolean fileIsExists(String filename) {
        boolean rvalue = false;
        File f = new File(filename);
        if (f.exists()) {
            rvalue = true;
        } else {
            rvalue = false;
        }

        return rvalue;
    }


}
