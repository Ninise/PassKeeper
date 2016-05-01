package com.hazelhunt.passkeeper.mvp.model;

import rxsqlite.annotation.SQLiteColumn;
import rxsqlite.annotation.SQLiteObject;
import rxsqlite.annotation.SQLitePk;

@SQLiteObject("accounts")
public class PassModel {

    @SQLitePk
    private long _id;

    @SQLiteColumn
    private String mUrl;

    @SQLiteColumn
    private String mLogin;

    @SQLiteColumn
    private String mPass;

    @SQLiteColumn
    private String mEmail;

    @SQLiteColumn
    private String mExtra;

    public PassModel() {
    }

    public PassModel(long id, String url, String login, String pass, String email, String extra) {
        _id = id;
        mUrl = url;
        mLogin = login;
        mPass = pass;
        mEmail = email;
        mExtra = extra;
    }

    public PassModel(String url, String login, String pass, String email, String extra) {
        mUrl = url;
        mLogin = login;
        mPass = pass;
        mEmail = email;
        mExtra = extra;
    }

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        _id = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String login) {
        mLogin = login;
    }

    public String getPass() {
        return mPass;
    }

    public void setPass(String pass) {
        mPass = pass;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getExtra() {
        return mExtra;
    }

    public void setExtra(String extra) {
        mExtra = extra;
    }

    @Override
    public String toString() {
        return "PassModel{" +
                "_id=" + _id +
                ", mUrl='" + mUrl + '\'' +
                ", mLogin='" + mLogin + '\'' +
                ", mPass='" + mPass + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mExtra='" + mExtra + '\'' +
                '}';
    }
}
