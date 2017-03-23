# ThreadDebugger

[![ThreadDebugger][thread_debugger_svg]][thread_debugger_link]
[![ThreadPools][thread_pools_svg]][thread_pools_link]
[![Build Status][build_status_svg]][build_status_link]

[README](https://github.com/Jacksgong/ThreadDebugger/blob/master/README.md)|[中文文档](https://github.com/Jacksgong/ThreadDebugger/blob/master/README_zh.md)

---

This Github includes: Threads debugger(threaddebugger)、Thread pool factory(threadpools).

#### Thread debugger

There are several ways to debugger the activity of threads in the application, such as the Allocation Tracking from Android Studio Monitor by the way there are information about the running threads, or recording the Method Profiling from the Android Device Monitor by the way it also present the running threads information, but they are a little too heavy, and not flexible enough sometimes.

With this ThreadDebugger, you don't need to worry about how long duration you recording, and you can find out the changing of the threads activity very easy.

#### Thread pool factory

The executor from this thread pool factory will require each task provide its exact name to facilitate debugging, and making create some thread pools in the common rule very convenient.

---

## I. Installation

ThreadDebugger and ThreadPool is installed by adding the following dependency to your `build.gradle` file:

```groovy
dependencies {
    // If you need use ThreadDebugger.
    debugCompile 'cn.dreamtobe.threaddebugger:threaddebugger:1.5.0'
    releaseCompile 'cn.dreamtobe.threaddebugger:threaddebugger-no-op:1.5.0'
    // If you need use ThreadPool.
    compile 'cn.dreamtobe.threaddebugger:threadpool:1.5.0'
}
```

- ThreadDebugger: [ ![Download](https://api.bintray.com/packages/jacksgong/maven/ThreadDebugger/images/download.svg) ](https://bintray.com/jacksgong/maven/ThreadDebugger/_latestVersion)
- ThreadDebugger-no-op: [ ![Download](https://api.bintray.com/packages/jacksgong/maven/ThreadDebugger-threaddebugger-no-op/images/download.svg) ](https://bintray.com/jacksgong/maven/ThreadDebugger-threaddebugger-no-op/_latestVersion)
- ThreadPool: [ ![Download](https://api.bintray.com/packages/jacksgong/maven/ThreadDebugger-threadpool/images/download.svg) ](https://bintray.com/jacksgong/maven/ThreadDebugger-threadpool/_latestVersion)

## II. Start monitor

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

## III. Output Logcat

> If the time is your consider, please using `adb logcat -v time` instead of `adb logcat -v raw` on bellow commands.

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

## IV. Method in IThreadDebugger

| Method | Function
| --- | ---
| add(key:String) | add the thread category with the key used to compare the start of thread-name and also as its alias to the Debugger.
| add(startWithKey:String , alias:String) | add the thread category with the startWithKey and its alias to the Debugger.
| refresh() | Refresh threads situation.
| drawUpEachThreadSize() | the content of the size of each thread.
| drawUpEachThreadInfo() | the content of the information of each thread.
| drawUpEachThreadSizeDiff() | the content of the changed size of each changed thread.
| drawUpEachThreadInfoDiff() | the content of the information of the each changed thread.
| drawUpUnknownInfo() | the content of unknown threads info.
| isSizeChanged() | Whether threads size has changed.
| isChanged() | Whether threads has changed.

## V. ThreadPools

> The executor from this thread pool factory will require each task in it to provide its exact name to facilitate debugging.

> - Entrance: [ThreadPools](https://github.com/Jacksgong/ThreadDebugger/blob/master/threadpool/src/main/java/cn/dreamtobe/threadpool/ThreadPools.java)
> - Demo: [DemoThreadPoolCentral](https://github.com/Jacksgong/ThreadDebugger/blob/master/demo/src/main/java/cn/dreamtobe/threaddebugger/demo/DemoThreadPoolCentral.java)

#### 1. Basic Rule

When a new task is submitted in method `execute(Runnable)`, and fewer than `corePoolSize` threads are running, a new thread is created to handle the request, even if other worker threads are idle. If there are more than `corePoolSize` but less than `maximumPoolSize` threads running, a new thread is created to handle the request too, but when it turn to idle and the interval time of waiting for new tasks more than `keepAliveTime`, it will be terminate to reduce the cost of resources.


#### 2. Methods in ThreadPools

| Method | Function
| --- | ---
| newExceedWaitPool | Under the premise of satisfying the **Basic Rules**, if there are `maximumPoolSize` tasks running, the further task will be enqueued to the waiting queue, and will be executed when the size of running tasks less than `maximumPoolSize`.
| newExceedDiscardPool | Under the premise of satisfying the **Basic Rules**, if there are `maximumPoolSize` tasks running, the further task will be discard.
| newExceedCallerRunsPool | Under the premise of satisfying the **Basic Rules**, if there are `maximumPoolSize` tasks running, the further task will be executed immediately in the caller thread.
| newExceedCallImmediatelyPool | Under the premise of satisfying the **Basic Rules**, if there are `maximumPoolSize` tasks running, the further task will be executed immediately in the global temporary unbound thread pool.
| newSinglePool | The same to the `java.util.concurrent.Executors#newSingleThreadExecutor()`.
| newFixedPool | The same to the `java.util.concurrent.Executors#newFixedThreadPool(int)`.
| newCachedPool | The same to the `java.util.concurrent.Executors#newCachedThreadPool()`.
| newNoCorePool | If there are `threadCount` tasks are running, the further task will be enqueued to the waiting queue, and will be executed when the size of running tasks less than `threadCount`. If the thread in this pool is turn to idle and the interval time of waiting for new tasks more `keepAliveTime`, it will be terminate to reduce the cost of resources.


## VI. Demo Project

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

[demo_screenshot_png]: https://raw.githubusercontent.com/Jacksgong/ThreadDebugger/master/art/demo_screenshot.png
[thread_pools_svg]: https://img.shields.io/badge/Thread-Pools-green.svg
[thread_pools_link]: https://github.com/Jacksgong/ThreadDebugger/blob/master/threadpool/src/main/java/cn/dreamtobe/threadpool/ThreadPools.java
[thread_debugger_svg]: https://img.shields.io/badge/Thread-Debugger-orange.svg
[thread_debugger_link]: https://github.com/Jacksgong/ThreadDebugger/blob/master/threaddebugger/src/main/java/cn/dreamtobe/threaddebugger/ThreadDebugger.java
[build_status_svg]: https://travis-ci.org/Jacksgong/ThreadDebugger.svg?branch=master
[build_status_link]: https://travis-ci.org/Jacksgong/ThreadDebugger
