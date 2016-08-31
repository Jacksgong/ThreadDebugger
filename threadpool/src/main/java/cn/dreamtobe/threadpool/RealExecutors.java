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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The factory of RealExecutor.
 */

class RealExecutors {
    private static final IExecutor TEMPORARY_CACHED_THREAD_POOL = ThreadPools.
            newCachedPool(5L, TimeUnit.SECONDS, "GlobalCachedThreadPool");

    static class ExceedWait extends RealExecutor {
        private final static String TAG = "ExceedWait";

        public ExceedWait(int corePoolSize,
                          int maximumPoolSize, long keepAliveTime, TimeUnit unit, String prefixName) {

            super(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                    new ExceedWaitQueue(),
                    new RejectedExecutionHandler() {
                        @Override
                        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                            if (!executor.isShutdown()) {
                                ThreadPoolLog.d(TAG, "put the rejected command to the exceed queue" +
                                        " in the rejectedExecution method: %s", r);
                                ((ExceedWaitQueue) executor.getQueue()).putExceed(r);
                            }
                        }
                    }, prefixName);
        }

        @Override
        public void execute(Runnable command) {
            final ExceedWaitQueue queue = (ExceedWaitQueue) getQueue();
            if (!isShutdown() && queue.exceedSize() > 0) {
                queue.putExceed(command);
                ThreadPoolLog.d(TAG, "put the rejected command to the exceed queue in " +
                        "the execute method: %s", command);
                return;
            }
            super.execute(command);
        }

        @Override
        public List<Runnable> shutdownNow() {
            List<Runnable> tasks = super.shutdownNow();
            tasks.addAll(((ExceedWaitQueue) getQueue()).drainExceedQueue());
            return tasks;
        }

        @Override
        public long getTaskCount() {
            return super.getTaskCount() + ((ExceedWaitQueue) getQueue()).exceedSize();
        }
    }

    private static class ExceedWaitQueue extends SynchronousQueue<Runnable> {

        private final static String TAG = "ExceedWaitQueue";
        private final LinkedBlockingQueue<Runnable> mExceedQueue = new LinkedBlockingQueue<>();

        ExceedWaitQueue() {
            super(true);
        }

        @Override
        public boolean offer(Runnable runnable) {
            ThreadPoolLog.d(TAG, "offer() called with: runnable = [%s]", runnable);
            boolean result = super.offer(runnable);
            ThreadPoolLog.d(TAG, "offer() returned: %B", result);
            return result;
        }

        @Override
        public Runnable poll(long timeout, TimeUnit unit) throws InterruptedException {
            ThreadPoolLog.d(TAG, "poll() called with: timeout = [%d], unit = [%s]", timeout, unit);
            Runnable result = super.poll(timeout, unit);
            if (mExceedQueue.size() > 0 && result == null) {
                result = mExceedQueue.poll(timeout, unit);
            }
            ThreadPoolLog.d(TAG, "poll() returned: %s", result);
            return result;
        }

        @Override
        public Runnable take() throws InterruptedException {
            ThreadPoolLog.d(TAG, "take() called");
            Runnable result = super.take();
            if (mExceedQueue.size() > 0 && result == null) {
                result = mExceedQueue.take();
            }
            ThreadPoolLog.d(TAG, "take() returned: %s", result);
            return result;
        }

        @Override
        public boolean remove(Object o) {
            ThreadPoolLog.d(TAG, "remove() called with: o = [%s]", o);
            return mExceedQueue.remove(o);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            ThreadPoolLog.d(TAG, "removeAll() called with: c = [%s]", c);
            return mExceedQueue.removeAll(c);
        }

        public int exceedSize() {
            return mExceedQueue.size();
        }

        public void putExceed(Runnable e) {
            mExceedQueue.offer(e);
        }

        public List<Runnable> drainExceedQueue() {
            BlockingQueue<Runnable> q = mExceedQueue;
            ArrayList<Runnable> taskList = new ArrayList<>();
            q.drainTo(taskList);
            if (!q.isEmpty()) {
                for (Runnable r : q.toArray(new Runnable[0])) {
                    if (q.remove(r))
                        taskList.add(r);
                }
            }
            return taskList;
        }
    }


    static class ExceedDiscard extends RealExecutor {

        public ExceedDiscard(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                             TimeUnit unit, String prefixName) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                    new SynchronousQueue<Runnable>(true),
                    new DiscardPolicy(), prefixName);
        }
    }

    static class ExceedCallerRuns extends RealExecutor {

        public ExceedCallerRuns(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                TimeUnit unit, String prefixName) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                    new SynchronousQueue<Runnable>(true),
                    new CallerRunsPolicy(), prefixName);
        }
    }

    static class ExceedCallImmediately extends RealExecutor {

        public ExceedCallImmediately(int corePoolSize, int maximumPoolSize, long keepAliveTime,
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

    static class Single extends RealExecutor {

        public Single(String prefixName) {
            super(1, 1, 0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(), // unbound queue.
                    new AbortPolicy(), // never meet the policy.
                    prefixName);
        }
    }

    static class Fixed extends RealExecutor {

        public Fixed(int threadCount, String prefixName) {
            super(threadCount, threadCount, 0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(), // unbound queue.
                    new AbortPolicy(), // never meet the policy.
                    prefixName);
        }
    }

    static class Cached extends RealExecutor {

        public Cached(long keepAliveTime, TimeUnit unit, String prefixName) {
            super(0, Integer.MAX_VALUE, keepAliveTime, unit, new SynchronousQueue<Runnable>(true),
                    new AbortPolicy(), // never meet the policy.
                    prefixName);
        }
    }

}
