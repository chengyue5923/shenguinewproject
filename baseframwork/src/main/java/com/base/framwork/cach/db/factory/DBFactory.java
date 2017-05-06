package com.base.framwork.cach.db.factory;

import android.content.Context;

import com.base.framwork.cach.db.achieve.AndroidDataOperate;
import com.base.framwork.cach.db.callback.DbOpration;

import java.util.HashMap;
import java.util.Set;

/**
 * 创建的数据库工厂，在常见前时候需要对数据库 工厂前需要对数据库的配置文件进行设置
 *
 * @author lin
 */
public class DBFactory {
    Context context;
    String path;
    HashMap<String, DbOpration> hashMap;

    /**
     * 获取数据库操作的对象  通过数据库名称获取 操作对象的单例
     *
     * @return
     */
    public DbOpration getDbcommon(String[] createSql, String[] upDbSql, String db_name, int db_version) {
        // ---暂时 先放弃单例 进行 每次调用的时候 都进行一次 数据库的hepler 对象的 创新
//        Logger.e(db_name+"--------------------context-----------------------"+context);
        if (hashMap.containsKey(db_name) && hashMap.get(db_name) != null) {
            return hashMap.get(db_name);
        }
        DbOpration dbCommon = new AndroidDataOperate(context, createSql, upDbSql, db_name, db_version, path);
        hashMap.put(db_name, dbCommon);
        return dbCommon;
    }

    /**
     * 实例化 数据库工厂
     *
     * @param
     */
    DBFactory() {
        hashMap = new HashMap<String, DbOpration>();
    }


    static DBFactory instance;

    /**
     * 获取单例
     *
     * @return
     */
    public static DBFactory getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new DBFactory();
            return instance;
        }
    }


    public void setContext(Context context) {
//        SQLiteDatabase.loadLibs(context);
        this.context = context;
    }

    public void setPath(String path) {
        this.path = path;
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        hashMap.clear();
    }

    public void close() {
        Set<String> set = hashMap.keySet();
        if (!set.isEmpty()) {
            String[] keys = new String[set.size()];
            for (String key : keys) {
                DbOpration opration = hashMap.get(key);
                if (opration != null) {
                    opration.close();
                }

            }

        }

    }
}
