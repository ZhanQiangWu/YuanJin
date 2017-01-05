package net.yuanjin.mytest.orm;

import net.yuanjin.ormlib.BaseSqliteDALEx;
import net.yuanjin.ormlib.annotation.DataBaseField;
import net.yuanjin.ormlib.annotation.DataBaseField.FieldType;

/**
 *  Created by WuZhanQiang on 2017/1/5.
 */

public class PeopleDALEx extends BaseSqliteDALEx{

    @DataBaseField(primaryKey = true,Type = FieldType.VARCHAR)
    private String peopleId;

    @DataBaseField(Type = FieldType.VARCHAR)
    private String name;

    @DataBaseField(Type = FieldType.INT)
    private int age;

    @DataBaseField(Type = FieldType.REAL)
    private float height;
}
