
package com.wstro.app.common.base;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * @author pengl
 */
public class BaseActivityManager {
    private static final String TAG = BaseActivityManager.class.getSimpleName();

    private static BaseActivityManager instance = null;
    private static List<Activity> activities = new LinkedList<Activity>();

    private BaseActivityManager() {

    }

    public static BaseActivityManager getInstance() {
        if (null == instance) {
            synchronized (BaseActivityManager.class) {
                if (null == instance) {
                    instance = new BaseActivityManager();
                }
            }
        }
        return instance;
    }

    public int size() {
        return activities.size();
    }

    public synchronized Activity getForwardActivity() {
        return size() > 0 ? activities.get(size() - 1) : null;
    }

    public synchronized void addActivity(Activity activity) {
        activities.add(activity);
    }

    public synchronized void removeActivity(Activity activity) {
        if (activities.contains(activity)) {
            activities.remove(activity);
        }
    }

    public synchronized void clear() {
        for (int i = activities.size() - 1; i > -1; i--) {
            Activity activity = activities.get(i);
            removeActivity(activity);
            activity.finish();
            i = activities.size();
        }
    }

    public synchronized void clearTop() {
        for (int i = activities.size() - 2; i > -1; i--) {
            Activity activity = activities.get(i);
            removeActivity(activity);
            activity.finish();
            i = activities.size() - 1;
        }
    }


    public int getSize() {
        if (activities == null) {
            return 0;
        }
        return activities.size();
    }

    /**
     * 检查 是否 包含lockactivity
     *
     * @param name
     * @return
     */
    public boolean contains(String name) {
        for (Activity activity : activities) {
            if (activity.getClass().getSimpleName().equals(name)) {
                return true;
            }
        }
        return false;
    }


}
