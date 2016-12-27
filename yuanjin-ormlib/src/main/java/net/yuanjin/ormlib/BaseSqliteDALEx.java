package net.yuanjin.ormlib;

import android.text.TextUtils;

import java.io.Serializable;

/**
 *  @author Created by WuZhanQiang on 2016/12/27.
 *  @description 数据表对象的基类
 */

public abstract class BaseSqliteDALEx implements Serializable,Cloneable{

    private static final long serialVersionUID = 1L;
    private static String DefaultTableName = "yuanjin_t_";
    protected String TABLE_NAME = "";
    protected String SQL_CREATETABLE = "";
    protected int indexId;

    public BaseSqliteDALEx(){
        if (TextUtils.isEmpty(TABLE_NAME)){
            TABLE_NAME = createTableName();
        }
    }

    /**
     * 功能描述：创建数据表名称
     */
    private String createTableName() {
        // TODO: 2016/12/27 此处需要验证一下
        return DefaultTableName + this.getClass().getSimpleName();
    }

    /**
     * @Decription 获取数据表表名
     * @return 表名
     **/
    public String getTableName(){
        return TABLE_NAME;
    }
}
