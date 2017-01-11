package net.yuanjin.common.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;


import net.yuanjin.common.constant.Constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *  Created by WuZhanQiang on 2016/9/12.
 */
public class PhotoUtils {

    public static String getBasePath() {
        return Constant.IMAGEPATH ;
    }

    /**
     * 由文件名称获取文件路径名
     * @param pic_name ：文件名称
     * @return String ：文件路径名
     */
    public static String getPhotoPath(String pic_name) {
        // TODO: 2016/9/14  pictureFormat
        return getBasePath() + "/" + pic_name + ".jpg";
    }

    /**
     * 通过文件名获取bitmap对象（bitmap可压缩）
     * @param path ：文件路径名
     * @param REQUIRED_SIZE ：压缩后图片的最短边的最大值，eg：REQUIRED_SIZE=200 > min(长=400，宽=198)
     * @return
     */
    public static Bitmap decodeUriAsBitmap(String path , final int REQUIRED_SIZE){
        File file=new File(path);
        if (file.isDirectory()){
            return null;
        }
        BitmapFactory.Options option=new BitmapFactory.Options();
        option.inJustDecodeBounds=true;//不返回实际bitmap但可获取图片宽高信息
        BitmapFactory.decodeFile(file.getAbsolutePath(),option);
        int width_tmp=option.outWidth;
        int height_tmp = option.outHeight;
        int scale =1;
        while (true){
            if (width_tmp/2 <REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE){
                break;
            }
            width_tmp/=2;
            width_tmp/=2;
            height_tmp/=2;
            scale*=2;
        }
        // decode with inSampleSize
        option.inSampleSize=scale;
        option.inJustDecodeBounds=false;//可返回bitmap，以读取图片
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),option);
        return bitmap;
    }

    /**
     * 通过文件名获取bitmap对象（bitmap不压缩）
     * @param path ：文件路径名
     * @return Bitmap
     */
    public static Bitmap decodeUriAsBitmap(String path ){
        return decodeUriAsBitmap(path ,1000000);
    }

    /**
     * 读取图片属性
     * @param path ：图片绝对路径
     * @return degree ：旋转的角度
     */
    public static int readPictureDegree(String path){
        int degree =0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation =exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation){
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree=90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree=180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree=270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return degree;
    }

    /**
     * 保存bitmap到sd卡filePath文件中 如果有，则删除
     * @param bitmap
     * @param filePath :图片绝对路径
     * @return boolean :是否成功
     */
    public static boolean saveBitmap2file(Bitmap bitmap, String filePath){
        if (bitmap==null){
            return false;
        }
        //压缩格式
        CompressFormat format = CompressFormat.JPEG;
        int quality =100;
        OutputStream stream=null;
        File file=new File(filePath);
        File dir=file.getParentFile();
        if (!dir.exists()){
            dir.mkdirs();//创建父目录
        }
        if (file.exists()){
            file.delete();
        }

        try {
            stream = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return  bitmap.compress(format,quality,stream);
    }

    /**
     * 通过图片的Uri进行裁剪操作，再在onActivityResult对返回数据处理
     * @param activity ：图片所在页面
     * @param uri ：图片Uri
     * @param outputX :输出宽
     * @param outputY   ：输出高
     * @param requestCode ：请求标识
     */
    public static void cropImageUri(Activity activity, Uri uri, int outputX, int outputY, int requestCode){

    }
}
