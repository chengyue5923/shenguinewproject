package com.shengui.app.android.shengui.dao;


import com.base.framwork.cach.db.SqlRequest;
import com.base.framwork.cach.db.callback.DbOpration;
import com.base.framwork.cach.db.factory.DBFactory;

/**
 * Created by Wesley on 14-10-14.
 */
public class BaseDao {
    protected DbOpration opration;

    public BaseDao() {
        opration = DBFactory.getInstance().getDbcommon(new String[]{}, new String[]{}, "im.db", 1);
    }


    public void dropTable(String table) {
        String sql = "DROP TABLE " + table;
        opration.execSql(new SqlRequest(sql, new Object[]{}));
    }

    public void createTable(String sql) {
        opration.execSqls(new SqlRequest[]{
                new SqlRequest(sql, new Object[]{})

        });
    }
    public void dropAllTable(){
        try {
            dropTable("message");
            dropTable("session");
        }catch (Exception e){}

    }
}
