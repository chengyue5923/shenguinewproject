package com.base.framwork.cach.db;

/**
 * Created by Wesley on 14-1-26.
 */
public class SqlRequest {

    private String sql;
    private Object[] selectionArgsl;

    public SqlRequest(String sql, Object[] selectionArgsl) {
        this.sql = sql;
        this.selectionArgsl = selectionArgsl;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object[] getSelectionArgsl() {
        if (selectionArgsl == null)
            selectionArgsl = new String[]{};
        return selectionArgsl;
    }


    public void setSelectionArgsl(String[] selectionArgsl) {
        this.selectionArgsl = selectionArgsl;
    }
}
