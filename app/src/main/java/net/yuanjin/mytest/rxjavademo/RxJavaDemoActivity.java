package net.yuanjin.mytest.rxjavademo;

import android.os.Bundle;
import android.util.Log;

import net.yuanjin.ui.BasicActivity;

/**
 *  Created by zhan on 2016/12/21.
 */

public class RxJavaDemoActivity extends BasicActivity{

    private String tag = "mytest";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void test(){
        rx.Observer<String> observer = new rx.Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(tag, "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(tag, "Error!");
            }

            @Override
            public void onNext(String s) {
                Log.d(tag, "Item: " + s);
            }
        };
    }
}
