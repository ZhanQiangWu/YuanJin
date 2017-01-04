package net.yuanjin.common.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 *  Created by WuZhanQiang on 2017/1/4.
 */

public class CommonUtil {

    /**
     * 获取当前数据库版本
     * @param context
     * @return
     */
    public static int getDBVersion(Context context){
        ApplicationInfo appInfo;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                                                            PackageManager.GET_META_DATA);
            // TODO: 2017/1/4 到时候断点查看appInfo具体详情
            return appInfo.metaData.getInt("dbversion");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 1;
        }
    }
}
