package com.ncu.springboot.core.thread;

import com.ncu.springboot.core.context.DataContext;
import com.ncu.springboot.core.util.CollectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExecutorServiceManager {

    public static final int DEFAULT_POOL_SIZE = 10;


    private static Map<String, ExecutorService> executorServiceMap = new HashMap<>();

    private static Lock executorServiceMapLock = new ReentrantLock();

    static {
        init();
    }

    private static void init() {
        // 注册关闭钩子
        Runtime.getRuntime().addShutdownHook( new Thread( () -> {
            executorServiceMapLock.lock();
            try {
                executorServiceMap.values().forEach( executorService -> executorService.shutdown() );
            } finally {
                executorServiceMapLock.unlock();
            }
        }) );
    }

    public static ExecutorService getCachedThreadPool(final String name) {
        ExecutorService executorService;
        executorServiceMapLock.lock();
        try {
            executorService = executorServiceMap.get(name);
            if (null == executorService) {
                // 创建执行服务
                executorService = Executors.newCachedThreadPool(createThreadFactory(name));
                executorServiceMap.put(name, executorService);
            }
        } finally {
            executorServiceMapLock.unlock();
        }
        return executorService;
    }

    public static ScheduledExecutorService getScheduledThreadPool(final String name) {
        return getScheduledThreadPool(DEFAULT_POOL_SIZE, name);
    }

    public static ScheduledExecutorService getScheduledThreadPool(int corePoolSize, String name) {
        ExecutorService executorService;
        executorServiceMapLock.lock();
        try {
            executorService = executorServiceMap.get(name);
            if (null == executorService) {
                // 创建执行服务
                executorService = Executors.newScheduledThreadPool(corePoolSize, createThreadFactory(name));
                executorServiceMap.put(name, executorService);
            }
        } finally {
            executorServiceMapLock.unlock();
        }
        return (ScheduledExecutorService) executorService;
    }

    private static ThreadFactory createThreadFactory(final String threadGroupName) {
        return new ThreadFactory() {
            private String threadNamePrefix = threadGroupName + "Thread-";
            private int threadIndex = 0;

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName(threadNamePrefix + (++threadIndex));
                t.setDaemon(true);
                return t;
            }
        };
    }

    public static <T> Future<T> submit(ExecutorService executorService, final Callable<T> task,
                                       String... penetratedContextKeys) {
        // 判断是否需要复制上下文变量
        final Map<String, Object> penetratedContext = preparePenetratedContext(penetratedContextKeys);
        return executorService.submit( () -> {
            if (null != penetratedContext) {
                // 复制上下文变量
                try {
                    CollectionUtil.merge(DataContext.getStore(), penetratedContext);
                    return task.call();
                } finally {
                    DataContext.clear();
                }
            } else {
                // 无需复制上下文变量
                return task.call();
            }
        });
    }

    public static Future<?> submit(ExecutorService executorService, Runnable task, String... penetratedContextKeys) {
        return submit(executorService, task, null, penetratedContextKeys);
    }

    public static <T> Future<T> submit(ExecutorService executorService, final Runnable task, T result,
                                       String... penetratedContextKeys) {
        // 判断是否需要复制上下文变量
        final Map<String, Object> penetratedContext = preparePenetratedContext(penetratedContextKeys);
        return executorService.submit(() -> {
            if (null != penetratedContext) {
                // 复制上下文变量
                try {
                    CollectionUtil.merge(DataContext.getStore(), penetratedContext);
                    task.run();
                } finally {
                    DataContext.clear();
                }
            } else {
                // 无需复制上下文变量
                task.run();
            }
        }, result);
    }

    private static Map<String, Object> preparePenetratedContext(String... penetratedContextKeys) {
        Map<String, Object> penetratedContext;
        if (null != penetratedContextKeys && 0 != penetratedContextKeys.length) {
            penetratedContext = CollectionUtil.subMap(DataContext.getStore(), penetratedContextKeys);
        } else {
            penetratedContext = null;
        }
        return penetratedContext;
    }

}
