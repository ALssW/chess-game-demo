package com.alva.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-02-15
 */
public class ThreadPoolUtil {

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;

    static {
        int coreThreadNum = Runtime.getRuntime().availableProcessors() * 3;
        int maxThreadNum = Runtime.getRuntime().availableProcessors() * 4;
	    THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
                coreThreadNum,
                maxThreadNum,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static void submit(Runnable runnable){
	    THREAD_POOL_EXECUTOR.submit(runnable);
    }

}
