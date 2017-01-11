package net.yuanjin.widgetlib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 *  Created by WuZhanQiang on 2017/1/5.
 */

public class XtionBasicActivity extends AppCompatActivity{

    /**
     * 在 startActivityForResult 基础上包一层 listener，从而将返回数据放在 listener 的 onResult() 方法中实现
     * @param intent
     * @param onActivityResultListener
     */
    public void startActivityForListener(Intent intent, OnActivityResultListener onActivityResultListener) {
        this.listener = onActivityResultListener;
        startActivityForResult(intent, 1011);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (listener!=null){
                listener.onResult(data);
                listener = null;
            }
        }
    }

    private OnActivityResultListener listener;

    public interface OnActivityResultListener {
        void onResult(Intent result);
    }
}
