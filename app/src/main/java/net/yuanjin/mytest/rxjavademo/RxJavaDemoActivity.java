package net.yuanjin.mytest.rxjavademo;

import android.os.Bundle;
import android.util.Log;

import net.yuanjin.R;
import net.yuanjin.ui.BasicActivity;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 *  Created by zhan on 2016/12/21.
 */

public class RxJavaDemoActivity extends BasicActivity{

    private String tag = "mytest";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjavamain);

        test();

    }

    public void test(){
        //观察者
        Observer<String> observer = new    Observer<String>() {
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
        //观察者
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i("mytest","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("mytest","onError");
            }

            @Override
            public void onNext(String s) {
                Log.i("mytest","onNext");
            }
        };

        //被观察者
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });

        //以下两个操作等效
        //Observable observable1 = Observable.just("Hello", "Hi", "Aloha");
        // 将会依次调用：
        // onNext("Hello");
        // onNext("Hi");
        // onNext("Aloha");
        // onCompleted();

        //String[] words = {"Hello", "Hi", "Aloha"};
        //Observable observable2 = Observable.from(words);
        // 将会依次调用：
        // onNext("Hello");
        // onNext("Hi");
        // onNext("Aloha");
        // onCompleted();
    }
}
