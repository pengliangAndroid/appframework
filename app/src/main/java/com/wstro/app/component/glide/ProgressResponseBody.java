package com.wstro.app.component.glide;

import android.support.annotation.Nullable;

import com.wstro.app.common.utils.LogUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * ClassName: ProgressResponseBody
 * Function:
 * Date:     2017/12/14 0014 17:07
 *
 * @author pengl
 * @see
 */
public class ProgressResponseBody extends ResponseBody {
    private static final String TAG = "ProgressResponseBody";
    private static final Charset UTF8 = Charset.forName("UTF-8");

    private BufferedSource bufferedSource;

    private ResponseBody responseBody;

    private OnProgressListener listener;

    public ProgressResponseBody(String url,ResponseBody responseBody){
        this.responseBody = responseBody;
        listener = ProgressInterceptor.sListenerMap.get(url);
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if(bufferedSource == null){
            bufferedSource = Okio.buffer(new ProgressSource(responseBody.source()));
        }
        return bufferedSource;
    }

    private class ProgressSource extends ForwardingSource{

        private long totalBytesRead = 0;

        int currentProgress;

        public ProgressSource(Source delegate) {
            super(delegate);
        }

        @Override
        public long read(Buffer sink, long byteCount) throws IOException {
            long bytesRead = super.read(sink, byteCount);
            long fullLength = contentLength();

            if(bytesRead == -1){
                totalBytesRead = fullLength;
            }else{
                totalBytesRead += bytesRead;
            }

            int progress = (int) (totalBytesRead *100f /fullLength);
            LogUtil.d(TAG,"download progress:"+progress);
            if(listener != null && progress != currentProgress){
                listener.onProgress(progress);
            }

            if(listener != null && totalBytesRead == fullLength){
                listener = null;
            }

            currentProgress = progress;
            return bytesRead;
        }
    }
}
