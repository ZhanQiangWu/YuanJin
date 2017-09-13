package net.yuanjin.mytest.retrofitdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import net.yuanjin.R;
import net.yuanjin.ui.BasicActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：Carson_Ho
 * 链接：http://www.jianshu.com/p/a3e162261ab6
 * 來源：简书
 */

public class RetrofitDemoActivity extends BasicActivity {
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofitdemo);
        message = (TextView) findViewById(R.id.textview_retrofit_message);

        request();
    }

    private void request() {
        //创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")//设置网络请求url
                .addConverterFactory(GsonConverterFactory.create())//设置使用Gson解析(记得加入依赖)
                .build();
        //创建网络请求接口的实例
        Request_Interface request = retrofit.create(Request_Interface.class);

        //对发送请求进行封装
        Call<Translation> call = request.getCall();

        //发送网络请求（异步）
        call.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                //处理返回的结果
                response.body().show();
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {
                System.out.println("请求失败");
            }
        });
    }


    /**
     * post 请求
     */
    public void postRequest(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/")
                //.addConverterFactory(GsonConverterFactory.create())
                .build();
        final Request_Interface request = retrofit.create(Request_Interface.class);
        Call<ResponseBody> call = request.postCall("你好");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.i("mytest",response.body().string());
                    message.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                message.setText("请求失败");
            }
        });
    }
}