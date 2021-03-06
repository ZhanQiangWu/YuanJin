package net.yuanjin.mytest.picassodemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import net.yuanjin.R;
import net.yuanjin.mytest.recycleviewdemo.DividerItemDecoration;
import net.yuanjin.ui.BasicActivity;
import net.yuanjin.widget.navigation.NavigationText;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.Bitmap.createBitmap;
import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static android.graphics.Shader.TileMode.REPEAT;

/**
 *  Created by WuZhanQiang on 2016/12/27.
 */

public class PicassoSampleActivity extends BasicActivity{

    private RecyclerView recyclerView;
    private NavigationText navigation;
    private MyAdapter adapter;
    private List<String> imgUrls;
    private List<String> imgText;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picassosample);

        initActionBar();
        initImgDatas();
        initPicasso();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_picasso);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));


//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
////                //根据 recyclerview 的滚动状态决定图片是否加载
////                if (newState == RecyclerView.SCROLL_STATE_IDLE ){
////                    Picasso.with(PicassoSampleActivity.this)
////                            .pauseTag(PicassoSampleActivity.this);//暂停图片的加载
////                }else {
////                    Picasso.with(PicassoSampleActivity.this)
////                            .resumeTag(PicassoSampleActivity.this);//允许图片加载
////                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });


        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);



    }

    /**
     * Picasso初始化
     */
    private void initPicasso() {
        /** 通过Builder方式 自定义Picasso **/

        Picasso picasso = new Picasso.Builder(PicassoSampleActivity.this)
                //.downloader(new OkHttpDownloader(new OkHttpClient()))// 更改 Downloader
                .addRequestHandler(new MyRequestHandler(this))
                .build();
        Picasso.setSingletonInstance(picasso);//将自定义 picasso 变成全局使用
    }


    private void initActionBar() {
        if (navigation == null){
            navigation = new NavigationText(this);
            navigation.setTitle("PicassoSample");
        }
        setNavigation(navigation);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(getLayoutInflater().inflate(R.layout.item_list_detail_picasso,parent,false));
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.text.setText(imgText.get(position));

//            ArrayList<Transformation> transformations = new ArrayList<>();
//            transformations.add(new BlurTransformation(PicassoSampleActivity.this));
//            transformations.add(new GrayscaleTransformation(Picasso.with(PicassoSampleActivity.this)));

            //Picasso的标准创建方式 Picasso.with(context)

            Picasso.with(PicassoSampleActivity.this)
                    .setIndicatorsEnabled(true);//左上角标识；蓝色 - 从内存中获取,最佳性能；绿色 - 从本地获取,性能一般；红色 - 从网络加载,性能最差

//            Picasso.with(PicassoSampleActivity.this)
//                    .setLoggingEnabled(true);// 通过输出日志的方式查看每张网络请求的资源所用的时间

            Picasso.with(PicassoSampleActivity.this)
                    //.load(new File(imgUrls.get(position)))
                    .load(imgUrls.get(position))
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_error)
                    //.resizeDimen(R.dimen.list_detail_image_size,R.dimen.list_detail_image_size)
                    //.centerCrop() //图片会被剪切中间部分
                    //.centerInside() //图片会被完整的展示,可能图片不会填充满ImageView`,也有可能会被拉伸或者挤压
                    //.noFade() //去除图片加载时的默认淡入效果
                    //.noPlaceholder() //避免同一个view 图片第二次加载时刷出预置图片(不与placeholder同时使用)
                    .resize(400,400) //自定义图片的加载大小.一般会重新计算以改变图片的加载质量,比如小图变成大图
                    .onlyScaleDown() //来缩短图片的加载计算时间, 如果图片规格大于resize设定规格,将直接进行展示而不再重新计算
                    //.fit() //Picasso会对图片的大小及ImageView进行测量,计算出最佳的大小及最佳的图片质量来进行图片展示,减少内存,并对视图没有影响(不与resize同时使用)
                    //.priority(Picasso.Priority.HIGH) //设置图片加载的优先级,默认优先级为MEDIUM
                    //.tag(PicassoSampleActivity.this) //结合 pauseTag()，resumeTag() 可控制根据listview的滚动状态决定是否加载图片，pauseTag 不加载，resumeTag 加载
                    //.memoryPolicy(MemoryPolicy.NO_CACHE) //内存缓存管理。NO_STORE:指图片加载完不缓存在内存中 ;NO_CACHE: 指图片加载时放弃在内存缓存中查找 .适用于大图查看
                    //.networkPolicy(NetworkPolicy.NO_CACHE) //本地缓存管理。 NO_CACHE:跳过从本地读取资源这一过程 ; NO_STORE:不进行本地图片缓存 ; OFFLINE :只从本地读取除非联网正常并且本地找不到资源的情况
                    .config(Bitmap.Config.RGB_565)// 对于不透明的图片可以使用RGB_565来优化内存, Android使用ARGB_8888
                    //.rotate(90f) //加载时图片旋转，大于0小于360
                    //.transform(new BlurTransformation(PicassoSampleActivity.this)) //图片转化 -- 模糊
                    //.transform(new GrayscaleTransformation(Picasso.with(PicassoSampleActivity.this))) //图片转化 -- 灰化
                    //.transform(transformations) //图片转化 -- 模糊+灰化
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            holder.image.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
//                    .into(holder.image, new Callback() {//下载过程监听
//                        @Override
//                        public void onSuccess() {
//
//                        }
//
//                        @Override
//                        public void onError() {
//
//                        }
//                    });

