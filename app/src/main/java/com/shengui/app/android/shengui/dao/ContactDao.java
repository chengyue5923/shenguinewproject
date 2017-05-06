package com.shengui.app.android.shengui.dao;

import com.base.framwork.cach.db.SqlRequest;
import com.base.platform.utils.java.ListUtil;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.ContactModel;

import java.util.List;
import java.util.Map;

/**
 * Created by yanbo on 16-7-15.
 */
public class ContactDao extends BaseDao {
    public ContactDao() {
        super();
        createTable();
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS  user( " +
                " uid        INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " name       VARCHAR, " +
                " avatar         VARCHAR " +
                " )";
        opration.execSql(new SqlRequest(sql, new Object[]{}));
    }

    public ContactModel getUserById(int id) {
        ContactModel model = new ContactModel();
        String sql = "select * from user where uid = ? limit 0,1";
        SqlRequest sqlRequest = new SqlRequest(sql, new String[]{String.valueOf(id)});
        List<Map<String, String>> maps = opration.querySql(sqlRequest);
        if (ListUtil.isNullOrEmpty(maps)) {
            return model;
        }
        try {
            Map<String, String> map = maps.get(0);
            model.setUid(Integer.valueOf(map.get("uid")));
            model.setName(map.get("name"));
            model.setAvatar(map.get("avatar"));

        } catch (Exception e) {

        }
        return model;
    }

    public boolean update(ContactModel model) {
        if (model == null) {
            return false;
        }
        String sql = "replace into user (uid,name,avatar) values (?,?,?)";
        SqlRequest sqlRequest = new SqlRequest(sql, new Object[]{model.getUid(), model.getName(), model.getAvatar()});
        return opration.execSql(sqlRequest);
    }
}
