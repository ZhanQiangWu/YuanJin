package net.yuanjin.mytest.picassodemo.imageloader;

import java.util.Locale;

/**
 *  路径处理工具
 *  Created by WuZhanQiang on 2017/1/6.
 */

public class ImageLoaderUtils {

    public static String getUri(String imagePath){
        switch (Scheme.ofUri(imagePath)){

            case HTTP:
            case HTTPS:
                return imagePath;

            case DRAWABLE:
                return getUriFromDrawable(imagePath);

            case HEADIMG:
                return getUriFromHeadImg(imagePath);

            case ASSETS:
                return  getUriFromAssets(imagePath);

            case FILE:
                return getUriFromFile(imagePath);

            default:
                return "";
        }
    }

    private static String getUriFromFile(String imagePath) {
        return "file:///sdcard/"+ Scheme.FILE.crop(imagePath);
    }

    private static String getUriFromAssets(String imagePath) {
        return "file:///android_asset/"+ Scheme.ASSETS.crop(imagePath);
    }

    /**
     *  处理 headimg:// 开头的路径（适用于picasso）
     *  headimg://livingthing.jpg   -->   file:///android_asset/livingthing.jpg
     */
    private static String getUriFromHeadImg(String imagePath) {
        return "file:///android_asset/headimg/" + Scheme.HEADIMG.crop(imagePath);
    }

    private static String getUriFromDrawable(String imagePath) {
        String drawableIdString = Scheme.DRAWABLE.crop(imagePath);
        return null;
    }

    public enum Scheme{

        DYNAMICPLUGIN("dynamicplugin"),APPPLUGIN("appplugin"),HEADIMG("headimg"),THUM("thum"),
        DETAIL("detail"),HTTP("http"), HTTPS("https"), FILE("file"), CONTENT("content"),
        ASSETS("assets"), DRAWABLE("drawable"), UNKNOWN("");

        private String scheme;
        private String uriPrefix;//uri前缀

        Scheme(String scheme){
            this.scheme = scheme;
            this.uriPrefix = scheme + "://";
        }

        public static Scheme ofUri(String uri){
            if (uri != null){
                for (Scheme s : values()){
                    if (s.belongsTo(uri)){
                        return s;
                    }
                }
            }
            return UNKNOWN;
        }
        /**
         * 判断 Uri 开头字段是否满足
         * @param uri
         */
        private boolean belongsTo(String uri){
            return uri.toLowerCase(Locale.US).startsWith(uriPrefix);
        }

        /** Removed scheme part ("scheme://") from incoming URI */
        public String crop(String uri){
            return uri.substring(uriPrefix.length());
        }

        public String wrap(String path){
            return uriPrefix + path;
        }
    }
}
