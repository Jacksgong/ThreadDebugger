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

package cn.dreamtobe.threaddebugger;

/**
 * @author jacks
 * @since 2019-07-29 12:03
 */
public class ThreadUtils {

    private static ThreadGroup rootGroup = null;

    /**
     * @return threads but its count not equal to real thread count, you need filter null manually.
     */
    public static Thread[] getAllThreads() {
        ThreadGroup rootGroup = ThreadUtils.rootGroup;
        if (rootGroup == null) {
            rootGroup = Thread.currentThread().getThreadGroup();
            ThreadGroup parentGroup;
            while ((parentGroup = rootGroup.getParent()) != null) {
                rootGroup = parentGroup;
            }
            ThreadUtils.rootGroup = rootGroup;
        }

        Thread[] threads = new Thread[rootGroup.activeCount()];
        while (rootGroup.enumerate(threads, true) == threads.length) {
            // thread array not big enough for enumerate try more
            threads = new Thread[threads.length + threads.length / 2];
        }

        return threads;
    }
}
