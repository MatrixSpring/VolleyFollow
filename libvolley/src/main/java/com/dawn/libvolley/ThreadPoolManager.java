package com.dawn.libvolley;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {
    public static ThreadPoolManager instance = new ThreadPoolManager();

    public static ThreadPoolManager getInstance(){
        if(null != instance){
            synchronized (ThreadPoolManager.class){
                if(null != instance){
                    instance = new ThreadPoolManager();
                }
            }
        }
        return instance;
    }

    private ThreadPoolExecutor threadPoolExecutor;

    private LinkedBlockingQueue<Future<?>> linkedBlockingQueue = new LinkedBlockingQueue<>();

    private ThreadPoolManager(){
        threadPoolExecutor = new ThreadPoolExecutor(5,10,10,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(4),handler);
        threadPoolExecutor.execute(runnable);
    }

    private RejectedExecutionHandler handler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                linkedBlockingQueue.put(new FutureTask<Object>(r, null));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                FutureTask futureTask = null;
                try {
                    futureTask = (FutureTask) linkedBlockingQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(null != futureTask && null != threadPoolExecutor){
                    threadPoolExecutor.execute(futureTask);
                }
            }
        }
    };

    //加任务进来
    public <T> void execute(FutureTask<T> futureTask){
        if(null != futureTask){
            try {
                linkedBlockingQueue.put(futureTask);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
