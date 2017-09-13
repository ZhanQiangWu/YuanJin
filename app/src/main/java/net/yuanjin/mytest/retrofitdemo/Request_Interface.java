package net.yuanjin.mytest.retrofitdemo;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 用于描述网络请求
 * 作者：Carson_Ho
 * 链接：http://www.jianshu.com/p/a3e162261ab6
 * 來源：简书
 */

public interface Request_Interface {

    /**
     * 注解里传入 网络请求 的部分URL地址
     * Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
     * 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
     * getCall()是接受网络请求数据的方法
     */

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<Translation> getCall();

    /**
     * 有道翻译
     * // URL
     * http://fanyi.youdao.com/translate
     * // URL实例
     * http://fanyi.youdao.com/translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=
     * 参数说明
     * doctype：json 或 xml
     * jsonversion：如果 doctype 值是 xml，则去除该值，若 doctype 值是 json，该值为空即可
     * xmlVersion：如果 doctype 值是 json，则去除该值，若 doctype 值是 xml，该值为空即可
     * type：语言自动检测时为 null，为 null 时可为空。英译中为 EN2ZH_CN，中译英为 ZH_CN2EN，日译中为 JA2ZH_CN，中译日为 ZH_CN2JA，韩译中为 KR2ZH_CN，中译韩为 ZH_CN2KR，中译法为 ZH_CN2FR，法译中为 FR2ZH_CN
     * keyform：mdict. + 版本号 + .手机平台。可为空
     * model：手机型号。可为空
     * mid：平台版本。可为空
     * imei：???。可为空
     * vendor：应用下载平台。可为空
     * screen：屏幕宽高。可为空
     * ssid：用户名。可为空
     * abtest：???。可为空
     * 请求方式说明
     * 请求方式：POST
     * 请求体：i
     * 请求格式：x-www-form-urlencoded
     */
    @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    @FormUrlEncoded
    Call<ResponseBody> postCall(@Field("i") String targetSentence);


//    @GET("info")
//    Call<ResponseBody> getInfoByParamsQueryMap(@QueryMap Map<String, Integer> params);
//
//    @POST("info")
//    Call<ResponseBody> getInfoByParamsPost(@Query("pagenum") int pagenum, @Query("type") int type);
}
