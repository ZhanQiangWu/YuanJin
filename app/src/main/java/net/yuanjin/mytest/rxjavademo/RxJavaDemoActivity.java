package net.yuanjin.mytest.rxjavademo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import net.yuanjin.R;
import net.yuanjin.ui.BasicActivity;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.NewThreadScheduler;
import rx.schedulers.Schedulers;

/**
 *  Created by zhan on 2016/12/21.
 *  参考资料：
 *  1、给 Android 开发者的 RxJava 详解 http://gank.io/post/560e15be2dca930e00da1083
 */

public class RxJavaDemoActivity extends BasicActivity{

    private String tag = "mytest";
    private ImageView imageView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjavamain);

        test3();

    }

    /**
     * 由 id 取得图片并显示
     */
    private void test3(){
        final int drawableRes = R.drawable.picasso_drawable;
        imageView = (ImageView) findViewById(R.id.imageview_rxjava);
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getResources().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(RxJavaDemoActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Drawable drawable) {
                imageView.setImageDrawable(drawable);
            }
        });
    }

    /**
     * 打印字符串数组
     */
    private void test2(){
        String[] names = new String[]{"aa","ss","dd","ff"};
        Observable<String> observable = Observable.from(names);
        observable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(tag,s);
            }
        });
    }

    private void test1() {
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
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("See You");
                subscriber.onCompleted();
            }
        });

        //observable.subscribe(observer);

        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String string) {
                Log.d(tag,string);
            }
        };
        //observable.subscribe(onNextAction);

        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
            }
        };

        observable.subscribe(onNextAction,onErrorAction);
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

        //Action0 是 RxJava 的一个接口，它只有一个方法 call()，这个方法是无参无返回值的
        Action0 onCompletedAction = new Action0(){
            // onCompleted()
            @Override
            public void call() {
                Log.i(tag, "completed");
            }
        };

        Action1<String> onNextAction = new Action1<String>() {
            // onNext()
            @Override
            public void call(String s) {
                Log.i(tag, s);
            }
        };

        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            // onError()
            @Override
            public void call(Throwable throwable) {
                // Error handling
            }
        };

        // 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
//        observable.subscribe(onNextAction);
//        // 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
//        observable.subscribe(onNextAction,onErrorAction);
//        // 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
        observable.subscribe(onNextAction,onErrorAction,onCompletedAction);

        String[] names = {"小青","小白","小黑"};
        Observable.from(names)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(tag,s);
                    }
                });

        final int drawableRes = R.drawable.picasso_drawable;
        imageView = (ImageView) findViewById(R.id.imageview_rxjava);
        Observable.create(new Observable.OnSubscribe<Drawable>() {

            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getResources().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(RxJavaDemoActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Drawable drawable) {
                Log.i(tag,"获取图片了");
                imageView.setImageDrawable(drawable);
            }
        });
    }
}
