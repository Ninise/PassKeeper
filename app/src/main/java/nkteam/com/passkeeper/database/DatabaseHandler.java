package nkteam.com.passkeeper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {

    private static final int DATABASE_VERSION = 3;

    private static final String DATABASE_NAME = "dataPassKeeper";
    private static final String TABLE_PASSES = "datapass";

    private static final String KEY_ID = "_id";
    private static final String KEY_URL = "url";
    private static final String KEY_LOGIN = "login";
    private static final String KEY_PASS = "pass";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_EXTRA = "extra";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PASSES_TABLE = "CREATE TABLE " + TABLE_PASSES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_URL + " TEXT,"
                + KEY_LOGIN + " TEXT,"
                + KEY_PASS + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_EXTRA + " TEXT" + ")";
        db.execSQL(CREATE_PASSES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PASSES);
        onCreate(db);
    }

    @Override
    public void addDataPass(PKDataModel pkDataModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_URL, pkDataModel.getUrl());
        values.put(KEY_LOGIN, pkDataModel.getLogin());
        values.put(KEY_PASS, pkDataModel.getPass());
        values.put(KEY_EMAIL, pkDataModel.getEmail());
        values.put(KEY_EXTRA, pkDataModel.getExtra());

        db.insert(TABLE_PASSES, null, values);
        db.close();
    }

    @Override
    public PKDataModel getDataPass(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_PASSES,
                                new String[] {
                                        KEY_ID,
                                        KEY_URL,
                                        KEY_LOGIN,
                                        KEY_PASS,
                                        KEY_EMAIL,
                                        KEY_EXTRA
                                },
                                KEY_ID + "=?",
                                new String[] { String.valueOf(id) },
                                null,
                                null,
                                null,
                                null );

        if (cursor != null) {
            cursor.moveToFirst();
        }

        assert cursor != null;
        PKDataModel pkDataModel = new PKDataModel(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5));

        return pkDataModel;
    }

    @Override
    public List<PKDataModel> getAllDataPasses() {
        List<PKDataModel> dataPassesList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PASSES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                PKDataModel pkDataModel = new PKDataModel();
                pkDataModel.setId(Integer.parseInt(cursor.getString(0)));
                pkDataModel.setUrl(cursor.getString(1));
                pkDataModel.setLogin(cursor.getString(2));
                pkDataModel.setPass(cursor.getString(3));
                pkDataModel.setEmail(cursor.getString(4));
                pkDataModel.setExtra(cursor.getString(5));
                dataPassesList.add(pkDataModel);
            } while (cursor.moveToNext());
        }
        Log.d("Database", "IT WORK! GET ALL");
        return dataPassesList;
    }

    @Override
    public int updateDataPass(PKDataModel pkDataModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_URL, pkDataModel.getUrl());
        values.put(KEY_LOGIN, pkDataModel.getLogin());
        values.put(KEY_PASS, pkDataModel.getPass());
        values.put(KEY_EMAIL, pkDataModel.getEmail());
        values.put(KEY_EXTRA, pkDataModel.getExtra());

        return db.update(
                TABLE_PASSES,
                values,
                KEY_ID + "=?",
                new String[] { String.valueOf(pkDataModel.getId()) }
        );
    }

    @Override
    public void deleteDataPass(PKDataModel pkDataModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PASSES,
                KEY_ID + " = ?",
                new String[] { String.valueOf(pkDataModel.getId()) });
        db.close();
    }

    @Override
    public void deleteAllDataPasses() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PASSES, null, null);
        db.close();
        Log.d("Database", "IT WORK! DELETE ALL");
    }


}
