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

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The factory of RealExecutor.
 */

public class RealExecutors {
    private static final IExecutor TEMPORARY_CACHED_THREAD_POOL = ThreadPools.
            newCachedPool(5L, TimeUnit.SECONDS, "GlobalCachedThreadPool");

    public static class ExceedWaitExecutor extends RealExecutor {
        private final static String TAG = "ExceedWait";

        public ExceedWaitExecutor(int corePoolSize,
                                  int maximumPoolSize, long keepAliveTime, TimeUnit unit, String prefixName,
                                  ExceedWait.Queue queue, ExceedWait.RejectedHandler rejectedHandler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                    queue, rejectedHandler, prefixName);
        }

        public ExceedWaitExecutor(int corePoolSize,
                                  int maximumPoolSize, long keepAliveTime, TimeUnit unit, String prefixName) {
            this(corePoolSize, maximumPoolSize, keepAliveTime, unit, prefixName,
                    new ExceedWait.Queue(), new ExceedWait.RejectedHandler());
        }

        @Override
        public void execute(Runnable command) {
            final ExceedWait.Queue queue = (ExceedWait.Queue) getQueue();
            if (!isShutdown() && queue.exceedSize() > 0) {
                queue.putExceed(command, this);
                ThreadPoolLog.d(TAG, "put the rejected command to the exceed queue in " +
                        "the execute method: %s", command);
                return;
            }
            super.execute(command);
        }

        @Override
        public List<Runnable> shutdownNow() {
            List<Runnable> tasks = super.shutdownNow();
            tasks.addAll(((ExceedWait.Queue) getQueue()).drainExceedQueue());
            return tasks;
        }

        @Override
        public long getTaskCount() {
            return super.getTaskCount() + ((ExceedWait.Queue) getQueue()).exceedSize();
        }
    }

    static class ExceedDiscardExecutor extends RealExecutor {

        public ExceedDiscardExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                     TimeUnit unit, String prefixName) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                    new SynchronousQueue<Runnable>(true),
                    new DiscardPolicy(), prefixName);
        }
    }

    static class ExceedCallerRunsExecutor extends RealExecutor {

        public ExceedCallerRunsExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                        TimeUnit unit, String prefixName) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                    new SynchronousQueue<Runnable>(true),
                    new CallerRunsPolicy(), prefixName);
        }
    }

    static class ExceedCallImmediatelyExecutor extends RealExecutor {

        public ExceedCallImmediatelyExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                             TimeUnit unit, String prefixName) {

            super(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                    new SynchronousQueue<Runnable>(true),
                    new RejectedExecutionHandler() {
                        @Override
                        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                            if (!executor.isShutdown()) {
                                TEMPORARY_CACHED_THREAD_POOL.
                                        execute(((RealExecutor) executor).getCommandNameMap().get(r),
                                                r);
                            }
                        }
                    }, prefixName);
        }
    }

    static class SinglePoolExecutor extends RealExecutor {

        public SinglePoolExecutor(String prefixName) {
            super(1, 1, 0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(), // unbound queue.
                    new AbortPolicy(), // never meet the policy.
                    prefixName);
        }
    }

    static class FixedPoolExecutor extends RealExecutor {

        public FixedPoolExecutor(int threadCount, String prefixName) {
            super(threadCount, threadCount, 0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(), // unbound queue.
                    new AbortPolicy(), // never meet the policy.
                    prefixName);
        }
    }

    static class CachedPoolExecutor extends RealExecutor {

        public CachedPoolExecutor(long keepAliveTime, TimeUnit unit, String prefixName) {
            super(0, Integer.MAX_VALUE, keepAliveTime, unit, new SynchronousQueue<Runnable>(true),
                    new AbortPolicy(), // never meet the policy.
                    prefixName);
        }
    }

}
