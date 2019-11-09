package com.czh.basicframe.https;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.czh.basicframe.base.BaseApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author  : czh
 * create Date : 2019/10/22  14:10
 * 详情 :
 */
public class RetrofitUtils {
    public static RetrofitUtils getInstance() {
        return RetrofitHolder.instance;
    }

    private static class RetrofitHolder {
        private static RetrofitUtils instance = new RetrofitUtils();
    }

    private Retrofit retrofit;
    private MyService mService;

    public RetrofitUtils() {

        retrofit = new Retrofit.Builder().baseUrl(UrlUtils.IP)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkhttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = retrofit.create(MyService.class);
    }

    public MyService getService() {
        return mService;
    }

    private OkHttpClient getOkhttpClient() {
//        File cacheFile = new File(BaseApplication.getContext().getExternalCacheDir(), "http_cache");
//        long cacheSize = 10 * 1024 * 1024;//10M缓存空间
//        Cache cache = new Cache(cacheFile, cacheSize);
        // TODO: 2019/11/6 感觉这个缓存设置下去对token的请求有致命影响
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new HttpLogInterceptor())
//                .cache(cache)
                .build();
        return client;
    }

    /**
     * 设置网络拦截器
     */
    private Interceptor interceptor = chain -> {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (isNetworkReachable(BaseApplication.getContext())) {
            int maxAge = 60 * 60 * 24 * 2;
            return response.newBuilder().removeHeader("Pragma")
                    .header("Cache-Control", "public ,max-age=" + maxAge)
                    .build();
        }
        return response;
    };

    /**
     * 判断网络情况
     *
     * @param context
     * @return
     */
    public Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }
}
