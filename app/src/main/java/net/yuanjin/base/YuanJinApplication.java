package net.yuanjin.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushService;

import net.yuanjin.tencentxg.receiver.MessageReceiver;
import net.yuanjin.widgetlib.imageloader.XtionImageLoader;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 *  Created by WuZhanQiang on 2017/1/9.
 */

public class YuanJinApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        XtionImageLoader.init(getApplicationContext());

        Log.d(MessageReceiver.LogTag, "开始腾讯信鸽注册操作");
        XGPushConfig.enableDebug(this,true);
        XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                Log.d(MessageReceiver.LogTag, "注册成功，设备token为：" + data);
            }
            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.d(MessageReceiver.LogTag, "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });
    }

}
