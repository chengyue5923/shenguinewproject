package com.base.framwork.cach.db;

import android.content.ContentValues;

/**
 * Created by Wesley on 14-1-26.
 */
public class InsertRequest {

    private String tabName;
    private ContentValues contentValues;

    public InsertRequest(String tableName, ContentValues contentValues) {
        this.tabName = tableName;
        this.contentValues = contentValues;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public ContentValues getSelectionArgsl() {
        return contentValues;
    }

    public void setContentValues(ContentValues contentValues) {
        this.contentValues = contentValues;
    }

}
