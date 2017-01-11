package net.yuanjin.widgetlib.photolib;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.yuanjin.common.utils.PhotoUtils;
import net.yuanjin.widgetlib.R;
import net.yuanjin.widgetlib.XtionBasicActivity;


/**
 *  Created by WuZhanQiang on 2016/9/12.
 */
public class Sample_Photolib extends XtionBasicActivity implements View.OnClickListener{

    ImageView imageView;

    ImageView CameraImageView;

    ImageView albumImageView;

    TextView cropCameraTextview;

    TextView albumTextView;

    Button btn_cropcamera,btn_camera,btn_selectalbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photolib);

        imageView = (ImageView) findViewById(R.id.imagetest);
        CameraImageView = (ImageView) findViewById(R.id.iv_cropcamera);
        albumImageView = (ImageView) findViewById(R.id.iv_cropalbum);
        cropCameraTextview = (TextView) findViewById(R.id.tv_cropcamera);
        albumTextView = (TextView) findViewById(R.id.tv_cropalbum);
        //测试
//        ImageLoader imageLoader= ImageLoader.getInstance();
//        imageLoader.displayImage("assets://headimg/F01.png",imageView);
        btn_camera = (Button) findViewById(R.id.btn_camera);
        btn_cropcamera = (Button) findViewById(R.id.btn_cropcamera);
        btn_selectalbum = (Button) findViewById(R.id.btn_selectalbum);

        btn_camera.setOnClickListener(this);
        btn_cropcamera.setOnClickListener(this);
        btn_selectalbum.setOnClickListener(this);
    }

    //拍照+截图

    void cropCameraPhoto() {

        XtionPhotoService.cropCameraPhoto(this, true, new XtionPhotoService.OnPhotoListener() {
            @Override
            public void onSelectedAlbum(String[] ids, String[] uris) {

            }

            @Override
            public void onCamera(String pic_name, String uri) {
                Bitmap bitmap = PhotoUtils.decodeUriAsBitmap(uri,200);
                CameraImageView.setImageBitmap(bitmap);
                cropCameraTextview.setText("图片路径："+uri);
                //Toast.makeText(Sample_Photolib.this, pic_name, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_cropcamera) {
            Log.i("mytest","btn_cropcamera");
            cropCameraPhoto();
        }
        if (i == R.id.btn_camera){
            Log.i("mytest","btn_camera");
            cameraPhoto();
        }
        if (i == R.id.btn_selectalbum){
            Log.i("mytest","btn_selectalbum");
            selectedPhotoFromAlbum();
        }
    }

    //拍照不截图
    void cameraPhoto(){

        XtionPhotoService.cameraPhoto(this, new XtionPhotoService.OnPhotoListener() {
            @Override
            public void onSelectedAlbum(String[] ids, String[] uris) {

            }

            @Override
            public void onCamera(String pic_name, String uri) {
                Bitmap bitmap = PhotoUtils.decodeUriAsBitmap(uri,200);
                CameraImageView.setImageBitmap(bitmap);
                cropCameraTextview.setText("图片路径："+uri);
            }
        });

    }

    /**
     * 从相册中选择图片
     */
    void selectedPhotoFromAlbum() {

//        XtionPhotoService.selectedPhotoFromAlbum(this, false, 4, new String[]{}, true, null, new XtionPhotoService.OnPhotoListener() {
//            @Override
//            public void onSelectedAlbum(String[] ids, String[] uris) {
//                Log.i("test","数量为： "+ids.length);
//                for (int i = 0;i<ids.length;i++) {
//                    Log.i("test",uris[i]);
//                }
//                Bitmap bitmap = PhotoUtils.decodeUriAsBitmap(uris[0]);
//                albumImageView.setImageBitmap(bitmap);
//                albumTextView.setText("图片路径："+uris[0]);
//            }
//
//            @Override
//            public void onCamera(String pic_name, String uri) {
//
//            }
//        });

        XtionPhotoService.selectedOnePhotoFromAlbum(this,false,new XtionPhotoService.OnPhotoListener() {
            @Override
            public void onSelectedAlbum(String[] ids, String[] uris) {
                Log.i("test","数量为： "+ids.length);
                for (int i = 0;i<ids.length;i++) {
                    Log.i("test",uris[i]);
                }
                Bitmap bitmap = PhotoUtils.decodeUriAsBitmap(uris[0]);
                albumImageView.setImageBitmap(bitmap);
                albumTextView.setText("图片路径："+uris[0]);
            }

            @Override
            public void onCamera(String pic_name, String uri) {

            }
        });
    }


//
//    void imagePagerTest(){
//
//        //初始化测试数据
//        List<ImageUri> uriList = new ArrayList<ImageUri>();
//        ImageUri uri1 = new ImageUri("http://img5.imgtn.bdimg.com/it/u=1530544751,3448105792&fm=11&gp=0.jpg",null);//网络图片
//        ImageUri uri2 = new ImageUri("assets://headimg/F01.png",null);//assets 中的图片
//        ImageUri uri3 = new ImageUri("headimg://F01.png",null);//assets 中的图片
//        uriList.add(uri1);
//        uriList.add(uri2);
//        uriList.add(uri3);
//
//        ImageUriParams params = new ImageUriParams(uriList);
//        Intent intent = new Intent(this, ImageViewPagerActivity.class);
////        Intent intent = new Intent(this, TestActivtiy.class);
//        intent.putExtra(ImageViewPagerActivity.IMAGEURIPARAMS,params);
//        startActivity(intent);
//
//
//    }

}
