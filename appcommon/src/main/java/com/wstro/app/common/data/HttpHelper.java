package com.wstro.app.common.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.wstro.app.common.BuildConfig;
import com.wstro.app.common.utils.LogUtil;
import com.wstro.app.common.utils.NetUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class Note:
 * entrance class to access network with {@link Retrofit}
 * used only by{@link AbstractDataManager} is recommended
 * <p>
 * 使用retrofit进行网络访问的入口类，推荐只在{@link AbstractDataManager}中使用
 */
public class HttpHelper {

    public static final int DEFAULT_TIMEOUT = DataConstants.DEFAULT_TIMEOUT;

    private HashMap<String, Object> serviceMap;
    private Context context;
    private OkHttpClient okHttpClient;

    public HttpHelper(Context context) {
        //Map used to store RetrofitService
        serviceMap = new HashMap<>();
        this.context = context;
        okHttpClient = createOkHttpClient();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    @SuppressWarnings("unchecked")
    public <S> S getService(Class<S> serviceClass) {
        if (serviceMap.containsKey(serviceClass.getName())) {
            return (S) serviceMap.get(serviceClass.getName());
        } else {
            Object obj = createService(serviceClass);
            serviceMap.put(serviceClass.getName(), obj);
            return (S) obj;
        }
    }

    @SuppressWarnings("unchecked")
    public <S> S getService(Class<S> serviceClass, OkHttpClient client) {
        if (serviceMap.containsKey(serviceClass.getName())) {
            return (S) serviceMap.get(serviceClass.getName());
        } else {
            Object obj = createService(serviceClass, client);
            serviceMap.put(serviceClass.getName(), obj);
            return (S) obj;
        }
    }

    private <S> S createService(Class<S> serviceClass) {
        OkHttpClient client;
        if(okHttpClient != null)
            client = okHttpClient;
        else
            client = createOkHttpClient();
        return createService(serviceClass, client);
    }

    @NonNull
    private OkHttpClient createOkHttpClient() {
        //custom OkHttp
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //time our
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //cache
        File httpCacheDirectory = new File(context.getCacheDir(), "OkHttpCache");
        builder.cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024));

        builder.addInterceptor(new HeaderInterceptor());
        //Interceptor
        // Log信息拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if(BuildConfig.DEBUG) {
                    LogUtil.d("HttpHelper",message);
                }
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.addInterceptor(interceptor);
        builder.addInterceptor(new CacheControlInterceptor());

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        CookieJar cookieJar = new JavaNetCookieJar(cookieManager);
        builder.cookieJar(cookieJar);

        return builder.build();
    }

    private <S> S createService(Class<S> serviceClass, OkHttpClient client) {
        String end_point = "";
        try {
            Field field1 = serviceClass.getField("end_point");
            end_point = (String) field1.get(serviceClass);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(end_point)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }


    private class HeaderInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder();

            if(DataConstants.accessToken != null) {
                requestBuilder.addHeader("Authorization", DataConstants.accessToken);
            }
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    }

    private class CacheControlInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtils.isConnected(context)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response response = chain.proceed(request);

            if (NetUtils.isConnected(context)) {
                int maxAge = 60 * 60; // read from cache for 1 hour
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }

    public void destroy(){
        serviceMap.clear();
        serviceMap = null;
        context = null;
        okHttpClient = null;
    }
}
