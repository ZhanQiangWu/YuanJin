package net.yuanjin.mytest.retrofitdemo;

import android.util.Log;

/**
 * 接收服务器返回数据Model
     作者：Carson_Ho
     链接：http://www.jianshu.com/p/a3e162261ab6
     來源：简书
 */

public class Translation {

    private int status;
    private content content;

    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    //定义 输出返回数据 的方法
    public void show() {
        Log.i("mytest","输出结果： status: "+ status + " , content.from: "+ content.from + ",content.to : "+ content.to
                +",content.vendor : "+ content.vendor + ",content.out : "+ content.out + ",content.errNo : "+ content.errNo);
    }

}
