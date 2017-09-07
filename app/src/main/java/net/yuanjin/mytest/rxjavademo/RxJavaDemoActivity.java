package net.yuanjin.mytest.rxjavademo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import net.yuanjin.R;
import net.yuanjin.ui.BasicActivity;

import org.mozilla.javascript.ast.WhileLoop;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observers.Subscribers;
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
    final int drawableRes = R.drawable.picasso_drawable;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjavamain);

        imageView = (ImageView) findViewById(R.id.imageview_rxjava);

        flatMapTest();

    }

    private void flatMapTest2() {
        Observable.from(Arrays.asList(
                "http://www.baidu.com/",
                "http://www.google.com/",
                "https://www.bing.com/"))
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return createIpObservableMultiThread(s);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i("mytest", "Consume Data <- " + s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.i("mytest", "throwable call()");
                    }
                });
    }

    // 获取ip
    private Observable<String> createIpObservableMultiThread(final String url) {
        return Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        String ip = getIPByUrl(url);
                        Log.i("mytest", "Emit Data -> "+url + " -> " + ip + " time: " );
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        subscriber.onNext(ip);
//                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io());

    }

    private String getIPByUrl(String url) {
        return "ip:"+url;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }

    Subscription subscription;

    /**
     * flatmap 使用测试
     */
    private void flatMapTest() {
        final Student[]  students = new Student[]{new Student("小明1"),new Student("小花1"),new Student("小兰1")};
        Subscriber<Student.Course> subscriber = new Subscriber<Student.Course>() {
            @Override
            public void onCompleted() {
                Log.i("mytest","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("mytest","onError");
            }

            @Override
            public void onNext(Student.Course course) {
                Log.d(tag, "onNext" + course.getName() + " threadid:"+Thread.currentThread().getId());
            }
        };

         subscription = Observable.from(students)
                .flatMap(new Func1<Student, Observable<Student.Course>>() {
                    @Override
                    public Observable<Student.Course> call(final Student student) {
                        Log.i("mytest","开始了");
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Log.i("mytest",student.getName() + " threadid : "+ Thread.currentThread().getId());

                        return Observable.from(student.getCourses()).filter(new Func1<Student.Course, Boolean>() {
                            @Override
                            public Boolean call(Student.Course course) {
                                try {
                                    Thread.sleep(200 );
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Log.d(tag, "--分发--->" + course.getName() + " threadid:"+Thread.currentThread().getId());
                                return true;
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .subscribe(subscriber);

    }



    class Student {
        private String name;
        private List<Course> courses;
        public Student(String name){
            this.name = name;
            courses = new ArrayList<>();
            courses.add(new Course("语文_" + name));
            courses.add(new Course("数学_" + name));
            courses.add(new Course("英语_" + name));
        }

        public String getName() {
            return name;
        }

        public List<Course> getCourses() {
            return courses;
        }

        class Course{
            private String name;
            public Course(String name){
                this.name = name;
            }

            public String getName() {
                return name;
            }
        }
    }

    private void test5() {



        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(10);
            }
        })


        .map(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer *10;
            }
        })


        .map(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer + 1;
            }
        })

        .subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(Integer integer) {
                Toast.makeText(RxJavaDemoActivity.this,"运行结果: "+integer,Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 变换
     */
    private void test4() {
        Log.i("test0",Thread.currentThread().getName().toString() + " ,id = "+Thread.currentThread().getId());
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                Log.i("test1",Thread.currentThread().getName().toString() + " ,id = "+Thread.currentThread().getId());
                subscriber.onNext(R.drawable.picasso_drawable);
            }
        })
        .observeOn(Schedulers.io()) // observeOn 设置线程为 io线程
        .map(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                Log.i("test2",Thread.currentThread().getName().toString() + " ,id = "+Thread.currentThread().getId());
                return integer;
            }
        })
        .observeOn(Schedulers.newThread()) // observeOn 设置线程为 新线程
        .map(new Func1<Integer, Drawable>() {
            @Override
            public Drawable call(Integer integer) {
                Log.i("test3",Thread.currentThread().getName().toString() + " ,id = "+Thread.currentThread().getId());
                Drawable drawable = getResources().getDrawable(integer);
                return drawable;
            }
        })
        .doOnSubscribe(new Action0() {
            @Override
            public void call() {
                Log.i("test doOnSubscribe 1",Thread.currentThread().getName().toString() + " ,id = "+Thread.currentThread().getId());
            }
        })
        .subscribeOn(Schedulers.newThread()) // subscribeOn 设置线程为 新线程
        .doOnSubscribe(new Action0() {
            @Override
            public void call() {
                Log.i("test doOnSubscribe 0",Thread.currentThread().getName().toString() + " ,id = "+Thread.currentThread().getId());
            }
        })
        .subscribeOn(Schedulers.io()) // subscribeOn 设置线程为 io线程

        .subscribe(new Subscriber<Drawable>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onStart() {
                Log.i("test onStart",Thread.currentThread().getName().toString() + " ,id = "+Thread.currentThread().getId());
            }

            @Override
            public void onNext(Drawable drawable) {
                Log.i("test4",Thread.currentThread().getName().toString() + " ,id = "+Thread.currentThread().getId());
            }
        });
    }

    /**
     * 由 id 取得图片并显示
     */
    private void test3(){

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

        Observable observable1 = Observable.just("Hello", "Hi", "Aloha");
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
