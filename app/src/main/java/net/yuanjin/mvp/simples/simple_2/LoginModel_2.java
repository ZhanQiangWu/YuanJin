package net.yuanjin.mvp.simples.simple_2;

import net.yuanjin.mvp.simples.util.HttpTask;
import net.yuanjin.mvp.simples.util.HttpUtil;

/**
 *  Created by wzq on 2017/9/7.
 */
//M层
public class LoginModel_2 {

    public void login(String username, String password, final HttpUtil.OnHttpResultListener onHttpResultListener){
        HttpTask task = new HttpTask(new HttpUtil.OnHttpResultListener() {
            @Override
            public void onResult(String result) {
                onHttpResultListener.onResult(result);
            }
        });
        task.execute(username,password);

    }
}
