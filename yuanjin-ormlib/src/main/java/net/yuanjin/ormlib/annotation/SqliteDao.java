package net.yuanjin.ormlib.annotation;

import net.yuanjin.ormlib.BaseSqliteDALEx;

import java.util.HashMap;
import java.util.Map;

/**
 *  缓存对象模型
 *  Created by WuZhanQiang on 2017/1/3.
 */

public class SqliteDao {

    private static Map<Class<? extends BaseSqliteDALEx>,BaseSqliteDALEx> daos
                        = new HashMap<Class<? extends BaseSqliteDALEx>, BaseSqliteDALEx>();

    public static <T extends BaseSqliteDALEx> T getDao(){

    }
}
