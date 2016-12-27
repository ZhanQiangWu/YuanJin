package net.yuanjin.ormlib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *  数据库
 *  Created by WuZhanQiang on 2016/12/27.
 */

public class BaseSqliteOpenHelper extends SQLiteOpenHelper{

    public BaseSqliteOpenHelper(Context context,String dbName,int dbVersion) {
        super(context, dbName, null, dbVersion);
        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
