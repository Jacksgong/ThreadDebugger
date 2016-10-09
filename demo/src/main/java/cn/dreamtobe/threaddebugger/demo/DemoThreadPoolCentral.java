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

package cn.dreamtobe.threaddebugger.demo;

import java.util.concurrent.TimeUnit;

import cn.dreamtobe.threadpool.IExecutor;
import cn.dreamtobe.threadpool.ThreadPools;

/**
 * Created by Jacksgong on 27/08/2016.
 */

@SuppressWarnings("DefaultFileTemplate")
public class DemoThreadPoolCentral {

    private static final int CPU_PROCESSORS = Runtime.getRuntime().availableProcessors();

    private static final IExecutor IO = ThreadPools.newExceedWaitPool(2, /** core **/
            3, 5, TimeUnit.SECONDS, /** max, idle-time **/
            "io");

    private static final IExecutor COMPUTATION = ThreadPools.newFixedPool(CPU_PROCESSORS,
            "computation");

    private static final IExecutor NETWORK = ThreadPools.newNoCorePool(
            3, 2, TimeUnit.SECONDS, /** max, idle-time **/
            "network");

    private static final IExecutor TEST1 = ThreadPools.newExceedDiscardPool(2, /** core **/
            8, 2, TimeUnit.SECONDS, /** max, idle-time **/
            "test1-ExceedDiscard");

    private static final IExecutor TEST2 = ThreadPools.newExceedCallImmediatelyPool(2, /** core **/
            8, 2, TimeUnit.SECONDS, /** max, idle-time **/
            "test2-ExceedCallImmediately");

    private static final IExecutor TEST3 = ThreadPools.newCachedPool(2, TimeUnit.SECONDS, /** max, idle-time **/
            "test3-Cached");

    private static final IExecutor TEST4 = ThreadPools.newExceedCallerRunsPool(2, /** core **/
            8, 2, TimeUnit.SECONDS, /** max, idle-time **/
            "test4-ExceedCallerRuns");


    public static IExecutor io() {
        return IO;
    }

    public static IExecutor computation() {
        return COMPUTATION;
    }

    public static IExecutor network() {
        return NETWORK;
    }

    public static IExecutor test1() {
        return TEST1;
    }

    public static IExecutor test2() {
        return TEST2;
    }

    public static IExecutor test3() {
        return TEST3;
    }

    public static IExecutor test4() {
        return TEST4;
    }
}
