# ThreadDebugger

[![ThreadDebugger][thread_debugger_svg]][thread_debugger_link]
[![ThreadPools][thread_pools_svg]][thread_pools_link]
[![Build Status][build_status_svg]][build_status_link]

[README](https://github.com/Jacksgong/ThreadDebugger/blob/master/README.md)|[中文文档](https://github.com/Jacksgong/ThreadDebugger/blob/master/README_zh.md)

---

这个库包含: 线程调试器(threaddebugger)、线程池创建工具(threadpools)。

#### 线程调试器

虽然我们知道已经有很多方法进行线程调试，如通过Android Studio Monitor 进行Allocation Tracking，在分析结果中会带有期间线程的一些信息；或者是通过Android Device Monitor进行Method Profiling，在分析结果中也会带有期间线程的一些信息。但是每次都需要进行启动关闭，并且每次结果分析都要几秒，几十秒甚至更久，显得不是很灵活。

ThreadDebugger是一个简单易用的线程调试器，可以帮助您随时查看应用中所有线程的使用情况，以及变化情况。

#### 线程池创建工具

该线程池创建工具，相比系统的Executors强制要求每次执行任务的时候都需要指定任务的名称，以便于更好的调试与监控，并且提供非常便捷的创建相关规则的线程池。

---

## I. 安装

```
```

## II. 启动监控

 ```java
 IThreadDebugger debugger = ThreadDebugger.install(
         ThreadDebuggers.createWithCommonThreadKey() /** The ThreadDebugger with known thread Categories **/
                 // add Thread Category
                 .add("IO", "IO")
                 .add("computation", "computation")
                 .add("network", "network")
                 .add("test1", "test1")
                 .add("test2", "test2")
                 .add("test3", "test3")
                 .add("test4", "test4"),

         2000, /** The frequent of Updating Thread Activity information **/

         new ThreadDebugger.ThreadChangedCallback() { /** The threads changed callback **/
             @Override
             public void onChanged(IThreadDebugger debugger) {
                 // callback this method when the threads in this application has changed.
                 Log.d(TAG, debugger.drawUpEachThreadInfoDiff());
                 Log.d(TAG, debugger.drawUpEachThreadSizeDiff());
                 Log.d(TAG, debugger.drawUpEachThreadSize());
                 Log.d(TAG, debugger.drawUpEachThreadInfo());
                 Log.d(TAG, debugger.drawUpUnknownInfo());
             }
         });
 ```

## III. Logcat的输出

#### 1. drawUpEachThreadInfoDiff

> `adb logcat -v raw | grep drawUpEachThreadInfoDiff:`

```
drawUpEachThreadInfoDiff: Thread count = 13. Thread differ : +13. main: +1 [(+)main] | Binder: +2 [(+)Binder_2, (+)Binder_1] | Finalizer: +2 [(+)FinalizerWatchdogDaemon, (+)FinalizerDaemon] | RenderThread: +1 [(+)RenderThread] | HeapTaskDaemon: +1 [(+)HeapTaskDaemon] | ReferenceQueueDaemon: +1 [(+)ReferenceQueueDaemon] | JDWP: +1 [(+)JDWP] | unknown: +4 [(+)hwuiTask1, (+)hwuiTask2, (+)ThreadDebugger, (+)Signal Catcher]
drawUpEachThreadInfoDiff: Thread count = 15. Thread differ : +2. computation: +1 [(+)computation-1::running-Test1:7s] | test1: +1 [(+)test1-ExceedDiscard-1::running-RandomThreadWork]
drawUpEachThreadInfoDiff: Thread count = 16. Thread differ : +1. test1: +1 [(+)test1-ExceedDiscard-2::running-Test2:1s]
drawUpEachThreadInfoDiff: Thread count = 17. Thread differ : +1. computation: +1 [(+)computation-2::running-Test3:8s] | test1: SWAP [(+)test1-ExceedDiscard-2::idle, (-)test1-ExceedDiscard-2::running-Test2:1s]
drawUpEachThreadInfoDiff: Thread count = 17. Thread differ : 0. computation: SWAP [(+)computation-1::idle, (-)computation-1::running-Test1:7s]
drawUpEachThreadInfoDiff: Thread count = 18. Thread differ : +1. computation: +1 [(+)computation-3::running-Test4:9s]
drawUpEachThreadInfoDiff: Thread count = 19. Thread differ : +1. network: +1 [(+)network-1::running-Test5:1s]
drawUpEachThreadInfoDiff: Thread count = 19. Thread differ : 0. network: SWAP [(+)network-1::idle, (-)network-1::running-Test5:1s]
drawUpEachThreadInfoDiff: Thread count = 20. Thread differ : +1. IO: +1 [(+)io-1::running-Test6:1s]
drawUpEachThreadInfoDiff: Thread count = 20. Thread differ : 0. IO: SWAP [(+)io-1::idle, (-)io-1::running-Test6:1s]
drawUpEachThreadInfoDiff: Thread count = 20. Thread differ : 0. computation: SWAP [(+)computation-2::idle, (-)computation-2::running-Test3:8s] | test1: SWAP [(+)test1-ExceedDiscard-1::idle, (-)test1-ExceedDiscard-1::running-RandomThreadWork]
drawUpEachThreadInfoDiff: Thread count = 20. Thread differ : 0. computation: SWAP [(+)computation-3::idle, (-)computation-3::running-Test4:9s]
```

#### 2. drawUpEachThreadSizeDiff

> `adb logcat -v raw | grep drawUpEachThreadSizeDiff:`

```
drawUpEachThreadSizeDiff: Thread count = 13. Thread differ : +13. main: +1 | Binder: +2 | Finalizer: +2 | RenderThread: +1 | HeapTaskDaemon: +1 | ReferenceQueueDaemon: +1 | JDWP: +1 | unknown: +4 [(+)hwuiTask1, (+)hwuiTask2, (+)ThreadDebugger, (+)Signal Catcher]
drawUpEachThreadSizeDiff: Thread count = 15. Thread differ : +2. computation: +1 | test1: +1
drawUpEachThreadSizeDiff: Thread count = 16. Thread differ : +1. test1: +1
drawUpEachThreadSizeDiff: Thread count = 17. Thread differ : +1. computation: +1
drawUpEachThreadSizeDiff: Thread count = 17. Thread size has not changed.
drawUpEachThreadSizeDiff: Thread count = 18. Thread differ : +1. computation: +1
drawUpEachThreadSizeDiff: Thread count = 19. Thread differ : +1. network: +1
drawUpEachThreadSizeDiff: Thread count = 19. Thread size has not changed.
drawUpEachThreadSizeDiff: Thread count = 20. Thread differ : +1. IO: +1
drawUpEachThreadSizeDiff: Thread count = 20. Thread size has not changed.
drawUpEachThreadSizeDiff: Thread count = 20. Thread size has not changed.
drawUpEachThreadSizeDiff: Thread count = 20. Thread size has not changed.
```

#### 3. drawUpEachThreadSize

> `adb logcat -v raw | grep drawUpEachThreadSize:`

```
drawUpEachThreadSize: Thread count = 13. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | unknown: 4
drawUpEachThreadSize: Thread count = 15. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | computation: 1 | test1: 1 | unknown: 4
drawUpEachThreadSize: Thread count = 16. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | computation: 1 | test1: 2 | unknown: 4
drawUpEachThreadSize: Thread count = 17. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | computation: 2 | test1: 2 | unknown: 4
drawUpEachThreadSize: Thread count = 17. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | computation: 2 | test1: 2 | unknown: 4
drawUpEachThreadSize: Thread count = 18. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | computation: 3 | test1: 2 | unknown: 4
drawUpEachThreadSize: Thread count = 19. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | computation: 3 | network: 1 | test1: 2 | unknown: 4
drawUpEachThreadSize: Thread count = 19. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | computation: 3 | network: 1 | test1: 2 | unknown: 4
drawUpEachThreadSize: Thread count = 20. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | IO: 1 | computation: 3 | network: 1 | test1: 2 | unknown: 4
drawUpEachThreadSize: Thread count = 20. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | IO: 1 | computation: 3 | network: 1 | test1: 2 | unknown: 4
drawUpEachThreadSize: Thread count = 20. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | IO: 1 | computation: 3 | network: 1 | test1: 2 | unknown: 4
drawUpEachThreadSize: Thread count = 20. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | IO: 1 | computation: 3 | network: 1 | test1: 2 | unknown: 4
```

#### 4. drawUpEachThreadInfo

> `adb logcat -v raw | grep drawUpEachThreadInfo:`

```
drawUpEachThreadInfo: Thread count = 13. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerWatchdogDaemon, FinalizerDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | unknown[hwuiTask1, hwuiTask2, ThreadDebugger, Signal Catcher]
drawUpEachThreadInfo: Thread count = 15. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | computation[computation-1::running-Test1:7s] | test1[test1-ExceedDiscard-1::running-RandomThreadWork] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
drawUpEachThreadInfo: Thread count = 16. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | computation[computation-1::running-Test1:7s] | test1[test1-ExceedDiscard-1::running-RandomThreadWork, test1-ExceedDiscard-2::running-Test2:1s] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
drawUpEachThreadInfo: Thread count = 17. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | computation[computation-1::running-Test1:7s, computation-2::running-Test3:8s] | test1[test1-ExceedDiscard-1::running-RandomThreadWork, test1-ExceedDiscard-2::idle] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
drawUpEachThreadInfo: Thread count = 17. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | computation[computation-1::idle, computation-2::running-Test3:8s] | test1[test1-ExceedDiscard-1::running-RandomThreadWork, test1-ExceedDiscard-2::idle] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
drawUpEachThreadInfo: Thread count = 18. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | computation[computation-1::idle, computation-2::running-Test3:8s, computation-3::running-Test4:9s] | test1[test1-ExceedDiscard-1::running-RandomThreadWork, test1-ExceedDiscard-2::idle] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
drawUpEachThreadInfo: Thread count = 19. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | computation[computation-1::idle, computation-2::running-Test3:8s, computation-3::running-Test4:9s] | network[network-1::running-Test5:1s] | test1[test1-ExceedDiscard-1::running-RandomThreadWork, test1-ExceedDiscard-2::idle] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
drawUpEachThreadInfo: Thread count = 19. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | computation[computation-1::idle, computation-2::running-Test3:8s, computation-3::running-Test4:9s] | network[network-1::idle] | test1[test1-ExceedDiscard-1::running-RandomThreadWork, test1-ExceedDiscard-2::idle] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
drawUpEachThreadInfo: Thread count = 20. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | IO[io-1::running-Test6:1s] | computation[computation-1::idle, computation-2::running-Test3:8s, computation-3::running-Test4:9s] | network[network-1::idle] | test1[test1-ExceedDiscard-1::running-RandomThreadWork, test1-ExceedDiscard-2::idle] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
drawUpEachThreadInfo: Thread count = 20. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | IO[io-1::idle] | computation[computation-1::idle, computation-2::running-Test3:8s, computation-3::running-Test4:9s] | network[network-1::idle] | test1[test1-ExceedDiscard-1::running-RandomThreadWork, test1-ExceedDiscard-2::idle] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
drawUpEachThreadInfo: Thread count = 20. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | IO[io-1::idle] | computation[computation-1::idle, computation-2::idle, computation-3::running-Test4:9s] | network[network-1::idle] | test1[test1-ExceedDiscard-1::idle, test1-ExceedDiscard-2::idle] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
drawUpEachThreadInfo: Thread count = 20. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | IO[io-1::idle] | computation[computation-1::idle, computation-2::idle, computation-3::idle] | network[network-1::idle] | test1[test1-ExceedDiscard-1::idle, test1-ExceedDiscard-2::idle] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
```

#### 5. drawUpUnknownInfo

> `adb logcat -v raw | grep drawUpUnknownInfo:`

```
drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = +4. unknown[hwuiTask1, hwuiTask2, ThreadDebugger, Signal Catcher]
drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
```

## IV. IThreadDebugger中的方法

| 方法 | 功能
| --- | ---
| add(key:String) | 为当前调试器添加一个名称前缀为key，并且别名为key的线程类别
| add(startWithKey:String , alias:String) | 为当前调试器添加一个名称前缀为startWithKey，并且别名为alias的线程类别
| refresh() | 刷新线程情况
| drawUpEachThreadSize() | 返回当前所有类别线程的个数
| drawUpEachThreadInfo() | 返回当前所有类别中线程的详情
| drawUpEachThreadSizeDiff() | 返回对比上次刷新，线程个数的变化
| drawUpEachThreadInfoDiff() | 返回对比上次刷新，线程变化的详情
| drawUpUnknownInfo() | 返回当前所有未分类的线程类别中线程的详情
| isSizeChanged() | 对比上次刷新，线程个数是否有变化
| isChanged() | 对比上次刷新，线程是否有变化

## V. ThreadPools

> 该线程池创建工具，相比系统的Executors强制要求每次执行任务的时候都需要指定任务的名称，以便于更好的调试与监控，并且提供非常便捷的创建相关规则的线程池。

> - 入口: [ThreadPools](https://github.com/Jacksgong/ThreadDebugger/blob/master/threadpool/src/main/java/cn/dreamtobe/threadpool/ThreadPools.java)
> - 案例: [DemoThreadPoolCentral](https://github.com/Jacksgong/ThreadDebugger/blob/master/demo/src/main/java/cn/dreamtobe/threaddebugger/demo/DemoThreadPoolCentral.java)

#### 1. 通用规则

始终保活corePoolSize的线程，当任务数大于corePoolSize时小于maximumPoolSize时，会继续创建线程进行执行；当大于corePoolSize小于maximumPoolSize的线程闲置超过keepAliveTime时间时，这部分线程会被回收。

#### 2. 方法说明

| 方法 | 功能
| --- | ---
| newExceedWaitPool | 在满足**通用规则**的前提下，当任务数大于maximumPoolSize，溢出的任务会在溢出等待队列中等待，直到有任务执行完成，线程空闲出来再进行执行，溢出等待队列符合Fifo进出队列规则
| newExceedDiscardPool | 在满足**通用规则**的前提下，当任务数大于maximumPoolSize，溢出的任务会直接被丢弃
| newExceedCallerRunsPool | 在满足**通用规则**的前提下，当任务数大于maximumPoolSize，溢出的任务在调用线程执行方法(`execute`/`submit`)所在线程直接执行
| newExceedCallImmediatelyPool | 在满足**通用规则**的前提下，当任务数大于maximumPoolSize，溢出的任务在全局缓存线程池中直接执行
| newSinglePool | 规则与`java.util.concurrent.Executors#newSingleThreadExecutor()`相同
| newFixedPool | 规则与`java.util.concurrent.Executors#newFixedThreadPool(int)`相同
| newCachedPool | 规则与`java.util.concurrent.Executors#newCachedThreadPool()`相同


## VI. Demo项目

![][demo_screenshot_png]

## VII. License

```
Copyright (C) 2015-2016 Jacksgong(blog.dreamtobe.cn)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[demo_screenshot_png]: https://raw.githubusercontent.com/Jacksgong/ThreadDebugger/master/art/demo_screenshot_zh.png
[thread_pools_svg]: https://img.shields.io/badge/Thread-Pools-green.svg
[thread_pools_link]: https://github.com/Jacksgong/ThreadDebugger/blob/master/threadpool/src/main/java/cn/dreamtobe/threadpool/ThreadPools.java
[thread_debugger_svg]: https://img.shields.io/badge/Thread-Debugger-orange.svg
[thread_debugger_link]: https://github.com/Jacksgong/ThreadDebugger/blob/master/threaddebugger/src/main/java/cn/dreamtobe/threaddebugger/ThreadDebugger.java
[build_status_svg]: https://travis-ci.org/Jacksgong/ThreadDebugger.svg?branch=master
[build_status_link]: https://travis-ci.org/Jacksgong/ThreadDebugger
