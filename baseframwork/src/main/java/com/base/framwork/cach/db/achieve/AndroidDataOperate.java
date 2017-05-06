package com.base.framwork.cach.db.achieve;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.base.framwork.cach.db.DBHelper;
import com.base.framwork.cach.db.InsertRequest;
import com.base.framwork.cach.db.SqlRequest;
import com.base.framwork.cach.db.callback.DbOpration;
import com.base.platform.utils.android.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wesley on 14-1-26.
 */
public class AndroidDataOperate implements DbOpration {


    DBHelper db;
    SQLiteDatabase mSQLiteDatabase;
    private Context mcontext;
    /**
     * db配置
     */

    private String db_name;
    private int db_version;
    private String[] createSql;
    private String[] upDbSql;
    private String path;

    public AndroidDataOperate(Context context, String[] createSql, String[] upDbSql, String db_name, int db_version, String path) {
        mcontext = context;
        this.db_name = db_name;
        this.db_version = db_version;
        this.createSql = createSql;
        this.upDbSql = upDbSql;
        this.path = path;


    }

    @Override
    public synchronized boolean execSqls(String[] requests) {

        Logger.i("SQLS START");


        int sqllength = requests.length;
        try {

            getDbBase().beginTransaction();
            for (int i = 0; i < sqllength; i++) {
//                Logger.d("sql[" + i + "]:" + requests[i]);
                getDbBase().execSQL(requests[i]);
            }
            getDbBase().setTransactionSuccessful();
            return true;
        } catch (SQLException e) {
            Logger.e(e.getMessage(), e);
            return false;
        } finally {
            try {
                getDbBase().endTransaction();

            } catch (Exception e) {

            }

        }


    }

    @Override
    public String[][] querySqlNew(SqlRequest request) {
//        Logger.e("sql--" + request.getSql());
        Cursor vcus = null;
        try {

            vcus = getDbBase().rawQuery(request.getSql(),
                    (String[]) request.getSelectionArgsl());

//            Logger.e("sql-----0-----------------END");
            String[][] list = getDataArray(vcus);
//            Logger.e("sql-------1---------------END");
            return list;
        } catch (Exception e) {
            Logger.e(e.getMessage(), e);
            return null;
        } finally {
            Logger.d("sql--END");
            if (vcus != null && !(vcus.isClosed())) {
                vcus.close();
            }

        }
    }

    public void open() {
        db = new DBHelper(mcontext, createSql, upDbSql, db_name, db_version, path);
        Logger.e("-open-" + mcontext);
        mSQLiteDatabase = db.getWritableDatabase();

        Logger.d("db is open");
    }

    private SQLiteDatabase getDbBase() {
        if (mSQLiteDatabase == null || !mSQLiteDatabase.isOpen()) {
            open();
        }
        return this.mSQLiteDatabase;
    }

    public void close() {
        if (mSQLiteDatabase != null) {
            if (mSQLiteDatabase.isOpen())
                mSQLiteDatabase.close();
        }
        if (db != null) {
            db.close();
        }
        Logger.d("db is close");
    }

    /**
     * 数据库的查询
     *
     * @return
     */
    public List<Map<String, String>> querySql(SqlRequest request) {
        Cursor vcus = null;
        try {
            vcus = getDbBase().rawQuery(request.getSql(),
                    (String[]) request.getSelectionArgsl());
            List<Map<String, String>> list = getDataArrayList(vcus);
            return list;
        } catch (Exception e) {
            Logger.e(e.getMessage(), e);
            return null;
        } finally {
            if (vcus != null && !(vcus.isClosed())) {
                vcus.close();
            }
        }
    }

    /**
     * 查询 cont
     *
     * @param request
     * @return
     */
    public int queryCount(SqlRequest request) {
        Logger.d("sql--" + db_name);
        Cursor vcus = null;
        try {

            vcus = getDbBase().rawQuery(request.getSql(),
                    (String[]) request.getSelectionArgsl());
            vcus.moveToNext();
            int count = vcus.getInt(0);
            vcus.close();
            return count;
        } catch (Exception e) {
            Logger.e(e.getMessage(), e);
            return 0;
        } finally {
            if (vcus != null && !(vcus.isClosed())) {
                vcus.close();
            }
        }
    }

