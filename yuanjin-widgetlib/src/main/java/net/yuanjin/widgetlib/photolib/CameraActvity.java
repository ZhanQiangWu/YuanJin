package net.yuanjin.widgetlib.photolib;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import net.yuanjin.common.constant.Constant;
import net.yuanjin.common.utils.FileUtils;
import net.yuanjin.common.utils.PhotoUtils;
import net.yuanjin.widgetlib.R;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 缓冲Activity，进行数据传递
 * Created by WuZhanQiang on 2016/9/14.
 */
public class CameraActvity extends AppCompatActivity {

    //临时照片文件名
    public  static String pic_name = "";
    public final static int ActivityResult_CAMERA_WITH_DATA = 11111;
    public final static int ActivityResult_CAMERA_CROP_WITH_DATA = 12121;
    public final static int ActivityResult_CROP_SMALL_PICTURE = 22222;

    public final static String ActivityResult_PicName = "pic_name";//返回图片名称


    private boolean isCrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Intent intent = getIntent();
        isCrop = intent.getBooleanExtra("isCrop",false);

        pic_name=cropCameraPhoto(isCrop);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("onSaveInstanceState");
        outState.putString("pic_name",pic_name);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        System.out.println("onRestoreInstanceState");
        pic_name=savedInstanceState.getString("pic_name");
    }

    /**
     * 拍照，返回临时照片地址
     *
     * @return String ：照片名称
     */
    @TargetApi(Build.VERSION_CODES.M)
    private String cropCameraPhoto(boolean isCrop) {
        int MaxLimit = 10;
        int retianMB = FileUtils.freeSpaceOnSd();
        if (retianMB < MaxLimit) {
            //onToastErrorMsg("存储空间不足" + MaxLimit + "M，未能获取图片");
            Toast.makeText(this, "存储空间不足" + MaxLimit + "M，未能获取图片", Toast.LENGTH_SHORT).show();
            return "";
        }
        String tempImage = null;

        try {
            tempImage = UUID.randomUUID().toString();
            File root = new File(Constant.IMAGEPATH);
            if (!root.exists()) {
                root.mkdir();
            }
            File directory = new File(PhotoUtils.getBasePath());
            if (!directory.exists()) {
                directory.mkdir();
            }
            File picFile = new File(PhotoUtils.getPhotoPath(tempImage));
            if (!picFile.exists()) {
                picFile.createNewFile();
            }
            Uri photoUri= Uri.fromFile(picFile);
            Intent cameraIntent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);

            if (Build.VERSION.SDK_INT>13){//4.0以上
                cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,0);//低质量
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
            if (isCrop){
                startActivityForResult(cameraIntent, CameraActvity.ActivityResult_CAMERA_CROP_WITH_DATA);//拍照返回后跳至裁剪
            }else {
                startActivityForResult(cameraIntent, CameraActvity.ActivityResult_CAMERA_WITH_DATA);//拍照返回
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempImage;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            /**
             * 拍照返回后跳至裁剪
             */
            case CameraActvity.ActivityResult_CAMERA_CROP_WITH_DATA:
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (resultCode==RESULT_OK){
                    Bitmap bitmap = PhotoUtils.decodeUriAsBitmap(PhotoUtils.getPhotoPath(pic_name),200);
                    int degree =PhotoUtils.readPictureDegree(PhotoUtils.getPhotoPath(pic_name));
                    //根据旋转角度，生成旋转矩阵
                    Matrix matrix=new Matrix();
                    matrix.setRotate(degree);
                    //旋转
                    int width=bitmap.getWidth();
                    int height=bitmap.getHeight();
                    bitmap= Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
                    PhotoUtils.saveBitmap2file(bitmap,PhotoUtils.getPhotoPath(pic_name));
                    Uri uri= Uri.fromFile(new File(PhotoUtils.getPhotoPath(pic_name)));

                    if (isCrop){
                        //转至截图
                        cropImageUri(uri,250,250, CameraActvity.ActivityResult_CROP_SMALL_PICTURE);
                    }else{
                        //返回
                        setResultBack();
                    }

                }
                break;
            /**
             * 裁剪返回
             */
            case CameraActvity.ActivityResult_CROP_SMALL_PICTURE:
                if (resultCode==RESULT_OK){
                    Bitmap bitmap= (Bitmap) data.getExtras().get("data");
                    if (PhotoUtils.saveBitmap2file(bitmap,PhotoUtils.getPhotoPath(pic_name))){//bitmap存储成file
                        setResultBack();//数据返回并关闭页面
                    }
                }
                break;

            /**
             * 拍照返回
             */
            case CameraActvity.ActivityResult_CAMERA_WITH_DATA:
                try {
                    Thread.sleep(100);
                    if (resultCode == RESULT_OK) {
                        String path = PhotoUtils.getPhotoPath(pic_name);
                        Bitmap bitmap = PhotoUtils.decodeUriAsBitmap(path, 400);
                        int degree = PhotoUtils.readPictureDegree(PhotoUtils.getPhotoPath(pic_name));
                        Matrix matrix = new Matrix();
                        matrix.setRotate(degree);
                        // 旋转
                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();

                        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
                        PhotoUtils.saveBitmap2file(bitmap, PhotoUtils.getPhotoPath(pic_name));
                        setResultBack();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            default :
                break;
        }
    }
//    protected FileDALEx fileDALEx;

    /**
     * 图片截取
     * @param uri :图片文件的Uri
     * @param outputX   ：裁剪图片的宽
     * @param outputY   ：裁剪图片的高
     * @param requestCode   ：int
     */
    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent=new Intent("com.android.camera.action.CROP");//调用系统裁剪功能
        intent.setDataAndType(uri,"image/*");
        //在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop","true");
        // aspectX aspectY 是宽高的比例，这里设置的是正方形（长宽比为1:1）
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX",outputX);
        intent.putExtra("outputY",outputY);
        //裁剪时是否保留图片的比例，这里的比例是1:1
        intent.putExtra("scale",true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,PhotoUtils.getPhotoPath(pic_name));
        //是否将数据保留在Bitmap中返回
        intent.putExtra("return-data",true);
        //设置输出的格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection",true);
        intent.putExtra("scaleUpIfNeeded", "true");
        startActivityForResult(intent,requestCode);
    }


    private  void setResultBack(){
        Intent intent = new Intent();
        intent.putExtra(ActivityResult_PicName,pic_name);
        setResult(RESULT_OK,intent);
        finish();
    }

}
