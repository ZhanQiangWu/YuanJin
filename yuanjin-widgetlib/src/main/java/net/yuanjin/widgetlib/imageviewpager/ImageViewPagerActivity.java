package net.yuanjin.widgetlib.imageviewpager;
// TODO: 2016/9/21  后面看下位置放置

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.yuanjin.common.constant.Constant;
import net.yuanjin.common.utils.FileUtils;
import net.yuanjin.common.utils.PhotoUtils;
import net.yuanjin.widgetlib.R;
import net.yuanjin.widgetlib.imageloader.ImageLoadingCallBack;
import net.yuanjin.widgetlib.imageloader.XtionImageLoader;

import java.io.File;
import java.util.List;

/**
 * <p>Created by WuZhanQiang on 2016/9/21.        </p>
 *
 *  <p>ImageViewPagerActivity 图片展示页面</p>
 *  传递参数:
 *  <ul>
 *      <li>int defaultPosition : 默认显示图片的位置</li>
 *      <li>ImageUriParams params : 图片信息集合</li>
 *  </ul>
 *  在进行图片下载操作时，ImageUriParams 支持两种图片路径
 *  <ol>
 *      <li>本地路径 localPath ，eg：/storage/emulated/0/DCIM/Camera/IMG2.jpg</li>
 *      <li>非本地路径 uploadUrl
 *          <ul>
 *              <li>assets中的文件，eg："assets://headimg/F01.png"</li>
 *              <li>服务器端 eg："http://img5.imgtn.bdimg.com/11.jpg"</li>
 *          </ul>
 *      </li>
 *  </ol>
 */
public class ImageViewPagerActivity extends Activity {

    public static final String IMAGEURIPARAMS = "ImageUriParams";
    public static final String DEFAULTPOSITION = "defaultPosition";
    public static final String SAVEABLE = "saveAble";
    public static final String FULLSCREEN = "fullscreen";

//    private ImageLoader imageLoader;
    private int defaultPosition;//进入时默认显示图片的位置
    private boolean saveAble;
    private boolean fullscreen;//是否完整显示图片

    private ImageViewPager viewPager;
    private LinearLayout backContainer;
    private TextView pageIndex;
    private ImageView img_save;

    private List<ImageUri> uris;
    private PageChangeListener pageChangeListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_imageviewpager);

        //get data from intent
        ImageUriParams params = (ImageUriParams) getIntent().getSerializableExtra(IMAGEURIPARAMS);
        defaultPosition = getIntent().getIntExtra(DEFAULTPOSITION, 0);
        saveAble = getIntent().getBooleanExtra(SAVEABLE, true);
        fullscreen = getIntent().getBooleanExtra(FULLSCREEN, false);

