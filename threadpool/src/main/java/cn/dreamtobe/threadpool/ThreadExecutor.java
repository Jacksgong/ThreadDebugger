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

import java.security.InvalidParameterException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadExecutor implements IExecutor {
    private final IRealExecutor mExecutor;

    ExecutorService getExecutor() {
        return mExecutor;
    }

    public ThreadExecutor(IRealExecutor executor) {
        this.mExecutor = executor;
    }

    @Override
    public void execute(String name, Runnable runnable) {
        synchronized (mExecutor.getCommandNameMap()) {
            mExecutor.getCommandNameMap().put(runnable, name);
        }
        mExecutor.execute(runnable);
    }

    @Override
    public <T> Future<T> submit(String name, Runnable task, T result) {
        if (task == null) throw new NullPointerException();
        RunnableFuture<T> fTask = new FutureTask(task, result);
        execute(name, fTask);
        return fTask;
    }

    @Override
    public Future<?> submit(String name, Runnable task) {
        if (task == null) throw new NullPointerException();
        RunnableFuture<Void> fTask = new FutureTask(task, null);
        execute(name, fTask);
        return fTask;
    }

    @Override
    public <T> Future<T> submit(String name, Callable<T> task) {
        if (task == null) throw new NullPointerException();
        RunnableFuture<T> fTask = new FutureTask(task);
        execute(name, fTask);
        return fTask;
    }

    @Override
    public boolean remove(Runnable command) {
        return mExecutor.remove(command);
    }

    @Override
    public boolean isShutdown() {
        return mExecutor.isShutdown();
    }

    void shutdown() {
        mExecutor.shutdown();
    }

    List<Runnable> shutdownNow() {
        return mExecutor.shutdownNow();
    }

    public static class Exposed {
        private final ThreadExecutor mThreadExecutor;

        public Exposed(IExecutor threadExecutor) {
            if (threadExecutor instanceof ThreadExecutor) {
                this.mThreadExecutor = (ThreadExecutor) threadExecutor;
            } else {
                throw new InvalidParameterException("The exposed only support the ThreadExecutor instance!");
            }
        }

        /**
         * @return The ExecutorService.
         */
        public ExecutorService getExecutor() {
            return this.mThreadExecutor.getExecutor();
        }

        /**
         * The same to {@link ThreadPoolExecutor#shutdown()}.
         */
        public void shutdown() {
            this.mThreadExecutor.shutdown();
        }

        /**
         * The same to {@link ThreadPoolExecutor#shutdownNow()}
         */
        public List<Runnable> shutdownNow() {
            return this.mThreadExecutor.shutdownNow();
        }
    }
}