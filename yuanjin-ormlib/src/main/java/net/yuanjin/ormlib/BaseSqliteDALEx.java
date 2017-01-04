package net.yuanjin.ormlib;

import android.text.TextUtils;

import net.yuanjin.ormlib.annotation.SqliteAnnotationCache;
import net.yuanjin.ormlib.annotation.SqliteAnnotationField;
import net.yuanjin.ormlib.annotation.SqliteAnnotationTable;

import java.io.Serializable;
import java.util.List;

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
    private BaseSqliteOpenHelper DB;

    public BaseSqliteDALEx(){
        if (TextUtils.isEmpty(TABLE_NAME)){
            TABLE_NAME = createTableName();
        }
    }

    /**
     * @Decription 获取数据表表名
     * @return 表名
     **/
    public String getTableName(){
        return TABLE_NAME;
    }

    public void createTable() {
        BaseSqliteOpenHelper db = getDB();
        if (!db.isTableExits(TABLE_NAME)){
            if (TextUtils.isEmpty(SQL_CREATETABLE)){
                SQL_CREATETABLE = sqlCreateTable();
            }
            if (TextUtils.isEmpty(TABLE_NAME)){
                TABLE_NAME = createTableName();
            }
            db.creatTable(SQL_CREATETABLE);
        }
    }

    /**
     * 得到构建表的sql语句
     * @return String
     */
    private String sqlCreateTable() {
        // TODO: 2017/1/4  sqlcreatetable
        if (!TextUtils.isEmpty(SQL_CREATETABLE)) return SQL_CREATETABLE;
        //遍历带注解的字段
        List<SqliteAnnotationField> safs = getSqliteAnnotationField();
        return null;
    }

    /**
     * 获得 数据表所有字段的注解集合
     * @return
     */
    private List<SqliteAnnotationField> getSqliteAnnotationField(){
        SqliteAnnotationCache cache = YuanJinOrmManager.getInstance().getSqliteAnnotationCache();
        SqliteAnnotationTable table = cache.getTable(TABLE_NAME,this.getClass());
        return table.getFields();
    }

    /**
     * 获取当前数据库对象
     * @return 当前DB BaseSqliteOpenHelper
     */
    public BaseSqliteOpenHelper getDB() {
        return YuanJinOrmManager.getInstance().getSqliteHelper();
    }

    /**
     * 功能描述：创建数据表名称
     */
    private String createTableName() {
        // TODO: 2016/12/27 此处需要验证一下
        return DefaultTableName + this.getClass().getSimpleName();
    }
}
