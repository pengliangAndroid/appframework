package com.wstro.app.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;
import com.wstro.app.common.utils.DialogUtil;
import com.wstro.app.common.utils.StatusBarCompat;

import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

/**
 * Created by pengl on 2016/9/6.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private final BehaviorSubject lifecycleSubject = BehaviorSubject.create();

    private static int statusBarColor = -1;

    protected boolean isStatusCompat = true;

    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(getLayoutId());
        context = this;

        BaseActivityManager.getInstance().addActivity(this);

        ButterKnife.bind(this);

        compatStatusBar();

        lifecycleSubject.onNext(ActivityEvent.CREATE);

        this.initToolbar(savedInstanceState);
        this.initViewsAndEvents(savedInstanceState);
        this.initData();
    }

    @NonNull
    @CheckResult
    public final Observable lifecycle() {
        return this.lifecycleSubject.asObservable();
    }

   /* @NonNull
    @CheckResult
    public final LifecycleTransformer bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(this.lifecycleSubject, event);
    }*/

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

    private void compatStatusBar(){
        if (isStatusCompat) {
            if(statusBarColor == -1) {
                int resourceId = getResources().getIdentifier("colorPrimary", "color", getPackageName());
                statusBarColor = getResources().getColor(resourceId);
            }

            StatusBarCompat.compat(this, statusBarColor);
        }
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

        ButterKnife.unbind(this);
        BaseActivityManager.getInstance().removeActivity(this);
        context = null;
    }


    public void setStatusCompat(boolean statusCompat) {
        isStatusCompat = statusCompat;
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

    public void showToast(String message) {
        Toast toast = Toast.makeText(getContext(),message,Toast.LENGTH_SHORT);
        //toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
        //ToastUtils.showToast(this,message);
    }

    public void showToast(int messageId) {
        showToast(getString(messageId));
    }

    public Context getContext() {
        return this;
    }
}
