package com.wstro.app.component.glide;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ClassName: ProgressInterceptor
 * Function:
 * Date:     2017/12/14 0014 16:56
 *
 * @author pengl
 * @see
 */
public class ProgressInterceptor implements Interceptor {
    static final Map<String,OnProgressListener> sListenerMap = new HashMap<>();

    public static void addListener(String url,OnProgressListener listener){
        sListenerMap.put(url,listener);
    }

    public static void removeListener(String url){
        sListenerMap.remove(url);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        String url = request.url().toString();
        Response newResponse = response.newBuilder()
                .addHeader("Accept-Encoding", "identity")
                .body(new ProgressResponseBody(url, response.body()))
                .build();

        return newResponse;
    }
}
