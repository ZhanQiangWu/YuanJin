package net.yuanjin.ormlib.annotation;

import android.text.TextUtils;

import net.yuanjin.ormlib.BaseSqliteDALEx;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  数据表所有注解
 *  Created by WuZhanQiang on 2017/1/4.
 */

public class SqliteAnnotationTable {

    private String tableName;
    private Class<? extends BaseSqliteDALEx> clazz;
    private List<SqliteAnnotationField> fields;
    private Map<String,SqliteAnnotationField> fieldMaps;
    private String primaryKey;

    public SqliteAnnotationTable(String tableName,Class<? extends BaseSqliteDALEx> clazz){
        this.tableName = tableName;
        this.clazz = clazz;
    }

    public String getTableName() {
        return tableName;
    }

    /**
     * 获得当前表的主键列名
     * @return
     */
    public String getPrimaryKey() {
        if (TextUtils.isEmpty(primaryKey)){
            getFields();
        }
        return primaryKey;
    }

    /**
     * 获取当前表对应的所有注解字段
     * @return
     */
    public synchronized List<SqliteAnnotationField> getFields() {

        if (fields == null){

            if (fieldMaps == null){
                fieldMaps = new HashMap<String,SqliteAnnotationField>();
            }
            fields = new ArrayList<SqliteAnnotationField>();

            for (Field f:clazz.getDeclaredFields()){
                DataBaseField dbf = f.getAnnotation(DataBaseField.class);
                if (dbf != null){
                    SqliteAnnotationField saf = new SqliteAnnotationField(f,dbf);
                    fields.add(saf);
                    fieldMaps.put(saf.getColumnName(),saf);
                    if (saf.isPrimarykey()){
                        this.primaryKey = saf.getColumnName();
                    }
                }
            }
        }
        return fields;
    }

    /**
     * 通过字段名称获取 该字段的注解
     * @param columnName 字段名
     * @return SqliteAnnotationField
     */
    public SqliteAnnotationField getField(String columnName ){
        if (fieldMaps == null){
            getFields();
        }
        return fieldMaps.get(columnName);
    }


}
