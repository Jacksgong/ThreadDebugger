/*
 * Copyright (C) 2015-2016 Jacksgong(blog.dreamtobe.cn)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.dreamtobe.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The factory of the thread pool.
 * <p>
 * <em>Basic Rules</em>
 * <p>
 * When a new task is submitted in method execute(Runnable), and fewer than corePoolSize threads are
 * running, a new thread is created to handle the request, even if other worker threads are idle. If
 * there are more than corePoolSize but less than maximumPoolSize threads running, a new thread is
 * created to handle the request too, but when it turn to idle and the interval time of waiting for
 * new tasks more than keepAliveTime, it will be terminate to reduce the cost of resources.
 */

public class ThreadPools {

    /**
     * Under the premise of satisfying the Basic Rules, if there are {@code maximumPoolSize} tasks
     * running, the further task will be enqueued to the waiting queue, and will be executed when
     * the size of running tasks less than {@code maximumPoolSize}.
     * <b/>
     * In this thread pool, it use the TransferQueue in {@link java.util.concurrent.SynchronousQueue}
     * to hold the normal command.
     *
     * @param corePoolSize    the number of threads to keep in the pool, even
     *                        if they are idle.
     * @param maximumPoolSize the maximum number of threads to allow in the
     *                        pool.
     * @param keepAliveTime   when the number of threads is greater than
     *                        the core, this is the maximum time that excess idle threads
     *                        will wait for new tasks before terminating.
     * @param unit            the time unit for the {@code keepAliveTime} argument.
     * @param prefixName      the prefix name of this thread pool.
     * @return The executor of a new ExceedWait thread pool.
     */
    public static IExecutor newExceedWaitPool(int corePoolSize,
                                              int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                              String prefixName) {
        return new ThreadExecutor(new RealExecutors.
                ExceedWaitExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, prefixName));
    }

    /**
     * Under the premise of satisfying the Basic Rules, if there are {@code maximumPoolSize} tasks
     * running, the further task will be discard.
     *
     * @param corePoolSize    the number of threads to keep in the pool, even
     *                        if they are idle.
     * @param maximumPoolSize the maximum number of threads to allow in the
     *                        pool.
     * @param keepAliveTime   when the number of threads is greater than
     *                        the core, this is the maximum time that excess idle threads
     *                        will wait for new tasks before terminating.
     * @param unit            the time unit for the {@code keepAliveTime} argument.
     * @param prefixName      the prefix name of this thread pool.
     * @return The executor of a new ExceedDiscard thread pool.
     * @see java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy
     */
    public static IExecutor newExceedDiscardPool(int corePoolSize, int maximumPoolSize,
                                                 long keepAliveTime, TimeUnit unit,
                                                 String prefixName) {
        return new ThreadExecutor(new RealExecutors.
                ExceedDiscardExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, prefixName));
    }

    /**
     * Under the premise of satisfying the Basic Rules, if there are {@code maximumPoolSize} tasks
     * running, the further task will be executed immediately in the caller thread.
     *
     * @param corePoolSize    the number of threads to keep in the pool, even
     *                        if they are idle.
     * @param maximumPoolSize the maximum number of threads to allow in the
     *                        pool.
     * @param keepAliveTime   when the number of threads is greater than
     *                        the core, this is the maximum time that excess idle threads
     *                        will wait for new tasks before terminating.
     * @param unit            the time unit for the {@code keepAliveTime} argument.
     * @param prefixName      the prefix name of this thread pool.
     * @return The executor of a new ExceedCallerRuns thread pool.
     * @see java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy
     */
    public static IExecutor newExceedCallerRunsPool(int corePoolSize, int maximumPoolSize,
                                                    long keepAliveTime, TimeUnit unit,
                                                    String prefixName) {
        return new ThreadExecutor(new RealExecutors.
                ExceedCallerRunsExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, prefixName));
    }

    /**
     * Under the premise of satisfying the Basic Rules, if there are {@code maximumPoolSize} tasks
     * running, the further task will be executed immediately in the global temporary unbound thread
     * pool.
     *
     * @param corePoolSize    the number of threads to keep in the pool, even
     *                        if they are idle.
     * @param maximumPoolSize the maximum number of threads to allow in the
     *                        pool.
     * @param keepAliveTime   when the number of threads is greater than
     *                        the core, this is the maximum time that excess idle threads
     *                        will wait for new tasks before terminating.
     * @param unit            the time unit for the {@code keepAliveTime} argument.
     * @param prefixName      the prefix name of this thread pool.
     * @return The executor of a new ExceedCallImmediately thread pool.
     */
    public static IExecutor newExceedCallImmediatelyPool(int corePoolSize, int maximumPoolSize,
                                                         long keepAliveTime, TimeUnit unit,
                                                         String prefixName) {
        return new ThreadExecutor(new RealExecutors.
                ExceedCallImmediatelyExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, prefixName));
    }

    /**
     * The same to the {@link Executors#newSingleThreadExecutor()}.
     *
     * @param prefixName the prefix name of this thread pool.
     * @return The executor of a new Single thread pool.
     */
    public static IExecutor newSinglePool(String prefixName) {
        return new ThreadExecutor(new RealExecutors.SinglePoolExecutor(prefixName));
    }

    /**
     * The same to the {@link Executors#newFixedThreadPool(int)}.
     *
     * @param threadCount the number of threads in the pool.
     * @param prefixName  the prefix name of this thread pool.
     * @return The executor of a new Fixed thread pool.
     */
    public static IExecutor newFixedPool(int threadCount, String prefixName) {
        return new ThreadExecutor(new RealExecutors.FixedPoolExecutor(threadCount, prefixName));
    }

    /**
     * The same to the {@link Executors#newCachedThreadPool()}.
     *
     * @param keepAliveTime when the number of threads is greater than
     *                      the core, this is the maximum time that excess idle threads
     *                      will wait for new tasks before terminating.
     * @param unit          the time unit for the {@code keepAliveTime} argument.
     * @param prefixName    the prefix name of this thread pool.
     * @return the executor of a new Cached thread pool.
     */
    public static IExecutor newCachedPool(long keepAliveTime, TimeUnit unit, String prefixName) {
        return new ThreadExecutor(new RealExecutors.CachedPoolExecutor(keepAliveTime, unit, prefixName));
    }

}
