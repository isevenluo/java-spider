package org.pekxxoo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by chong on 2017/8/8.
 */
public class AsyncTaskExecutorUtil {
    private static final Logger logger = LoggerFactory.getLogger(AsyncTaskExecutorUtil.class);
    private static final AsyncTaskExecutorUtil instance = new AsyncTaskExecutorUtil();
    private ThreadPoolExecutor executor;

    private static final int capacity = 100000;

    private AsyncTaskExecutorUtil(){
    }

    public static void execute(Runnable task){
        if(instance.executor==null){
            synchronized(instance){
                if(instance.executor==null){
                    try{
                        instance.executor = new ThreadPoolExecutor(
                                10, 10, 30, TimeUnit.SECONDS,
                                new LinkedBlockingQueue<Runnable>(capacity),
                                new ThreadPoolExecutor.CallerRunsPolicy());
                    }catch(Throwable ex){
                        logger.error("Intial AsyncTaskExecutorUtil failed!", ex);
                    }
                }
            }
        }
        try{
            instance.executor.execute(task);
        }catch(Throwable ex){
            logger.error("Execute AsyncTaskExecutorUtil failed!", ex);
        }
    }

    public static void sleep(long millions) {
        try {
            Thread.sleep(millions);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
