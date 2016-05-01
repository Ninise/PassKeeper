package com.hazelhunt.passkeeper.mvp.model;

import android.content.Context;

import rx.Observable;
import rxsqlite.RxSQLite;
import rxsqlite.RxSQLiteClient;
import rxsqlite.RxSQLiteWhere;

public class DatabaseWorker {

    private static final String TABLE_ACCS = "acc_table";

    public static final String KEY_ID = "_id";
    public static final String KEY_URL = "mUrl";
    public static final String KEY_LOGIN = "mLogin";
    public static final String KEY_PASS = "mPass";
    public static final String KEY_EMAIL = "mEmail";
    public static final String KEY_EXTRA = "mExtra";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_ACCS +
            "(" + KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_URL + " TEXT," +
            KEY_LOGIN + " TEXT," +
            KEY_PASS + " TEXT," +
            KEY_EMAIL + " TEXT," +
            KEY_EXTRA + " TEXT" + ")";

    public static void register(Context context) {
        RxSQLite.register(RxSQLiteClient.builder(context, 1)
                .doOnOpen(db -> db.exec("PRAGMA case_sensitive_like = true;"))
                .doOnUpgrade((sqLiteDb, integer, integer2) -> sqLiteDb.exec("DROP TABLE IF EXISTS " + TABLE_ACCS))
                .doOnCreate(db -> db.exec(CREATE_TABLE))
                .enableTracing()
                .build());
    }

    public static Observable<PassModel> save(PassModel model) {
            return RxSQLite.save(model);
    }

    public static Observable<PassModel> load(long id) {
        return RxSQLite.query(PassModel.class, new RxSQLiteWhere().where(KEY_ID + " = " + id));
    }

    public static Observable<PassModel> loadAll() {
        return RxSQLite.query(PassModel.class, new RxSQLiteWhere());
    }

    public static Observable<Integer> remove(PassModel model) {
        return RxSQLite.remove(model);
    }
}
