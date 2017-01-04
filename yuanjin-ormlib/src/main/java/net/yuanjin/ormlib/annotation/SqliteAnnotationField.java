package net.yuanjin.ormlib.annotation;

import android.text.TextUtils;

import java.lang.reflect.Field;

/**
 *  数据表 注解字段的详细信息
 *  reated by WuZhanQiang on 2017/1/4.
 */

public class SqliteAnnotationField {

    //对象字段
    private Field field;

    //字段的注解
    private DataBaseField annotation;

    //字段注解中声明的类型
    private DataBaseField.FieldType type;

    //字段是否为表的主键
    private boolean primarykey;

    //字段在数据库表中的列名
    private String columnName;

    public SqliteAnnotationField(Field field,DataBaseField annotation){
        this.field = field;
        this.annotation = annotation;
        this.type = annotation.Type();
        this.primarykey = annotation.primaryKey();
        this.columnName = annotation.fieldName();

        if (TextUtils.isEmpty(columnName)){
            this.columnName = field.getName();
        }
    }

    /**
     * 获取字段对应的注解
     * @return DataBaseField
     */
    public DataBaseField getAnnotation() {
        return annotation;
    }

    public DataBaseField.FieldType getType() {
        return type;
    }

    public Field getField() {
        return field;
    }

    /**
     * 获取该字段在表中的列名
     * @return String
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * 判断当前字段是否是主键字段
     */
    public boolean isPrimarykey() {
        return primarykey;
    }

    public void setPrimarykey(boolean primarykey) {
        this.primarykey = primarykey;
    }
}
