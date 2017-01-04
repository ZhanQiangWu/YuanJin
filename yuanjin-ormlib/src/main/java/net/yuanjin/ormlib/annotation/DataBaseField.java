package net.yuanjin.ormlib.annotation;

/**
 *  数据表 字段描述注解
 *  Created by WuZhanQiang on 2017/1/4.
 */

public @interface DataBaseField {

    //字段类型
    FieldType Type() default FieldType.VARCHAR;

    //字段是否为主键
    boolean primaryKey() default false;

    //字段名，默认与成员变量相同
    String fieldName() default "";

    enum FieldType{
        VARCHAR,INT,REAL
    }
}
