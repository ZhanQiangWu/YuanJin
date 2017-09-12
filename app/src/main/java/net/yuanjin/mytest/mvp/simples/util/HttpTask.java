package net.yuanjin.mytest.mvp.simples.util;

/**
 *  模拟http登录请求
 *  Created by wzq on 2017/9/5.
 */

public class HttpTask {

    private HttpUtil.OnHttpResultListener listener;

    public HttpTask(HttpUtil.OnHttpResultListener listener){
        this.listener = listener;
    }

    public void execute(String username,String password){
        if (listener != null){
            listener.onResult("welcome," + username);
        }
    }
}
