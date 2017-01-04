package net.yuanjin.ormlib;

import android.content.Context;

import net.yuanjin.common.utils.CommonUtil;
import net.yuanjin.ormlib.annotation.SqliteAnnotationCache;

import java.util.HashMap;
import java.util.Map;

/**
 *  管理数据库对象以及缓存对象（改善由于反射造成的性能）
 *  Created by WuZhanQiang on 2017/1/4.
 */

public class YuanJinOrmManager {

    private static volatile YuanJinOrmManager sInstance = null;
    private Context context;
    private Map<String,BaseSqliteOpenHelper> localDBMap = new HashMap<String, BaseSqliteOpenHelper>();
    private String yuanjin_dbname = "yuanjindb";//默认数据库名字
    private SqliteAnnotationCache sqliteAnnotationCache;


    public YuanJinOrmManager(Context context){
        this.context = context;
    }

    /**
     * 设置当前数据库名字(当前需要操作哪个数据库)
     * @param dbName 数据库名字
     */
    public void setCurrentDBName(String dbName){
        this.yuanjin_dbname = dbName;
    }

    /**
     * 初始化Orm缓存（必须在Application里面调用）
     * @param context ApplicationContext
     */
    public static void init(Context context){
        if (sInstance == null){
            synchronized(YuanJinOrmManager.class){
                if (sInstance == null){
                    sInstance = new YuanJinOrmManager(context);
                }
            }
        }
    }

    /**
     * 获取 YuanJinOrmManager 实例
     * @return YuanJinOrmManager
     */
    public static YuanJinOrmManager getInstance(){
        return sInstance;
    }

    /**
     * 根据数据库名字来获取数据库，如果不存在就创建，并且数据库会切换到当前名字
     * @param dbName
     * @return BaseSqliteOpenHelper
     */
    public BaseSqliteOpenHelper getSQLiteHelperByDBName(String dbName){
        this.yuanjin_dbname = dbName;
        return getSqliteHelper();
    }

    /**
     * 单例 获得 BaseSqliteOpenHelper 实例
     * @return
     */
    BaseSqliteOpenHelper getSqliteHelper(){
        BaseSqliteOpenHelper db = localDBMap.get(yuanjin_dbname);
        if(db == null){
            synchronized (YuanJinOrmManager.class){
                if(db == null){
                    db = new BaseSqliteOpenHelper(context, yuanjin_dbname, CommonUtil.getDBVersion(context));
                    localDBMap.put(yuanjin_dbname, db);
                }
            }
        }
        return db;
    }

    /**
     * 单例 获得 SqliteAnnotationCache 实例
     * @return
     */
    public SqliteAnnotationCache getSqliteAnnotationCache(){
        if (sqliteAnnotationCache == null){
            synchronized (this){
                if (sqliteAnnotationCache == null){
                    sqliteAnnotationCache = new SqliteAnnotationCache();

                }
            }
        }
        return sqliteAnnotationCache;
    }

//    /**
//     * 关闭所有数据库（退出应用必须调用）
//     */
//    public void closeDB(){
//        try {
//            for(BaseSqliteOpenHelper db:localDBMap.values()){
//                db.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        clearCache();
//    }
//
//    /**
//     * 功能描述：退出应用必须要调用该方法，用于清除所有缓存对象
//     */
//    private void clearCache(){
//
//    }




}
