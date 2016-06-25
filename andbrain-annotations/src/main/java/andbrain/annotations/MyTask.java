/**
 * Copyright (C) 2013-2016 www.andbrain.com
 * Lets Build Awesome Mobile Apps Using The Simple Way
 */
package andbrain.annotations;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

public class MyTask {

    private static Handler uiHandler = new Handler(Looper.getMainLooper());

    public MyTask() {
    }

    public static void doOnMainThread(final OnMainThread onMainThread) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                onMainThread.doInUIThread();
            }
        });
    }

    public static void doInBackground(final OnBackground onBackground) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                onBackground.doOnBackground();
            }
        }).start();
    }

    public static FutureTask doInBackground(final OnBackground onBackground, ExecutorService executor) {
        FutureTask task = (FutureTask) executor.submit(new Runnable() {
            @Override
            public void run() {
                onBackground.doOnBackground();
            }
        });

        return task;
    }

    public interface OnMainThread {
        public void doInUIThread();
    }

    public interface OnBackground {
        public void doOnBackground();
    }

}
