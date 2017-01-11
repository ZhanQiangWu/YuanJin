package net.yuanjin.widgetlib.photolib;

import android.content.Intent;

import net.yuanjin.common.utils.PhotoUtils;
import net.yuanjin.widgetlib.XtionBasicActivity;
import net.yuanjin.widgetlib.photolib.album.AlbumIndexActivity1;


/**
 * 图片相册操作
 * Created by WuZhanQiang on 2016/9/12.
 */
public class XtionPhotoService {

    public static OnPhotoListener listener;

    /**
     * 拍照+截图，返回临时照片地址
     *
     * @param activity  :XtionBaseActivity
     * @param isCrop    ：是否截图
     * @param onPhotoListener   ：图片信息返回接口
     */
    public static void cropCameraPhoto(XtionBasicActivity activity, boolean isCrop, OnPhotoListener onPhotoListener) {
        listener = onPhotoListener;

        //先进入缓冲Activity
        Intent intent =new Intent(activity,CameraActvity.class);
        intent.putExtra("isCrop",isCrop);
        activity.startActivityForListener(intent, new XtionBasicActivity.OnActivityResultListener() {

            @Override
            public void onResult(Intent result) {
                String pic_name =  result.getStringExtra(CameraActvity.ActivityResult_PicName);//文件名
                String pic_ComplePath= PhotoUtils.getPhotoPath(pic_name);//全路径
                listener.onCamera(pic_name,pic_ComplePath);
            }
        });
    }

    /**
     * 仅拍照，返回临时照片地址
     * @param activity :XtionBasicActivity
     * @param onPhotoListener : 图片信息返回接口
     */
    public static void cameraPhoto(XtionBasicActivity activity, OnPhotoListener onPhotoListener) {
        cropCameraPhoto(activity,false,onPhotoListener);
    }

    public interface OnPhotoListener{
        //public void onCropCamera(Bitmap bitmap,FileDALEx fileDALEx);
        //public void onCropAlbum(Bitmap bitmap,FileDALEx fileDALEx);
        public void onSelectedAlbum(String[] ids, String[] uris);
        public void onCamera(String pic_name, String uri);
    }

    /**
     * 从相册选取照片并返回临时照片路径
     * @param activity :XtionBasicActivity 调用相册功能页面
     * @param isMultiChoise ：boolean 是否多选
     * @param limit int :图片选择数量最大值
     * @param uris
     * @param isCrop ：boolean 是否截图
     * @param labelId
     * @param onPhotoListener
     */
    public static void selectedPhotoFromAlbum(XtionBasicActivity activity, boolean isMultiChoise, int limit , String[] uris, boolean isCrop, String labelId, OnPhotoListener onPhotoListener){
        listener = onPhotoListener;
        Intent intent=new Intent(activity,AlbumIndexActivity1.class);
        intent.putExtra(AlbumIndexActivity1.Tag_isMutiChoice,isMultiChoise);
        intent.putExtra(AlbumIndexActivity1.Tag_maxlimit,limit);
        intent.putExtra(AlbumIndexActivity1.Tag_uris,uris);
        intent.putExtra(AlbumIndexActivity1.Tag_isCrop,isCrop);
//        if (isCrop){
            // 选择照片
        activity.startActivityForListener(intent, new XtionBasicActivity.OnActivityResultListener() {
            @Override
            public void onResult(Intent result) {
                String[] ids = result.getStringArrayExtra(AlbumIndexActivity1.Tag_ids);//被选择图片的name集
                String[] uris = result.getStringArrayExtra(AlbumIndexActivity1.Tag_uris);//被选择图片的 uri集合
                listener.onSelectedAlbum(ids,uris);
//                    data.putExtra(Tag_ids,ids.toArray(new String[ids.size()]));
//                    data.putExtra(Tag_uris, uris.toArray(new String[uris.size()]));
//                    data.putExtra(Tag_labelId, labelId);

            }
        });
//        }else {//不裁剪
//            activity.startActivityForListener(intent, new XtionBasicActivity.OnActivityResultListener() {
//                @Override
//                public void onResult(Intent result) {
//
//                }
//            });
//        }
    }

    /**
     * 从相册中挑选一张图片
     * @param activity ：XtionBasicActivity 调用相册功能页面
     * @param isCrop :boolean 是否截图
     * @param onPhotoListener  ：图片信息返回监听
     */
    public static void selectedOnePhotoFromAlbum(XtionBasicActivity activity,boolean isCrop,OnPhotoListener onPhotoListener){
        selectedPhotoFromAlbum(activity, false, 1, null, isCrop, null, onPhotoListener);
    }

    // TODO: 2016/10/9  完善多张图片的接口编写



}
