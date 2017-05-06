package com.shengui.app.android.shengui.dao;

import com.base.framwork.cach.db.SqlRequest;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.java.ListUtil;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.SessionModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yanbo on 16-7-15.
 */
public class SessionDao extends BaseDao {
    public SessionDao() {
        super();
        createTable();
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS  session( " +
                " sessionId        VARCHAR PRIMARY KEY, " +
                " unReadCount       INTEGER, " +
                " fromUserId         INTEGER," +
                " userId         INTEGER" +
                " )";
        opration.execSql(new SqlRequest(sql, new Object[]{}));
    }

    public int getFromIdBySessionId(String sessionId) {
        String sql = "select * from session where sessionId=?";
        List<Map<String, String>> maps = opration.querySql(new SqlRequest(sql, new String[]{sessionId}));
        if (ListUtil.isNullOrEmpty(maps)) {
            return -1;
        }
        return Integer.parseInt(maps.get(0).get("fromUserId"));
    }

    public String getSessionIdByUid(int fromUserId) {
        String sql = "select * from session where userId=? and fromUserId =?";
        List<Map<String, String>> maps = opration.querySql(new SqlRequest(sql, new String[]{String.valueOf(fromUserId), String.valueOf(UserPreference.getUid())}));
        if (ListUtil.isNullOrEmpty(maps)) {
            return null;
        }
        return maps.get(0).get("sessionId");
    }
    public String getSessionIdFromMine(int fromUserId) {
        String sql = "select * from session where userId=? and fromUserId =?";
        List<Map<String, String>> maps = opration.querySql(new SqlRequest(sql, new String[]{String.valueOf(UserPreference.getUid()), String.valueOf(fromUserId)}));
        if (ListUtil.isNullOrEmpty(maps)) {
            return null;
        }
        return maps.get(0).get("sessionId");
    }
    public List<SessionModel> getAllSession() {
        List<SessionModel> res = new ArrayList<SessionModel>();
        String sql = "select * from session where userId  != 1 and fromUserId = ? ";
        List<Map<String, String>> maps = opration.querySql(new SqlRequest(sql, new String[]{String.valueOf(UserPreference.getUid())}));
        MessageDao messageDao = new MessageDao();
        if (ListUtil.isNullOrEmpty(maps)) {
            return res;
        }
        for (Map<String, String> map : maps) {
            try {
                Logger.e("----map--"+map.toString());
                SessionModel model = new SessionModel();
                model.setSessionId(map.get("sessionId"));
                model.setUserId(Integer.valueOf(map.get("userId")));
                model.setFromId(Integer.valueOf(map.get("fromUserId")));
                model.setUnReadCount(messageDao.getUnReadCountBySessionId(map.get("sessionId")));
                res.add(model);
            } catch (Exception e) {

            }
        }

        return res;
    }
    public List<SessionModel> getSendAllSession() {
        List<SessionModel> res = new ArrayList<SessionModel>();
        String sql = "select * from session where userId  = ? and fromUserId != 1 ";
        List<Map<String, String>> maps = opration.querySql(new SqlRequest(sql, new String[]{String.valueOf(UserPreference.getUid())}));
        MessageDao messageDao = new MessageDao();
        if (ListUtil.isNullOrEmpty(maps)) {
            return res;
        }
        for (Map<String, String> map : maps) {
            try {
                Logger.e("----map--"+map.toString());
                SessionModel model = new SessionModel();
                model.setSessionId(map.get("sessionId"));
                model.setUserId(Integer.valueOf(map.get("userId")));
                model.setFromId(Integer.valueOf(map.get("fromUserId")));
                model.setUnReadCount(messageDao.getUnReadCountBySessionId(map.get("sessionId")));
                res.add(model);
            } catch (Exception e) {

            }
        }

        return res;
    }
    public boolean update(SessionModel model) {
        if (model == null) {
            return false;
        }
        String sql = "replace into session (sessionId,unReadCount,fromUserId,userId) values (?,?,?,?)";
        SqlRequest sqlRequest = new SqlRequest(sql, new Object[]{model.getSessionId(), model.getUnReadCount(), model.getFromId(), model.getUserId()});
        return opration.execSql(sqlRequest);
    }
    public boolean updateSessionId(SessionModel model) {
        if (model == null) {
            return false;
        }
        String sql = "replace into session (sessionId,unReadCount,fromUserId,userId) values (?,?,?,?)";
        SqlRequest sqlRequest = new SqlRequest(sql, new Object[]{model.getSessionId(), model.getUnReadCount(), model.getFromId(), model.getUserId()});
        return opration.execSql(sqlRequest);
    }
}