//            Picasso.with(PicassoSampleActivity.this)
//                    .load(imgUrls.get(position))
//                    .placeholder(R.mipmap.ic_launcher)
//                    .error(R.mipmap.ic_error)
//                    .fit()
//                    .into(holder.image);







    }

        @Override
        public int getItemCount() {
            return imgText.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView text;

            public MyViewHolder(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.photo);
                text = (TextView) itemView.findViewById(R.id.url);
            }
        }

    }


    private void initImgDatas() {
        imgText = new ArrayList<String>();
        imgUrls = new ArrayList<String>();
        int imgNum = Constants.IMAGES.length;
        for (int i=0;i < imgNum ; i++){
            imgText.add("img "+ i);
            imgUrls.add(Constants.IMAGES[i]);
        }
    }


    private void initImgDatas2() {
        imgText = new ArrayList<String>();
        imgUrls = new ArrayList<String>();
        int imgNum = Data.URLS.length;
        for (int i=0;i < imgNum ; i++){
            imgText.add("img "+ i);
            imgUrls.add(Data.URLS[i]);
        }
    }

    private void initImgDatas3() {
        imgText = new ArrayList<String>();
        imgUrls = new ArrayList<String>();
        imgText.add("f01");
        imgText.add("f02");
        imgText.add("f03");
        imgText.add("f04");
        imgText.add("f05");
        imgText.add("f06");

        imgUrls.add("headimg://f01");
        imgUrls.add("headimg://f02");
        imgUrls.add("headimg://f03");
        imgUrls.add("headimg://f04");
        imgUrls.add("headimg://f01");
        imgUrls.add("headimg://f02");
    }

    public class BlurTransformation implements Transformation{

        RenderScript rs;

        public BlurTransformation(Context context){
            super();
            rs = RenderScript.create(context);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public Bitmap transform(Bitmap source) {

            // 创建一个Bitmap作为最后处理的效果Bitmap
            Bitmap blurredBitmap = source.copy(Bitmap.Config.ARGB_8888,true);

            // 分配内存
            Allocation input = Allocation.createFromBitmap(rs, blurredBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED);
            Allocation output = Allocation.createTyped(rs, input.getType());

            // 根据我们想使用的配置加载一个实例
            ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            script.setInput(input);

            // 设置模糊半径
            script.setRadius(10);

            //开始操作
            script.forEach(output);

            // 将结果copy到blurredBitmap中
            output.copyTo(blurredBitmap);

            //释放资源
            source.recycle();

            return blurredBitmap;
        }

        @Override
        public String key() {
            return "blur";
        }
    }


    //图片灰化处理
    public class GrayscaleTransformation implements Transformation {

        private final Picasso picasso;

        public GrayscaleTransformation(Picasso picasso) {
            this.picasso = picasso;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            Bitmap result = createBitmap(source.getWidth(), source.getHeight(), source.getConfig());
            Bitmap noise;
            try {
                noise = picasso.load(R.drawable.picasso_drawable).get();//叠加图片
            } catch (IOException e) {
                throw new RuntimeException("Failed to apply transformation! Missing resource.");
            }

            BitmapShader shader = new BitmapShader(noise, REPEAT, REPEAT);

            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);

            Paint paint = new Paint(ANTI_ALIAS_FLAG);
            paint.setColorFilter(filter);

            Canvas canvas = new Canvas(result);
            canvas.drawBitmap(source, 0, 0, paint);

            paint.setColorFilter(null);
            paint.setShader(shader);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));

            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

            source.recycle();
            noise.recycle();

            return result;
        }

        @Override
        public String key() {
            return "grayscaleTransformation()";
        }
    }
}
