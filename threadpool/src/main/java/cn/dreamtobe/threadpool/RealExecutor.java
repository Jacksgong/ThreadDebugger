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

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RealExecutor extends ThreadPoolExecutor implements IRealExecutor {

    private final HashMap<Runnable, String> mCommandNameMap = new HashMap<>();
    private final static String STATUS_SPLIT = "::";

    public RealExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                        BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler,
                        String prefixName) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                new ThreadNameHandledFactory(prefixName), handler);
    }


    @Override
    protected void beforeExecute(Thread thread, Runnable r) {
        super.beforeExecute(thread, r);
        String[] nameArray = thread.getName().split(STATUS_SPLIT);

        final String threadName;
        final String commandName = mCommandNameMap.get(r);
        if (commandName == null) {
            threadName = nameArray[0] + STATUS_SPLIT + "running";
        } else {
            threadName = nameArray[0] + STATUS_SPLIT + "running-" + commandName;
            synchronized (mCommandNameMap) {
                mCommandNameMap.remove(r);
            }
        }

        thread.setName(threadName);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        final Thread thread = Thread.currentThread();
        String[] nameArray = thread.getName().split(STATUS_SPLIT);
        thread.setName(nameArray[0] + STATUS_SPLIT + "idle");
    }

    @Override
    public HashMap<Runnable, String> getCommandNameMap() {
        return mCommandNameMap;
    }
}