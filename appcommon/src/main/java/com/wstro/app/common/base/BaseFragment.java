/*
 * {EasyGank}  Copyright (C) {2015}  {CaMnter}
 *
 * This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.
 * This is free software, and you are welcome to redistribute it
 * under certain conditions; type `show c' for details.
 *
 * The hypothetical commands `show w' and `show c' should show the appropriate
 * parts of the General Public License.  Of course, your program's commands
 * might be different; for a GUI interface, you would use an "about box".
 *
 * You should also get your employer (if you work as a programmer) or school,
 * if any, to sign a "copyright disclaimer" for the program, if necessary.
 * For more information on this, and how to apply and follow the GNU GPL, see
 * <http://www.gnu.org/licenses/>.
 *
 * The GNU General Public License does not permit incorporating your program
 * into proprietary programs.  If your program is a subroutine library, you
 * may consider it more useful to permit linking proprietary applications with
 * the library.  If this is what you want to do, use the GNU Lesser General
 * Public License instead of this License.  But first, please read
 * <http://www.gnu.org/philosophy/why-not-lgpl.html>.
 */

package com.wstro.app.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wstro.app.common.R;
import com.wstro.app.common.utils.DialogUtil;
import com.wstro.app.common.utils.ToastUtils;

import butterknife.ButterKnife;

/**
 * @author pengl
 */
public abstract class BaseFragment extends Fragment {
    protected BaseActivity activity;

    protected View rootView;

    protected Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (this.rootView == null) {
            this.rootView = inflater.inflate(this.getLayoutId(), container, false);
        }

        context = getActivity();
        ButterKnife.bind(this,rootView);

        this.initViewsAndEvents(this.rootView, savedInstanceState);
        this.initData();
        return this.rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (BaseActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activity = null;
    }

    public BaseActivity getHoldingActivity(){
        return activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (this.rootView.getParent() != null) {
            ViewGroup parent = (ViewGroup) this.rootView.getParent();
            parent.removeView(this.rootView);
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initViewsAndEvents(View self, Bundle savedInstanceState);

    /**
     * Initialize the Activity data
     */
    protected abstract void initData();


    /**
     * Find the view by id
     *
     * @param id id
     * @param <V> V
     * @return V
     */
    @SuppressWarnings("unchecked") protected <V extends View> V findView(int id) {
        return (V) this.rootView.findViewById(id);
    }


    public void showProgressDialog() {
        showProgressDialog("");
    }

    public void showProgressDialog(String message) {
        showProgressDialog(message,true);
    }

    public void showProgressDialog(String message, boolean cancelable) {
        DialogUtil.showProgressDialog(getHoldingActivity(),message,cancelable);
    }

    @Deprecated
    public void stopProgressDialog() {
        DialogUtil.stopProgressDialog();
    }

    public void dismissProgressDialog() {
        DialogUtil.stopProgressDialog();
    }

    public void showCustomProgressDialog() {
        showCustomProgressDialog(R.string.msg_loading);
    }

    public void showCustomProgressDialog(int msgId) {
        showCustomProgressDialog(getString(msgId));
    }

    public void showCustomProgressDialog(String msg) {
        DialogUtil.showCustomProgressDialog(getHoldingActivity(),msg);
    }

    public void showToast(String message) {
        ToastUtils.showToast(getHoldingActivity(),message);
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
        ToastUtils.showCustomToast(getHoldingActivity(),text,duration);

    }

    public void cancelToast() {
        ToastUtils.cancelToast();
    }
}
