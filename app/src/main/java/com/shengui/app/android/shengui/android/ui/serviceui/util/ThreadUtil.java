package com.shengui.app.android.shengui.android.ui.serviceui.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/10/20 0020.
 */
public class ThreadUtil {

    private static ExecutorService executorService;

    public static void execute(Runnable task){
        if(executorService == null){
            executorService = Executors.newFixedThreadPool(10);
        }

        executorService.submit(task);
    }

}
