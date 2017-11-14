package com.wstro.app.common.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;
import com.wstro.app.common.CommonConstants;
import com.wstro.app.common.R;
import com.wstro.app.common.utils.DialogUtil;
import com.wstro.app.common.utils.ToastUtils;

import java.util.Locale;

import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

/**
 * Created by pengl on 2016/9/6.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private final BehaviorSubject lifecycleSubject = BehaviorSubject.create();

    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(getLayoutId());
        context = this;

        BaseActivityManager.getInstance().addActivity(this);

        ButterKnife.bind(this);

        lifecycleSubject.onNext(ActivityEvent.CREATE);

        this.initToolbar(savedInstanceState);
        this.initViewsAndEvents(savedInstanceState);
        this.initData();
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = res.getConfiguration();
        //1.0 设置正常字体大小的倍数
        config.fontScale = CommonConstants.CURRENT_FONT_SCALE;

        if(CommonConstants.CURRENT_LOCALE.equals(Locale.CHINESE.getLanguage())) {
            config.locale = Locale.CHINESE;
        }else{
            config.locale = Locale.ENGLISH;
        }
        res.updateConfiguration(config,res.getDisplayMetrics());

        return res;
    }

    @NonNull
    @CheckResult
    public final Observable lifecycle() {
        return this.lifecycleSubject.asObservable();
    }


    @NonNull
    public <T> Observable.Transformer<T, T> bindUntilEvent(@NonNull final ActivityEvent event) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> sourceObservable) {
                Observable<ActivityEvent> compareLifecycleObservable =
                        lifecycleSubject.takeFirst(new Func1<ActivityEvent, Boolean>() {
                            @Override
                            public Boolean call(ActivityEvent activityLifeCycleEvent) {
                                return activityLifeCycleEvent.equals(event);
                            }
                        });
                return sourceObservable.takeUntil(compareLifecycleObservable);
            }
        };
    }

    @NonNull
    @CheckResult
    public final  LifecycleTransformer bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(this.lifecycleSubject);
    }


    @CallSuper
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @CallSuper
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @CallSuper
    protected void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @CallSuper
    protected void onStop() {
       lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();

        closeKeyboard();

        cancelToast();
        stopProgressDialog();
        BaseActivityManager.getInstance().removeActivity(this);
        context = null;
    }


    protected abstract int getLayoutId();

    protected abstract void initViewsAndEvents(Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract void initToolbar(Bundle savedInstanceState);

    protected <V extends View> V findView(int id) {
        return (V) this.findViewById(id);
    }

    public void showProgressDialog() {
        showProgressDialog("");
    }

    public void showProgressDialog(String message) {
        showProgressDialog(message,true);
    }

    public void showProgressDialog(String message, boolean cancelable) {
        DialogUtil.showProgressDialog(this,message,cancelable);
    }

    public void stopProgressDialog() {
        DialogUtil.stopProgressDialog();
    }

    public void showCustomProgressDialog() {
        showCustomProgressDialog(R.string.msg_loading);
    }

    public void showCustomProgressDialog(int msgId) {
        showCustomProgressDialog(getString(msgId));
    }

    public void showCustomProgressDialog(String msg) {
        DialogUtil.showCustomProgressDialog(this,msg);
    }

    public void showToast(String message) {
        ToastUtils.showToast(this,message);
    }

    public void showToast(int messageId) {
        showToast(getString(messageId));
    }

    public void showCustomToast(int msgId) {
        showCustomToast(getString(msgId));
    }

    public void showCustomToast(String message) {
        showCustomToast(message,Toast.LENGTH_SHORT);
    }

    public void showCustomToast(CharSequence text, int duration) {
        ToastUtils.showCustomToast(this,text,duration);

    }

    public void cancelToast() {
        ToastUtils.cancelToast();
    }

    public void closeKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
