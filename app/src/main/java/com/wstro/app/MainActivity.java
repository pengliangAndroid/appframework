package com.wstro.app;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wstro.app.common.base.BaseAppToolbarActivity;
import com.wstro.app.common.utils.LogUtil;
import com.wstro.app.component.glide.OnProgressListener;
import com.wstro.app.component.glide.ProgressInterceptor;
import com.wstro.app.data.DataManager;
import com.wstro.app.settings.SettingsActivity;
import com.wstro.app.widget.RoundProgressBar;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseAppToolbarActivity {


    @BindView(R.id.btn_settings)
    Button btnSettings;

    @BindView(R.id.iv_image)
    ImageView ivImage;

    @BindView(R.id.round_progress_bar)
    RoundProgressBar roundProgressBar;


    @BindView(R.id.fl_progress)
    FrameLayout flProgress;

    @BindView(R.id.tv_progress)
    TextView tvProgress;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        titleText.setText(getString(R.string.app_name_title));

        //showCustomProgressDialog("xxx");

        DataManager.get().getLoginUsesList();
        final String url = "http://imgsrc.baidu.com/forum/pic/item/5127ebc4b74543a9deab745318178a82b80114bf.jpg";
        //BitmapTypeRequest<String> request = Glide.with(this).load("https://www.baidu.com").asBitmap();

        OnProgressListener progressListener = new OnProgressListener() {
            @Override
            public void onProgress(final int progress) {
                LogUtil.d("progress:"+progress);
                tvProgress.post(new Runnable() {
                    @Override
                    public void run() {
                        tvProgress.setText(progress+"%");
                    }
                });
            }
        };

        ProgressInterceptor.addListener(url,progressListener);

        Glide.with(this)
                .load(url)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        flProgress.setVisibility(View.GONE);
                        ivImage.setImageDrawable(resource);
                        ProgressInterceptor.removeListener(url);
                    }

                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        flProgress.setVisibility(View.VISIBLE);
                    }
                });

        //roundProgressBar.setProgress(50);

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 100; i++) {
                    final int progress = i;
                    roundProgressBar.post(new Runnable() {
                        @Override
                        public void run() {
                            roundProgressBar.setProgress(progress);
                        }
                    });

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/


    }

    @Override
    protected void initData() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @OnClick(R.id.btn_settings)
    public void onViewClicked() {
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(MainActivity.this).clearDiskCache();

            }
        }).start();
        Glide.get(MainActivity.this).clearMemory();*/
        SettingsActivity.start(this);
    }
}
