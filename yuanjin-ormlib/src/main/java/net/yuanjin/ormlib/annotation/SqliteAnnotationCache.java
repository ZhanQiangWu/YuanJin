package net.yuanjin.ormlib.annotation;

import net.yuanjin.ormlib.BaseSqliteDALEx;

import java.util.HashMap;

/**
 *  数据库 缓存数据表对象
 *  Created by WuZhanQiang on 2017/1/4.
 */

public class SqliteAnnotationCache {

    HashMap<String,SqliteAnnotationTable> tableCache = new HashMap<String, SqliteAnnotationTable>();

    public synchronized SqliteAnnotationTable getTable(String tableName, Class<? extends BaseSqliteDALEx> clazz){
        SqliteAnnotationTable table = tableCache.get(tableName);

        if(table == null){
            table = new SqliteAnnotationTable(tableName,clazz);
            tableCache.put(table.getTableName(), table);
        }

        return table;
    }

    public void clear(){
        tableCache.clear();
    }

}
