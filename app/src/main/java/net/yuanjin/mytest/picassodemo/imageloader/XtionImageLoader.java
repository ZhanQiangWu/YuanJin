package net.yuanjin.mytest.picassodemo.imageloader;

import android.content.Context;

/**
 *  Created by WuZhanQiang on 2017/1/6.
 */

public class XtionImageLoader {

    public static volatile XtionImageLoader instance;

    final Context context;//应用上下文

    /**
     * XtionImageLoader 单例初始化
     * @param context 应用上下文
     * @return XtionImageLoader
     */
    public static XtionImageLoader with(Context context){
        if (instance == null){
            synchronized (XtionImageLoader.class){
                if (instance == null){
                    instance = new XtionImageLoader(context);
                }
            }
        }
        return instance;
    }

    private XtionImageLoader(Context context){
        this.context = context.getApplicationContext();
    }

    public XtionImageLoaderOption load(String path){
        return new XtionImageLoaderOption(this,path);
    }
}
