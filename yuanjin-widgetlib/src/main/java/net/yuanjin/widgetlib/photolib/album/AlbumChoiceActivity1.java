package net.yuanjin.widgetlib.photolib.album;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import net.yuanjin.widgetlib.FrameButton;
import net.yuanjin.widgetlib.R;
import net.yuanjin.widgetlib.XtionBasicActivity;


/**
 *  Created by WuZhanQiang on 2016/9/20.
 */
public class AlbumChoiceActivity1 extends XtionBasicActivity {

    public final static int ActivityResult_AlbumChoice = 1002;

    private GridView gridview;
    private TextView tv_selectedTotal;
    private FrameButton btn_submit;

    private boolean            isMutiChoice;
    private int                maxlimit;
    private int                selectedTotal;
    private AlbumIndexItem1 album;

    private AlbumChoiceAdapter1 adapter;

    private boolean isFinished = false;//标识 是否结束图片挑选，退出相册

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albumchoice);
        init();
    }

    private void init() {
        tv_selectedTotal = (TextView)findViewById(R.id.album_choice_selectedTotal);
        gridview = (GridView) findViewById(R.id.album_choice_gridview);
        btn_submit = (FrameButton)findViewById(R.id.album_choice_submit);

        //get data from intent
        album = (AlbumIndexItem1)getIntent().getExtras().get(AlbumIndexActivity1.Tag_album);//本相册详细图片信息
        isMutiChoice = getIntent().getBooleanExtra(AlbumIndexActivity1.Tag_isMutiChoice, false);//是否多选
        maxlimit = getIntent().getIntExtra(AlbumIndexActivity1.Tag_maxlimit, 3);//选择上限
        selectedTotal = getIntent().getIntExtra(AlbumIndexActivity1.Tag_selectedTotal, 0);//已选择总数目

        adapter = new AlbumChoiceAdapter1(this,album,isMutiChoice,gridview);
        gridview.setAdapter(adapter);
        tv_selectedTotal.setText(String.format("已选择 %d/%d", new Object[]{selectedTotal,maxlimit}));

        //adapter注册 图片选择 校验监听
        adapter.setOnCheckBoxSelectedListener(new AlbumChoiceAdapter1.OnCheckBoxSelectedListener() {
            @Override
            public void onSelected(int position, boolean isCheck) {
                if (isCheck){
                    selectedTotal = selectedTotal +1;
                }else {
                    selectedTotal = selectedTotal -1;
                }
                tv_selectedTotal.setText(String.format("已选择 %d/%d", new Object[]{selectedTotal,maxlimit}));
            }

            @Override
            public boolean validate(boolean isCheck) {
                if (isCheck){
                    //正选需要校验
                    if (selectedTotal<maxlimit){
                        return true;
                    }else {
                        // TODO: 2016/9/20  此处需要更换成 onToast写法 
                        Toast.makeText(AlbumChoiceActivity1.this, String.format("最多只能选择%d个", maxlimit), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }else{//反选不需要校验
                    return  true;
                }
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //back(true);
                isFinished = true;//挑选完成，退出相册
                finish();
            }
        });
    }





    //返回键监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                //back(false);
                isFinished = false;//返回相册列表页面，继续挑选
                finish();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 重写finish方法，结束图片选择操作或者返回相册列表页面时调用
     */
    @Override
    public void finish() {
        Intent i = new Intent();
        i.putExtra("album", album);
        i.putExtra("submit",isFinished);
        setResult(RESULT_OK,i);
        super.finish();
    }

//    /**
//     * 返回相册信息
//     * @param submit
//     */
//    public void back(boolean submit){
//        Intent i = new Intent();
//        i.putExtra("album", album);
//        i.putExtra("submit",submit);
//        setResult(RESULT_OK,i);
//        finish();
//    }

}
