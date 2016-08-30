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
 * Created by Jacksgong on 29/08/2016.
 */

public class ThreadPools {

    /**
     * If the size of active command equal to the {@code maximumPoolSize}, the further command will
     * be enqueued to the waiting queue, and will be executed when the size of active command less
     * than the {@code maximumPoolSize}.
     * <b/>
     * In this thread pool, it use the TransferQueue in {@link java.util.concurrent.SynchronousQueue}
     * to hold the normal command.
     *
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param prefixName
     * @return
     */
    public static IExecutor newExceedWaitPool(int corePoolSize,
                                              int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                              String prefixName) {
        return new ThreadExecutor(new RealExecutors.
                ExceedWait(corePoolSize, maximumPoolSize, keepAliveTime, unit, prefixName));
    }

    /**
     * If the size of active command equal to the {@code maximumPoolSize}, the further command will
     * be discard.
     *
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param prefixName
     * @return
     * @see java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy
     */
    public static IExecutor newExceedDiscardPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                                 TimeUnit unit, String prefixName) {
        return new ThreadExecutor(new RealExecutors.
                ExceedDiscard(corePoolSize, maximumPoolSize, keepAliveTime, unit, prefixName));
    }

    /**
     * If the size of active command equal to the {@code maximumPoolSize}, the further command will
     * be executed immediately in the caller thread.
     *
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param prefixName
     * @return
     * @see java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy
     */
    public static IExecutor newExceedCallerRunsPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                                    TimeUnit unit, String prefixName) {
        return new ThreadExecutor(new RealExecutors.
                ExceedCallerRuns(corePoolSize, maximumPoolSize, keepAliveTime, unit, prefixName));
    }

    /**
     * If the size of active command equal to the {@code maximumPoolSize}, the further command will
     * be executed immediately in the global temporary unbound thread pool.
     *
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param prefixName
     * @return
     */
    public static IExecutor newExceedCallImmediatelyPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                                         TimeUnit unit, String prefixName) {
        return new ThreadExecutor(new RealExecutors.
                ExceedCallImmediately(corePoolSize, maximumPoolSize, keepAliveTime, unit, prefixName));
    }

    /**
     * The same to the {@link Executors#newSingleThreadExecutor()}.
     *
     * @param prefixName
     * @return
     */
    public static IExecutor newSinglePool(String prefixName) {
        return new ThreadExecutor(new RealExecutors.Single(prefixName));
    }

    /**
     * The same to the {@link Executors#newFixedThreadPool(int)}.
     *
     * @param threadCount
     * @param prefixName
     * @return
     */
    public static IExecutor newFixedPool(int threadCount, String prefixName) {
        return new ThreadExecutor(new RealExecutors.Fixed(threadCount, prefixName));
    }

    /**
     * The same to the {@link Executors#newCachedThreadPool()}.
     *
     * @param keepAliveTime
     * @param unit
     * @param prefixName
     * @return
     */
    public static IExecutor newCachedPool(long keepAliveTime, TimeUnit unit, String prefixName) {
        return new ThreadExecutor(new RealExecutors.Cached(keepAliveTime, unit, prefixName));
    }

}
