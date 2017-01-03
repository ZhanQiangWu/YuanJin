package net.yuanjin.ormlib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    /**
     * 添加操作
     * @param insertSql 对应插入字段，如：insert into person(name,age) values(?,?)
     * @param obj 对应值，如：new Object[]{person.getName(),person.getAge()};
     * @return
     */
    public boolean save(String insertSql,Object obj[]){
        try {
            getWritableDatabase().execSQL(insertSql, obj);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 添加操作
     * @param tableName 表名
     * @param values 集合对象
     * @return
     */
    public boolean save(String tableName, ContentValues values){
        try {
            getWritableDatabase().insert(tableName, null, values);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 更新操作
     * @param table
     * @param values
     * @param whereClause
     * @param whereArgs
            ContentValues values = new ContentValues();
            values.put(“name”, “传智播客”);//key为字段名，value为值
            db.update("person", values, "personid=?", new String[]{"1"});
     * @return
     */
    public boolean update(String table ,ContentValues values,String whereClause,String [] whereArgs){
        try {
            int a = getWritableDatabase().update(table, values, whereClause, whereArgs);
            return a > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 删除操作
     * @param table 表名
     * @param deleteSql 对应更新字段，如： "where personid = ?"
     * @param obj [] 对应值，如： new Object[]{person.getPersonId()};
     * @return
     */
    public boolean delete(String table,String deleteSql,String obj[]){
        try {
            getWritableDatabase().delete(table, deleteSql, obj);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 查询操作
     * @param findSql 对应查询字段，如:"select * from person limit ?,?"
     * @param obj [] 对应值，如：new Object[]{String.valueOf(fristResult),String.valueOf(
     *            maxResult)}
     * @return
     */
    public Cursor find(String findSql, String obj[]){
        try {
            Cursor cursor = getReadableDatabase().rawQuery(findSql, obj);
            return cursor;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 创建数据表
     * @param createTableSql ：对应的创建语句
     *                       CREATE TABLE IF NOT EXISTS 表名
     *                       (
     *                          'id' integer primary key autoincrement,
     *                          `accountno` VARCHAR,
     *                          `address` VARCHAR,
     *                          `birthday` VARCHAR,
     *                          `city` VARCHAR,
     *                          `country` VARCHAR,
     *                          `qq` VARCHAR,
     *                       )
     * @return
     */
    public boolean creatTable(String createTableSql){
        try {
            getWritableDatabase().execSQL(createTableSql);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除表
     * @param tableName 表名
     * @return
     */
    public boolean deleteTable(String tableName){
        try{
            String sql = "DROP TABLE IF EXISTS "+ tableName;
            getWritableDatabase().execSQL(sql);
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断表是否存在
     * @param tableName 表名
     * @return
     */
    public boolean isTableExits(String tableName){
        Cursor cursor = null;
        try{
            String str = "select count(*) xcount from " + tableName;
            cursor = getReadableDatabase().rawQuery(str,null);
            if (null != cursor && cursor.moveToFirst()){
                return true;
            }
        }catch (Exception ex){
            return false;
        }finally {
            if (null != cursor){
                cursor.close();
            }
        }
        return false;

    }

    /**
     * 获取SQLite数据库连接
     * @return SQLiteDatabase
     */
    public SQLiteDatabase getConnection(){
        return getWritableDatabase();
    }

    /**
     * 执行sql
     * @param sql
     * @return
     */
    public boolean execSQL(String sql){
        try{
            getWritableDatabase().execSQL(sql);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