//        imageLoader = ImageLoader.getInstance();
        viewPager= (ImageViewPager) findViewById(R.id.image_prewview_viewpager);
        backContainer = (LinearLayout) findViewById(R.id.item_prewview_backcontainer);
        pageIndex = (TextView) findViewById(R.id.item_prewview_pageidex);
        img_save = (ImageView) findViewById(R.id.item_prewview_save);

        backContainer.setAlpha(0);
        backContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (params!=null){
            uris = params.uris;

            //// TODO: 2016/9/22  测试 fullscreen为false的情况
            viewPager.setData(uris);
            viewPager.setFullscreen(fullscreen);
            viewPager.setCurrentItem(defaultPosition);
//            viewPager.setImageLoadingListener(loadingListener);
            viewPager.setImageLoadingCallBack(loadingCallBack);
            pageChangeListener = new PageChangeListener(uris);
            viewPager.addOnPageChangeListener(pageChangeListener);

        }

        //保存图片
        img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSave();
            }
        });
        pageIndex.setText(defaultPosition+ 1+ "/" +uris.size());

    }



    private CountDownTimer disMissTimer = new CountDownTimer(5000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            ImageViewPagerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    backContainer.animate().alpha(0).setDuration(400)
                            .setListener(new AnimatorListenerAdapter() {
                                public void onAnimationEnd(Animator animation) {
                                    backContainer.setVisibility(View.GONE);
                                }
                            });
                }
            });
        }

    };


    //界面触摸监听--触摸显示，5s后隐藏
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        backContainer.setVisibility(View.VISIBLE);
        backContainer.animate().alpha(1f).setDuration(400).setListener(null);
        disMissTimer.cancel();
        disMissTimer.start();
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 进行保存操作
     */
    private void doSave(){
        String path ="";
        String fileName = "";
        ImageUri imageUri = pageChangeListener.getImageUri();
        String url = imageUri.getUploadUrl();//当前图片的 服务端路径

        if (!TextUtils.isEmpty(url)){//有服务端路径
            //从ImageLoader的图片缓存中获取图片文件路径
            //File file = imageLoader.getDiskCache().get(url);

            //XtionImageLoader.getInstance().l
            // TODO: 2017/1/11 待验证此处下载到本地的图片是否成功以及名字如何
            Bitmap bitmap = XtionImageLoader.getInstance().getDiskCache(url);
            saveBitmap(bitmap);

        }else{
            //本地路径
            url = imageUri.getLocalPath();
            String[] nameArray = url.split("/");
            path = url;
            fileName = nameArray[nameArray.length-1];

            saveImage(path,fileName);
        }
    }

    /**
     * 保存图片（图片为Bitmap格式）
     */
    private void saveBitmap(final Bitmap bitmap) {
        final String saveFilePath = Constant.SAVEIMAGEPATH+"/"
                + (System.currentTimeMillis()/1000+".jpg");

        new AsyncTask<String, Integer, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                File dir = new File(Constant.PATH);
                if (!dir.exists()){
                    dir.mkdirs();
                }
                dir = new File(Constant.SAVEIMAGEPATH);
                if(!dir.exists()){
                    dir.mkdir();
                }
                File source = new File(saveFilePath);
                if (!source.exists()){//图片存在才拷贝
                    return false;
                }

                return PhotoUtils.saveBitmap2file(bitmap,saveFilePath);
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                File target = new File(saveFilePath);
                if(result && target.exists()){
                    //// TODO: 2016/9/29  要改为 Ontoast
                    Toast.makeText(ImageViewPagerActivity.this,"图片已保存至相册", Toast.LENGTH_SHORT).show();
                    //刷新相册
                    Uri localUri = Uri.fromFile(target);
                    Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
                    sendBroadcast(localIntent);
                }else{
                    Toast.makeText(ImageViewPagerActivity.this,"保存图片失败", Toast.LENGTH_SHORT).show();
                }
            }

        }.execute();
    }

    /**
     * 保存图片（该图片存在于本地或已缓存于本地）
     * @param imagePath ：被保存图片的本地地址或缓存地址
     * @param fileName ：文件名
     */
    private void saveImage(final String imagePath, String fileName){
        if(TextUtils.isEmpty(imagePath) || TextUtils.isEmpty(fileName))return;
        String[] nArray = fileName.split("\\.");
        //文件带后缀名，把后缀名修改成png
        fileName = nArray[0]+".png";
        final String targetPath = Constant.SAVEIMAGEPATH+"/"+fileName;

        new AsyncTask<String, Integer, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                File dir = new File(Constant.PATH);
                if (!dir.exists()){
                    dir.mkdirs();
                }
                dir = new File(Constant.SAVEIMAGEPATH);
                if(!dir.exists()){
                    dir.mkdir();
                }
                File source = new File(imagePath);
                if (!source.exists()){//图片存在才拷贝
                    return false;
                }
                FileUtils.Copy(source,targetPath);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                File target = new File(targetPath);
                if(result && target.exists()){
                    //// TODO: 2016/9/29  要改为 Ontoast
                    Toast.makeText(ImageViewPagerActivity.this,"图片已保存至相册", Toast.LENGTH_SHORT).show();
                    //刷新相册
                    Uri localUri = Uri.fromFile(target);
                    Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
                    sendBroadcast(localIntent);
                }else{
                    Toast.makeText(ImageViewPagerActivity.this,"保存图片失败", Toast.LENGTH_SHORT).show();
                }
            }

        }.execute();

    }



    /**
     * 保存Asset中的图片
     * @param source 文件路径 eg：headimg/F01.png ，其完整路径为 “assets://headimg/F01.png”
     * @param fileName 文件名 eg：F01.png
     */
    private void saveAssetImage(final String source, String fileName){
        final String targetPath = Constant.SAVEIMAGEPATH+"/"+fileName;
        new AsyncTask<String ,Integer,Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                File dir = new File(Constant.PATH);
                if (!dir.exists()){
                    dir.mkdirs();
                }
                dir = new File(Constant.SAVEIMAGEPATH);
                if(!dir.exists()){
                    dir.mkdir();
                }

                boolean result = FileUtils.CopyAsset(ImageViewPagerActivity.this,source,targetPath);
                return result;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                File target = new File(targetPath);
                if(result && target.exists()){
                    // TODO: 2016/9/22  等待添加 Ontoast
                    Toast.makeText(ImageViewPagerActivity.this,"图片已保存至相册", Toast.LENGTH_SHORT).show();
                    //刷新相册
                    Uri localUri = Uri.fromFile(target);
                    Intent localIntent =new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,localUri);
                    sendBroadcast(localIntent);
                }else{
                    Toast.makeText(ImageViewPagerActivity.this, "保存图片失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }


    class PageChangeListener implements ViewPager.OnPageChangeListener {
        
        private List<ImageUri> uris;
        private int position;
        public PageChangeListener(List<ImageUri> uris) {
            this.uris = uris;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            
        }

        @Override
        public void onPageSelected(int position) {
            this.position = position;
            pageIndex.setText(position + 1 + "/" + uris.size());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        public ImageUri getImageUri(){
            if (position > uris.size()){
                return null;
            }else{
                return uris.get(position);
            }
        }
    }
    
//    ImageLoadingListener loadingListener = new ImageLoadingListener() {
//        @Override
//        public void onLoadingStarted(String imageUri, View view) {
//
//        }
//
//        @Override
//        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//
//        }
//
//        @Override
//        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//
//        }
//
//        @Override
//        public void onLoadingCancelled(String imageUri, View view) {
//
//        }
//    };

    ImageLoadingCallBack loadingCallBack = new ImageLoadingCallBack() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onError() {

        }
    };
}
