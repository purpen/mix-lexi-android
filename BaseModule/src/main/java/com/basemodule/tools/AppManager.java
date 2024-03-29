package com.basemodule.tools;

import android.app.Activity;

import java.util.Stack;

public final class AppManager {

    private Stack<Activity> activityStack;

    private static class SingletonInstance {
        static AppManager INSTANCE = new AppManager();
    }

    private AppManager() {
    }


    public static AppManager getAppManager() {
        return SingletonInstance.INSTANCE;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (activityStack == null || activityStack.empty()) return null;
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        if (activityStack == null) return;
        if (activityStack.empty()) return;
        Activity activity = activityStack.lastElement();
        if (activity != null) {
            activity.finish();
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activityStack == null) return;
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }


    /**
     * 查找某个activity
     * @param cls
     * @return
     */
    public Activity getActivity(Class<?> cls) {
        if (activityStack == null) return null;
        Activity currentActivity = null;
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                currentActivity = activity;
                break;
            }
        }
        return currentActivity;
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (activityStack == null) return;
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 从栈内移除
     */
    public void stackRemoveActivity(Activity activity) {
        if (activityStack != null && activityStack.contains(activity)) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack == null) return;
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            finishAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
        }
    }
}
