package net.yuanjin.widgetlib.photolib.album;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;


import net.yuanjin.widgetlib.FrameButton;
import net.yuanjin.widgetlib.R;
import net.yuanjin.widgetlib.XtionBasicActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Created by WuZhanQiang on 2016/9/18.
 */
public class AlbumIndexActivity1 extends XtionBasicActivity{

    public final static String Tag_isMutiChoice ="isMutiChoice";
    public final static String Tag_maxlimit = "maxlimit";
    public final static String Tag_uris = "uris";
    public final static String Tag_selectedTotal ="selectedTotal";
    public final static String Tag_album ="album";
    public final static String Tag_ids = "ids";
    public final static String Tag_labelId = "labelId";
    public final static String Tag_isCrop = "isCrop";

    private AlbumIndexAdapter1 adapter;

    private FrameButton btn_submit;
    private TextView tv_maxlimit;
    private GridView gridview;

    private boolean isMutiChoice;
    private int maxlimit;
    private String labelId;
    private boolean isCrop;

    private List<AlbumIndexItem1> albumList;
    private List<String> uris = new ArrayList<String>();//已选择图片的Uri
    private List<String> ids = new ArrayList<String>();//已选择图片的name集

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albumindex);
        initView();
    }

    private void initView() {
        tv_maxlimit = (TextView)findViewById(R.id.album_index_maxlimit);
        btn_submit = (FrameButton)findViewById(R.id.album_index_submit);
        gridview = (GridView) findViewById(R.id.album_index_gridview);

        //get data from intent
        isMutiChoice = getIntent().getBooleanExtra(Tag_isMutiChoice, false);
        maxlimit = getIntent().getIntExtra(Tag_maxlimit, 3);
        String[] selectedUris = getIntent().getStringArrayExtra(Tag_uris);
        isCrop = getIntent().getBooleanExtra(Tag_isCrop,true);//默认截图，仅对选取单张图片有效
        // TODO: 2016/9/30  labelId 的作用是？
        labelId = getIntent().getStringExtra(Tag_labelId);

        if (selectedUris==null){
            selectedUris = new String[]{};
        }
        albumList = getPhotoAlbum(Arrays.asList(selectedUris));//获取相册信息
        adapter = new AlbumIndexAdapter1(albumList,this);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(AlbumIndexActivity1.this,AlbumChoiceActivity1.class);
                intent.putExtra(Tag_album,albumList.get(position));
                intent.putExtra(Tag_isMutiChoice,isMutiChoice);
                intent.putExtra(Tag_maxlimit, maxlimit);
                intent.putExtra(Tag_selectedTotal,uris.size());
                startActivityForResult(intent, AlbumChoiceActivity1.ActivityResult_AlbumChoice);
            }
        });

        tv_maxlimit.setText(String.format("最多能选择%d张图片", maxlimit));
        //点击完成按钮
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnUri();
            }
        });

        //// TODO: 2016/9/20 以下部分的作用待看 
        for (AlbumIndexItem1 item :albumList) {
            List<String> us = item.getSelectedItemUris();
            if (us.size()==0){
                item.setSelected(false);
            }else {
                item.setSelected(true);
                uris.addAll(us);
            }
        }
        if(uris.size()==0){
            btn_submit.setText(String.format("完成",uris.size()));
        }else{
            btn_submit.setText(String.format("完成(%d)",uris.size()));
        }
    }

    /**
     * 方法描述：按相册获取图片信息
     * @param selectedUris ：List<String>
     * @return ：List<AlbumIndexItem1>
     */
    private List<AlbumIndexItem1> getPhotoAlbum(List<String> selectedUris) {
        List<AlbumIndexItem1> indexList=new ArrayList<AlbumIndexItem1>();
        Cursor cursor= MediaStore.Images.Media.query(getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,STORE_IMAGES,null, MediaStore.Images.Media._ID+" desc");
        //query(ContentResolver cr, Uri localPath         , String[] projection, String where, String orderBy)
        Map<String,AlbumIndexItem1> countMap = new HashMap<String,AlbumIndexItem1>();
        AlbumIndexItem1 pa=null;
        while(cursor.moveToNext()){
            String name =cursor.getString(0);
            String path =cursor.getString(1);
            //eg: Image 的名称name： P60912-221935.jpg , Image 的本地路径path： /storage/emulated/0/DCIM/P60912-221935.jpg
            String id =cursor.getString(3);
            String dir_id = cursor.getString(4);
            String dir = cursor.getString(5);//目录名称

            boolean isSelected = selectedUris.contains(path);

            if (!countMap.containsKey(dir_id)){
                pa = new AlbumIndexItem1();
                pa.setName(dir);
                pa.setDir_id(dir_id);
                pa.setBitmap(path);
                pa.setCount("1");
                pa.getBitList().add(new AlbumPhotoItem1(Integer.valueOf(id),name,path,isSelected));
                if (isSelected){
                    pa.setSelected(true);
                }
                countMap.put(dir_id,pa);
            }else {
                pa=countMap.get(dir_id);
                pa.setCount(String.valueOf( Integer.valueOf(pa.getCount()) +1 ));
                pa.getBitList().add(new AlbumPhotoItem1(Integer.valueOf(id),name,path,isSelected));
            }


        }
        cursor.close();
        Iterable<String> it = countMap.keySet();
        for (String key:it){
            indexList.add(countMap.get(key));
        }
        return indexList;
    }

    private static final String[] STORE_IMAGES={
        MediaStore.Images.Media.DISPLAY_NAME,//显示的名字
        MediaStore.Images.Media.DATA,//文件的路径
        MediaStore.Images.Media.LONGITUDE,//图片拍摄的经度
        MediaStore.Images.Media._ID,//id
        MediaStore.Images.Media.BUCKET_ID,//dir id目录
        MediaStore.Images.Media.BUCKET_DISPLAY_NAME//dir name 目录名字
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case AlbumChoiceActivity1.ActivityResult_AlbumChoice:
                if (resultCode==RESULT_OK){
                    AlbumIndexItem1 album = (AlbumIndexItem1) data.getSerializableExtra("album");
                    uris.clear();
                    ids.clear();
                    for (AlbumIndexItem1 item:albumList){
                        //更新有图片被选中文件夹的内容
                        if (item.getDir_id().equals(album.getDir_id())){
                            item.getBitList().clear();
                            item.getBitList().addAll(album.getBitList());
                        }
                        List<String> us = item.getSelectedItemUris();
                        //更新selected信息
                        if (us.size()==0){
                            item.setSelected(false);
                        }else {
                            item.setSelected(true);
                            uris.addAll(us);
                        }
                        //更新name信息
                        List<String> names = item.getSelectedItemIds();
                        if (names.size()==0){
                        }else{
                            ids.addAll(names);
                        }
                    }
                    //如果点击了提交按钮则判断是否需要进行截图操作，无则直接返回
                    if(data.getBooleanExtra("submit", false)){
                        //进行截图操作：条件：只选择一张，且iscrop标识为true
                        if (maxlimit==1&&isCrop){
                            cropPhoto();
                        }
                        returnUri();
                    }

                    if(uris.size()==0){
                        btn_submit.setText(String.format("完成",uris.size()));
                    }else{
                        btn_submit.setText(String.format("完成(%d)",uris.size()));
                    }
                    adapter.notifyDataSetChanged();
                }
                break;

            default:
                break;
        }
    }

    /**
     * 截图图片
     */
    private void cropPhoto() {

    }

    protected  void returnUri(){
        Intent data =new Intent();
        data.putExtra(Tag_ids,ids.toArray(new String[ids.size()]));
        data.putExtra(Tag_uris, uris.toArray(new String[uris.size()]));
        data.putExtra(Tag_labelId, labelId);
        setResult(RESULT_OK,data);
        finish();
    }
}
