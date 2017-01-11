package net.yuanjin.base;

import android.app.Application;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

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
        //initPicasso();//Picasso初始化
    }

//    private void initPicasso() {
//        Picasso.setSingletonInstance(new Picasso.Builder(getApplicationContext())
//                .downloader(new OkHttpDownloader(getUnsafeOkHttpClient())).build()
//        );
//    }

//    public static OkHttpClient getUnsafeOkHttpClient(){
//        try {
//            // Create a trust manager that does not validate certificate chains
//            final TrustManager[] trustAllCerts = new TrustManager[]{
//                    new X509TrustManager() {
//                        @Override
//                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//
//                        }
//
//                        @Override
//                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//
//                        }
//
//                        @Override
//                        public X509Certificate[] getAcceptedIssuers() {
//                            return new X509Certificate[0];
//                        }
//                    }
//            };
//
//            // Install the all-trusting trust manager
//            final SSLContext sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(null, trustAllCerts,new java.security.SecureRandom());
//
//            // Create an ssl socket factory with our all-trusting manager
//            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//
//            OkHttpClient okHttpClient = new OkHttpClient();
//            okHttpClient.setSslSocketFactory(sslSocketFactory);
//            okHttpClient.setHostnameVerifier(new HostnameVerifier() {
//
//                @Override
//                public boolean verify(String hostname, SSLSession session) {
//                    return true;
//
//                }
//            });
//            return okHttpClient;
//
//        }catch (Exception e) {
//
//            throw new RuntimeException(e);
//        }
//    }
}
