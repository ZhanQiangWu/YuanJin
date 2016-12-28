package net.yuanjin.mytest.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *  Created by zhan on 2016/12/27.
 */

public class BaseSqliteOpenHelper extends SQLiteOpenHelper{

    public BaseSqliteOpenHelper(Context context,String dbName,int dbVersion) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 添加操作
     * @param insertSql 对应插入字段，如：insert into person(name,age) values(?,?)
     * @param obj 对应值，如：new Object[]{person.getName(),person.getAge()};
     * @return
     */
    public boolean save(String insertSql,Object obj[]){

    }

    /**
     * 添加操作
     * @param tableName 表名
     * @param values 集合对象
     * @return
     */
    public boolean save(String tableName, ContentValues values){

    }

    /**
     * 更新操作
     * @param table
     * @param values
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public boolean update(String table ,ContentValues values,String whereClause,String [] whereArgs){

    }

    /**
     * 删除操作
     * @param table 表名
     * @param deleteSql 对应更新字段，如： "where personid = ?"
     * @param obj [] 对应值，如： new Object[]{person.getPersonId()};
     * @return
     */
    public boolean delete(String table,String deleteSql,String obj[]){

    }

    /**
     * 查询操作
     * @param findSql 对应查询字段，如:"select * from person limit ?,?"
     * @param obj [] 对应值，如：new Object[]{String.valueOf(fristResult),String.valueOf(
     *            maxResult)}
     * @return
     */
    public Cursor find(String findSql,String obj[]){

    }
}
