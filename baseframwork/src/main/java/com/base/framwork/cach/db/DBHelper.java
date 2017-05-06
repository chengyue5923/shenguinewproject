package com.base.framwork.cach.db;

/**
 * Created by Wesley on 14-1-23.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.base.platform.utils.android.Logger;


/**
 * sqHelper实现类
 *
 * @author Administrator
 */
public class DBHelper extends SQLiteOpenHelper {

    private String[] createSql;
    private String[] upDbSql;
    private int db_version;

    /**
     *
     */
    private String db_name;
    /**
     * 数据库的路径
     */
    private String path;

    public DBHelper(Context context, String[] createSql, String[] upDbSql, String db_name, int db_version, String path) {
        super(context, db_name, null, db_version);
        this.createSql = createSql;
        this.upDbSql = upDbSql;
    }

    public static String getInnerSd() {
        if (("mounted".equals(Environment.getExternalStorageState())) && (Environment.getExternalStorageDirectory().canWrite()) && (Environment.getExternalStorageDirectory().canRead()))
            return Environment.getExternalStorageDirectory().getPath();
        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        if (createSql == null) {
            Logger.e("createSql is null");
            return;
        }
        if (createSql != null) {
            for (String sql : createSql) {
                db.execSQL(sql);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (!db.needUpgrade(newVersion)) {
            Logger.e("createSql is null");
            return;
        }
        if (db.needUpgrade(newVersion)) {
            if (upDbSql == null) {
                Logger.e("版本改变了 但是没有更新的sql ");
                return;
            }
            if (upDbSql != null) {
                for (String sql : upDbSql) {
                    db.execSQL(sql);
                }
                db.setVersion(newVersion);
            }
        }

    }

    public String[] getCreateSql() {
        return createSql;
    }

    public void setCreateSql(String[] createSql) {
        this.createSql = createSql;
    }

    public String[] getUpDbSql() {
        return upDbSql;
    }

    public void setUpDbSql(String[] upDbSql) {
        this.upDbSql = upDbSql;
    }

    public int getDb_version() {
        return db_version;
    }

    public void setDb_version(int db_version) {
        this.db_version = db_version;
    }

    public String getDb_name() {
        return db_name;
    }

    public void setDb_name(String db_name) {
        this.db_name = db_name;
    }
}