    /**
     * 执行sql
     *
     * @param request
     * @return
     */
    public synchronized boolean execSql(SqlRequest request) {
        try {
            Logger.e("sql--" + request.getSql());
            getDbBase().execSQL(request.getSql(), request.getSelectionArgsl());
            return true;
        } catch (SQLException e) {
            SQLException e1 = e;
            Logger.e(e.getMessage(), e1);
            return false;
        } finally {

        }
    }

    @Override
    public long insert(InsertRequest request) {
        try {
            Logger.e("sql--" + request.getTabName());
            return getDbBase().insert(request.getTabName(), null,request.getSelectionArgsl());

        } catch (SQLException e) {
            SQLException e1 = e;
            Logger.e(e.getMessage(), e1);
            return -1;
        } finally {

        }
    }

    @Override
    public synchronized boolean execSqls(List<SqlRequest> requests) {
        SqlRequest[] requests1 = new SqlRequest[requests.size()];
        return execSqls(requests1);
    }

    /**
     * 执行sql数组
     *
     * @param requests
     * @return
     */
    public synchronized boolean execSqls(SqlRequest[] requests) {
        Logger.i("SQLS START");


        int sqllength = requests.length;
        try {

            getDbBase().beginTransaction();
            for (int i = 0; i < sqllength; i++) {
//                Logger.d("sql[" + i + "]:" + requests[i].getSql());
                if (requests != null) {
                    if (requests[i] != null) {
                        getDbBase().execSQL(requests[i].getSql(), requests[i].getSelectionArgsl());
                    }


                }
            }
            getDbBase().setTransactionSuccessful();
            return true;
        } catch (SQLException e) {
            Logger.e(e.getMessage(), e);
            return false;
        } finally {
            getDbBase().endTransaction();
            Logger.i("SQLS END");
            // close();
        }


    }

    /**
     * @return 从cursor中返回 listMap数组
     */
    private List<Map<String, String>> getDataArrayList(Cursor vCursor) {
//        long start=System.currentTimeMillis();
//        Logger.e("start---"+start);
//        String[][] returnvale = null;
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if (vCursor != null) {
            for (vCursor.moveToFirst(); !vCursor.isAfterLast(); vCursor.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();
                for (int j = 0; j < vCursor.getColumnCount(); j++) {
                    try {

                        map.put(vCursor.getColumnName(j), vCursor.getString(j));

                    } catch (Exception e) {
//                        map.put(vCursor.getColumnName(j), vCursor.getString(j));
                    }
                }
                list.add(map);
            }
        }


//        Logger.e("end---"+(System.currentTimeMillis()-start));
        return list;
    }


    /**
     * @return 从cursor中返回 listMap数组
     */
    private String[][] getDataArray(Cursor vCursor) {
//         long start=System.currentTimeMillis();
//         Logger.e("start---"+start);
        String[][] returnvale = null;
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if (vCursor != null) {
            if (!vCursor.isFirst()) {
                vCursor.moveToFirst();
            }
            returnvale = new String[vCursor.getCount()][vCursor.getColumnCount()];
            int i = 0;
            while (vCursor.moveToNext()) {
                for (int j = 0; j < vCursor.getColumnCount(); j++) {
                    returnvale[i][j] = vCursor.getString(j);
//                    Logger.e("returnvale[i][j]=="+returnvale[i][j]);
                }
//                vCursor.moveToNext();
                i++;
            }
        }
        return returnvale;
    }


    @Override
    public List<Map<String, String>> querySqls(SqlRequest[] request) {


        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        try {
            for (int i = 0; i < request.length; i++) {
//                Logger.d("sql:" + request[i].getSql());

                Cursor vcus = getDbBase().rawQuery(request[i].getSql(),
                        (String[]) request[i].getSelectionArgsl());
                list.addAll(getDataArrayList(vcus));
                if (vcus != null && !(vcus.isClosed())) {
                    vcus.close();
                }
            }
        } catch (Exception e) {
            Logger.e(e.getMessage(), e);
            return null;
        } finally {

        }
        return list;
    }
}
