package net.yuanjin.widgetlib.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

/**
 *  Created by WuZhanQiang on 2017/1/6.
 */

public class XtionImageLoader {

    public static volatile XtionImageLoader instance;

    final Context context ;//应用上下文

    /**
     * XtionImageLoader 单例初始化
     * @return XtionImageLoader
     */
    public static XtionImageLoader getInstance( ){
        if (instance != null){
            return instance;
        } else {
            Log.e("XtionImageLoader","XtionImageLoader should be inited First in ApplicationFile!");
            return null;
        }
    }

    /**
     * XtionImageLoader 初始化,在Application中
     * @param context 应用上下文
     */
    public static void init(Context context){
        if (instance == null){
            synchronized (XtionImageLoader.class){
                if (instance == null){
                    instance = new XtionImageLoader(context);
                }
            }
        }
    }

    private XtionImageLoader(Context context){
        this.context = context.getApplicationContext();
    }

    public XtionImageLoaderOption load(String path){
        return new XtionImageLoaderOption(this,path);
    }

//    /**
//     * 通过服务端路径获取本地缓存
//     * @param path 图片的服务端路径
//     * @return
//     */
//    public Bitmap getDiskCache(String path){
//        return new XtionImageLoaderOption(this).getDiskCache(path);
//    }

}
