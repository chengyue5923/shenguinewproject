package com.shengui.app.android.shengui.models;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by admin on 2017/2/28.
 */

public class ContactPhoneModel implements Serializable {


    String mContactsName;
    String mContactsNumber;
    Bitmap mContactsPhonto;

    @Override
    public String toString() {
        return "ContactPhoneModel{" +
                "mContactsName='" + mContactsName + '\'' +
                ", mContactsNumber='" + mContactsNumber + '\'' +
                ", mContactsPhonto=" + mContactsPhonto +
                '}';
    }

    public String getmContactsName() {
        return mContactsName;
    }

    public void setmContactsName(String mContactsName) {
        this.mContactsName = mContactsName;
    }

    public String getmContactsNumber() {
        return mContactsNumber;
    }

    public void setmContactsNumber(String mContactsNumber) {
        this.mContactsNumber = mContactsNumber;
    }

    public Bitmap getmContactsPhonto() {
        return mContactsPhonto;
    }

    public void setmContactsPhonto(Bitmap mContactsPhonto) {
        this.mContactsPhonto = mContactsPhonto;
    }
}
